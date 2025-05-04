package heroes.journey.systems;

import com.artemis.*;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.utils.IntBag;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.entities.Position;
import heroes.journey.systems.constantsystems.AISystem;
import heroes.journey.systems.constantsystems.ActionSystem;
import heroes.journey.systems.constantsystems.MovementSystem;
import heroes.journey.systems.constantsystems.RenderSystem;
import heroes.journey.systems.listeners.IdSyncSystem;
import heroes.journey.systems.listeners.LocationPositionSyncSystem;
import heroes.journey.systems.listeners.PositionSyncSystem;
import heroes.journey.systems.listeners.StatsActionsListener;
import heroes.journey.systems.triggerable.CooldownSystem;
import heroes.journey.systems.triggerable.QuestSystem;
import heroes.journey.systems.triggerable.RegenSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameWorld extends World {

    private final List<TriggerableSystem> triggerableSystems = new ArrayList<>();
    private final List<BaseSystem> nonBasicSystems = new ArrayList<>();
    private final WorldSerializationManager manager;
    private final KryoArtemisSerializer kryoSerializer;

    // TODO use this registration for any entity references since I cant trust the entityId will stay the same across GameWorlds
    private final Map<UUID, Integer> entityMap;

    private GameWorld(WorldConfiguration config) {
        super(config);
        manager = this.getSystem(WorldSerializationManager.class);
        kryoSerializer = new KryoArtemisSerializer(this);
        initKryoSerializer();
        manager.setSerializer(kryoSerializer);
        entityMap = new HashMap<>();
    }

    private void initKryoSerializer() {
        kryoSerializer.register(Position.class, Position.getKryoSerializer());
        kryoSerializer.register(PositionComponent.class);

        kryoSerializer.getKryo().setReferences(false);
        // To debug what doesnt have a custom serializer
        // kryoSerializer.getKryo().setRegistrationRequired(true);
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

    public static GameWorld initGameWorld(GameState gameState) {
        GameWorld world = new GameWorld(buildConfig(gameState, false));
        world.collectTriggerableSystems();  // Auto-detect end-of-turn systems, if needed

        return world;
    }

    private static WorldConfiguration buildConfig(GameState gameState, boolean limited) {
        WorldConfigurationBuilder builder = new WorldConfigurationBuilder().with(
            new WorldSerializationManager());
        builder.with(new IdSyncSystem())
            .with(new CooldownSystem())
            .with(new RegenSystem())
            .with(new QuestSystem(gameState))
            .with(new PositionSyncSystem(gameState))
            .with(new LocationPositionSyncSystem(gameState))
            .with(new StatsActionsListener());
        if (!limited) {
            builder.with(new RenderSystem())
                .with(new MovementSystem())
                .with(new ActionSystem())
                .with(new AISystem());
        }

        return builder.build();
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
        return IntStream.range(0, ids.length).mapToObj(i -> ids[i])  // convert int index to UUID entityId
            .sorted(Comparator.comparing((Integer e) -> {
                    UUID id = IdComponent.get(this, e);
                    return StatsComponent.get(this, id).getSpeed();
                })
                .thenComparing(Object::toString)).map(e -> {
                UUID id = IdComponent.get(this, e);
                return id;
            }).collect(Collectors.toList());
    }

    public void saveWorld(GameState gameState) {
        JsonArtemisSerializer jsonSerializer;
        jsonSerializer = new JsonArtemisSerializer(this);
        jsonSerializer.register(Position.class, Position.getJSONSerializer());
        manager.setSerializer(jsonSerializer);
        cloneWorldSerializer(gameState);
        manager.setSerializer(kryoSerializer);
    }

    /**
     * For AI Usage only
     *
     * @param gameState
     * @return
     */
    public GameWorld cloneWorld(GameState gameState) {
        long start = System.nanoTime();
        GameWorld cloned = new GameWorld(buildConfig(gameState, true));

        IntBag entities = this.getAspectSubscriptionManager().get(Aspect.all()).getEntities();
        int[] ids = entities.getData();

        Map<Integer, Integer> oldToNew = new HashMap<>();
        for (int id : ids) {
            oldToNew.put(id, cloned.create());
            ComponentCopier.copyEntity(this, cloned, oldToNew.get(id));
        }

        cloned.basicProcess();
        //System.out.println("clone took " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        return cloned;
    }

    /**
     * For AI Usage only
     *
     * @param gameState
     * @return
     */
    public GameWorld cloneWorldSerializer(GameState gameState) {
        long start = System.nanoTime();
        // Export current world state
        final EntitySubscription allEntities = this.getAspectSubscriptionManager().get(Aspect.all());

        // Create save format (container for entity state)
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final SaveFileFormat save = new SaveFileFormat(allEntities);

        this.getSystem(WorldSerializationManager.class).save(baos, save);

        // Create a new GameWorld with a fresh config
        GameWorld cloned = new GameWorld(buildConfig(gameState, true));

        // New serializer for the cloned world
        try {
            final ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
            cloned.getSystem(WorldSerializationManager.class).load(is, SaveFileFormat.class);
        } catch (Exception e) {
            System.out.println("Failed to clone world: " + e);
            e.printStackTrace();
            System.out.println("Known components: " + this.getComponentManager().getComponentTypes());
            System.out.println("Known archetypes: " + save.archetypes);
            try {
                System.out.println(baos.toString("UTF-8"));
            } catch (UnsupportedEncodingException uncoded) {
                throw new RuntimeException(uncoded);
            }
        }

        cloned.process();
        System.out.println("clone took " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        return cloned;
    }

    public void basicProcess() {
        this.setDelta(0);
        if (this.getSystem(RenderSystem.class) != null) {
            this.getSystem(AISystem.class).setEnabled(false);
            this.getSystem(RenderSystem.class).setEnabled(false);
            this.getSystem(MovementSystem.class).setEnabled(false);
        }
        this.process();
        if (this.getSystem(RenderSystem.class) != null) {
            this.getSystem(AISystem.class).setEnabled(true);
            this.getSystem(RenderSystem.class).setEnabled(true);
            this.getSystem(MovementSystem.class).setEnabled(true);
        }
    }

    public <T extends Component> T getEntity(java.lang.Class<T> type, UUID entityId) {
        return super.getMapper(type).get(entityMap.get(entityId));
    }

    public com.artemis.Entity getEntity(UUID entityId) {
        return super.getEntity(entityMap.get(entityId));
    }

    public EntityEdit edit(UUID entityId) {
        return super.edit(entityMap.get(entityId));
    }
}
