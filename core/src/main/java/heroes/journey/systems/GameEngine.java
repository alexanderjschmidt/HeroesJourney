package heroes.journey.systems;

import com.badlogic.ashley.core.Entity;
import heroes.journey.components.GameStateComponent;
import heroes.journey.systems.constantsystems.CleanupNonGlobalGameStateSystem;
import heroes.journey.systems.constantsystems.MovementSystem;
import heroes.journey.systems.constantsystems.RenderSystem;
import heroes.journey.systems.endofturnsystems.CooldownSystem;
import heroes.journey.systems.endofturnsystems.FactionSystem;
import heroes.journey.systems.listeners.GlobalGameStateListener;
import heroes.journey.systems.listeners.GlobalPositionListener;
import heroes.journey.systems.listeners.StatsActionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameEngine extends com.badlogic.ashley.core.Engine {

    List<EndOfTurnSystem> endOfTurnSystems;

    private static GameEngine gameEngine;

    public static GameEngine get() {
        if (gameEngine == null) {
            gameEngine = new GameEngine();
            gameEngine.initSystems();
        }
        return gameEngine;
    }

    private GameEngine() {
        super();
        endOfTurnSystems = new ArrayList<>();
    }

    private void initSystems() {
        addSystem(new RenderSystem());
        addSystem(new MovementSystem());
        addSystem(new CleanupNonGlobalGameStateSystem());
        addSystem(new FactionSystem());
        addSystem(new CooldownSystem());

        addEntityListener(GlobalGameStateListener.getFamily(), new GlobalGameStateListener());
        addEntityListener(GlobalPositionListener.getFamily(), new GlobalPositionListener());
        addEntityListener(StatsActionsListener.getFamily(), new StatsActionsListener());
    }

    public void enableEndOfTurnSystems() {
        for (EndOfTurnSystem system : endOfTurnSystems)
            system.setProcessing(true);
    }

    public static UUID getID(Entity e) {
        GameStateComponent gameStateComponent = GameStateComponent.get(e);
        return gameStateComponent == null ? null : gameStateComponent.getId();
    }

}
