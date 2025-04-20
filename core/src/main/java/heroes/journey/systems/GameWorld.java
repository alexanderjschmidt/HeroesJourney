package heroes.journey.systems;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.utils.IntBag;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.entities.Position;
import heroes.journey.systems.constantsystems.AISystem;
import heroes.journey.systems.constantsystems.MovementSystem;
import heroes.journey.systems.constantsystems.RenderSystem;
import heroes.journey.systems.endofturnsystems.CooldownSystem;
import heroes.journey.systems.listeners.FactionCarriageListener;
import heroes.journey.systems.listeners.PositionSyncSystem;
import heroes.journey.systems.listeners.StatsActionsListener;

public class GameWorld extends World {

    private final List<EndOfTurnSystem> endOfTurnSystems = new ArrayList<>();
    private final WorldSerializationManager manager;
    private final KryoArtemisSerializer kryoSerializer;

    private GameWorld(WorldConfiguration config) {
        super(config);
        manager = this.getSystem(WorldSerializationManager.class);
        kryoSerializer = new KryoArtemisSerializer(this);
        initKryoSerializer();
        manager.setSerializer(kryoSerializer);
    }

    private void initKryoSerializer() {
        kryoSerializer.register(Position.class, Position.getKryoSerializer());
        kryoSerializer.register(PositionComponent.class);

        kryoSerializer.getKryo().setReferences(false);
        // To debug what doesnt have a custom serializer
        // kryoSerializer.getKryo().setRegistrationRequired(true);
    }

    public static GameWorld initGameWorld(GameState gameState) {
        GameWorld world = new GameWorld(buildConfig(gameState, false));
        world.collectEndOfTurnSystems();  // Auto-detect end-of-turn systems, if needed

        return world;
    }

    private static WorldConfiguration buildConfig(GameState gameState, boolean limited) {
        WorldConfigurationBuilder builder = new WorldConfigurationBuilder().with(
                new WorldSerializationManager())
            .with(new CooldownSystem())                 // Removed 'this' (was illegal in static context!)
            .with(new PositionSyncSystem(gameState))
            .with(new StatsActionsListener())
            .with(new FactionCarriageListener());
        if (!limited) {
            builder.with(new RenderSystem()).with(new MovementSystem()).with(new AISystem());
        }

        return builder.build();
    }

    private void collectEndOfTurnSystems() {
        for (BaseSystem system : getSystems()) {
            if (system instanceof EndOfTurnSystem) {
                endOfTurnSystems.add((EndOfTurnSystem)system);
            }
        }
    }

    public void enableEndOfTurnSystems() {
        for (EndOfTurnSystem system : endOfTurnSystems) {
            system.setEnabled(true);
        }
    }

    @SafeVarargs
    public final IntBag getEntitiesWith(Class<? extends Component>... components) {
        AspectSubscriptionManager asm = getSystem(AspectSubscriptionManager.class);
        EntitySubscription subscription = asm.get(Aspect.all(components));
        return subscription.getEntities();
    }

    public void saveWorld(GameState gameState) {
        JsonArtemisSerializer jsonSerializer;
        jsonSerializer = new JsonArtemisSerializer(this);
        jsonSerializer.register(Position.class, Position.getJSONSerializer());
        manager.setSerializer(jsonSerializer);
        cloneWorld(gameState);
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
        // Export current world state
        final EntitySubscription allEntities = this.getAspectSubscriptionManager().get(Aspect.all());

        // Create save format (container for entity state)
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final SaveFileFormat save = new SaveFileFormat(allEntities);

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

}
