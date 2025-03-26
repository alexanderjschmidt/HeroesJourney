package heroes.journey.systems;

import com.badlogic.ashley.core.Entity;
import heroes.journey.components.GameStateComponent;
import heroes.journey.systems.listeners.GlobalGameStateListener;
import heroes.journey.systems.listeners.GlobalPositionListener;
import heroes.journey.systems.listeners.StatsActionsListener;

import java.util.UUID;

public class GameEngine extends com.badlogic.ashley.core.Engine {

    private static GameEngine gameEngine;

    public static GameEngine get() {
        if (gameEngine == null)
            gameEngine = new GameEngine();
        return gameEngine;
    }

    private GameEngine() {
        super();
        addSystem(new RenderSystem());
        addSystem(new MovementSystem());
        addSystem(new CleanupNonGlobalGameStateSystem());
        FactionSystem factionSystem = new FactionSystem();
        factionSystem.setProcessing(false);
        addSystem(factionSystem);

        addEntityListener(GlobalGameStateListener.getFamily(), new GlobalGameStateListener());
        addEntityListener(GlobalPositionListener.getFamily(), new GlobalPositionListener());
        addEntityListener(StatsActionsListener.getFamily(), new StatsActionsListener());
    }

    public static UUID getID(Entity e) {
        GameStateComponent gameStateComponent = GameStateComponent.get(e);
        return gameStateComponent == null ? null : gameStateComponent.getId();
    }

}
