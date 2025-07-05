package heroes.journey.systems;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import heroes.journey.GameState;
import heroes.journey.components.LocationComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.systems.constantsystems.AISystem;
import heroes.journey.systems.constantsystems.AIWanderSystem;
import heroes.journey.systems.constantsystems.ActionSystem;
import heroes.journey.systems.constantsystems.MovementSystem;
import heroes.journey.systems.constantsystems.RenderSystem;
import heroes.journey.systems.listeners.IdSyncSystem;
import heroes.journey.systems.listeners.LocationPositionSyncSystem;
import heroes.journey.systems.listeners.PositionSyncSystem;
import heroes.journey.systems.listeners.StatsActionsListener;
import heroes.journey.systems.triggerable.BuffSystem;
import heroes.journey.systems.triggerable.CooldownSystem;
import heroes.journey.systems.triggerable.EventSystem;
import heroes.journey.systems.triggerable.QuestSystem;
import heroes.journey.systems.triggerable.RegionManagementSystem;
import heroes.journey.utils.serializers.Serializers;

public class GameWorld extends World {

    private static final List<Class<? extends Component>> nonBasicSystems = new ArrayList<>();
    // TODO use this registration for any entity references since I cant trust the entityId will stay the same across GameWorlds
    public final Map<UUID,Integer> entityMap;
    private final List<TriggerableSystem> triggerableSystems = new ArrayList<>();
    private final WorldSerializationManager manager;

    private EntitySubscription locationSubscription, regionSubscription;
    private EntityFactory entityFactory;

    private final GameState gameState;

    private GameWorld(WorldConfiguration config, GameState gameState) {
        super(config);
        this.gameState = gameState;
        manager = this.getSystem(WorldSerializationManager.class);
        manager.setSerializer(Serializers.kryo(this));
        entityMap = new HashMap<>();

        locationSubscription = getAspectSubscriptionManager().get(Aspect.all(LocationComponent.class));
        regionSubscription = getAspectSubscriptionManager().get(Aspect.all(RegionComponent.class));
        entityFactory = new EntityFactory(this);
    }

    public static GameWorld initGameWorld(GameState gameState) {
        GameWorld world = new GameWorld(buildConfig(gameState, false), gameState);
        world.collectTriggerableSystems();  // Auto-detect end-of-turn systems, if needed

        return world;
    }

    private static WorldConfiguration buildConfig(GameState gameState, boolean limited) {
        WorldConfigurationBuilder builder = new WorldConfigurationBuilder().with(
            new WorldSerializationManager());
        builder.with(new IdSyncSystem())
            .with(new CooldownSystem())
            .with(new QuestSystem())
            .with(new RegionManagementSystem())
            .with(new PositionSyncSystem())
            .with(new LocationPositionSyncSystem())
            .with(new EventSystem())
            .with(new StatsActionsListener())
            .with(new BuffSystem());
        if (!limited) {
            builder.with(new RenderSystem())
                .with(new MovementSystem())
                .with(new ActionSystem())
                .with(new AISystem())
                .with(new AIWanderSystem());
        }

        return builder.build();
    }

    // Registration
    public UUID register(int entityId, UUID uuid) {
        entityMap.put(uuid, entityId);
        return uuid;
    }

    public void unregister(UUID uuid) {
        entityMap.remove(uuid);
    }

    public Integer get(UUID uuid) {
        return entityMap.get(uuid);
    }

    private void collectTriggerableSystems() {
        for (BaseSystem system : getSystems()) {
            if (system instanceof TriggerableSystem triggerableSystem) {
                triggerableSystems.add(triggerableSystem);
            }
        }
    }

    public void enableTriggerableSystems(TriggerableSystem.EventTrigger trigger) {
        for (TriggerableSystem system : triggerableSystems) {
            if (system.getTrigger() == trigger)
                system.setEnabled(true);
        }
    }

    @SafeVarargs
    public final List<UUID> getEntitiesWith(Class<? extends Component>... components) {
        AspectSubscriptionManager asm = getSystem(AspectSubscriptionManager.class);
        EntitySubscription subscription = asm.get(Aspect.all(components));
        int[] ids = subscription.getEntities().getData();
        return IntStream.range(0, subscription.getEntities().size())
            .mapToObj(i -> ids[i])
            .sorted(Comparator.comparing(
                    (Integer e) -> StatsUtils.getSpeed(StatsComponent.get(this, IdComponent.get(this, e))))
                .thenComparing(Object::toString))
            .map(e -> IdComponent.get(this, e))
            .collect(Collectors.toList());
    }

    /**
     * For AI Usage only
     *
     * @param gameState
     * @return
     */
    public GameWorld cloneWorld(GameState gameState) {
        long start = System.nanoTime();
        GameWorld cloned = new GameWorld(buildConfig(gameState, true), gameState);

        IntBag entities = this.getAspectSubscriptionManager().get(Aspect.all()).getEntities();
        int[] ids = entities.getData();

        Map<Integer,Integer> oldToNew = new HashMap<>();
        for (int id : ids) {
            oldToNew.put(id, cloned.create());
            ComponentCopier.copyEntity(this, cloned, id, oldToNew.get(id));
        }
        cloned.basicProcess();
        Utils.logTime("cloned " + ids.length + " " + entities.size(), start, 20);
        return cloned;
    }

    public void saveWorld(String saveName, boolean json) {
        KryoArtemisSerializer kryo = manager.getSerializer();
        if (json)
            manager.setSerializer(Serializers.json(this));
        saveWorld(saveName);
        manager.setSerializer(kryo);
    }

    public void loadWorld(String saveName, boolean json) {
        KryoArtemisSerializer kryo = manager.getSerializer();
        if (json)
            manager.setSerializer(Serializers.json(this));
        loadWorld(saveName);
        manager.setSerializer(kryo);
    }

    private void saveWorld(String saveName) {
        long start = System.nanoTime();
        // Export current world state
        final EntitySubscription allEntities = this.getAspectSubscriptionManager().get(Aspect.all());

        // Create save format (container for entity state)
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final SaveFileFormat save = new SaveFileFormat(allEntities);

        this.getSystem(WorldSerializationManager.class).save(baos, save);

        FileHandle file = Gdx.files.local("saves/" + saveName + "/world.json");
        file.writeBytes(baos.toByteArray(), false); // false = overwrite, true = append

        Utils.logTime("saved", start, 20);
    }

    private void loadWorld(String saveName) {
        long start = System.nanoTime();

        // Read saved data from file
        FileHandle file = Gdx.files.local("saves/" + saveName + "/world.json");
        if (!file.exists()) {
            throw new RuntimeException("Save file not found: " + file.path());
        }

        byte[] data = file.readBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        // Deserialize and apply to world
        manager.load(bais, SaveFileFormat.class);
        this.basicProcess();
        System.out.println(this);
        System.out.println(entityMap);

        Utils.logTime("loaded", start, 20);
    }

    public void basicProcess() {
        this.setDelta(0);
        if (this.getSystem(RenderSystem.class) != null) {
            this.getSystem(AISystem.class).setEnabled(false);
            this.getSystem(RenderSystem.class).setEnabled(false);
            this.getSystem(MovementSystem.class).setEnabled(false);
            this.getSystem(ActionSystem.class).setEnabled(false);
        }
        this.process();
        if (this.getSystem(RenderSystem.class) != null) {
            this.getSystem(AISystem.class).setEnabled(true);
            this.getSystem(RenderSystem.class).setEnabled(true);
            this.getSystem(MovementSystem.class).setEnabled(true);
            this.getSystem(ActionSystem.class).setEnabled(true);
        }
    }

    public <T extends Component> T getEntity(java.lang.Class<T> type, UUID entityId) {
        try {
            return super.getMapper(type).get(entityMap.get(entityId));
        } catch (Exception e) {
            System.out.println(this);
            System.out.println(entityId + " " + type);
            System.out.println(entityMap);
            throw e;
        }
    }

    public void delete(UUID entityId) {
        Integer internalId = get(entityId);
        if (internalId == null) {
            // Entity doesn't exist, nothing to do
            return;
        }

        // Call removed methods on all systems before deletion
        Entity e = callSystemRemovedMethods(entityId);

        // Now delete the entity
        e.edit().remove(IdComponent.class);
        this.delete(internalId);
        entityMap.remove(entityId);
    }

    /**
     * Calls the removed method on all systems that have one, before the entity is actually deleted.
     * This allows systems to access components that will be removed during deletion.
     */
    private Entity callSystemRemovedMethods(UUID entityId) {
        Entity e = this.getEntity(entityId);

        // Create an IntBag with just this entity ID
        IntBag entities = new IntBag();
        entities.add(e.getId());

        for (BaseSystem system : getSystems()) {
            if (system instanceof IteratingSystem iteratingSystem) {
                // Check if the entity matches the system's aspect
                if (iteratingSystem.getSubscription().getAspect().isInterested(e)) {
                    try {
                        iteratingSystem.removed(entities);
                    } catch (Exception exception) {
                        // Log any errors but don't fail the deletion
                        System.err.println(
                            "Error calling removed method on " + system.getClass().getSimpleName() + ": " +
                                exception.getMessage());
                    }
                }
            }
        }
        return e;
    }

    public com.artemis.Entity getEntity(UUID entityId) {
        return super.getEntity(entityMap.get(entityId));
    }

    public EntityEdit edit(UUID entityId) {
        return super.edit(entityMap.get(entityId));
    }

    public EntitySubscription getLocationSubscription() {
        return locationSubscription;
    }

    public EntitySubscription getRegionSubscription() {
        return regionSubscription;
    }

    public GameState getGameState() {
        return gameState;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
