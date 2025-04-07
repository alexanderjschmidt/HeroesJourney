package heroes.journey.systems;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.GameStateComponent;
import heroes.journey.systems.constantsystems.MovementSystem;
import heroes.journey.systems.constantsystems.RenderSystem;
import heroes.journey.systems.endofturnsystems.CooldownSystem;
import heroes.journey.systems.endofturnsystems.FactionSystem;
import heroes.journey.systems.listeners.FactionCarriageListener;
import heroes.journey.systems.listeners.GlobalPositionListener;
import heroes.journey.systems.listeners.StatsActionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameEngine extends com.badlogic.ashley.core.Engine {

    List<EndOfTurnSystem> endOfTurnSystems;

    public GameEngine() {
        super();
        endOfTurnSystems = new ArrayList<>();

        addEntityListener(StatsActionsListener.getFamily(), new StatsActionsListener());
        addEntityListener(FactionCarriageListener.getFamily(), new FactionCarriageListener());
    }

    public void initSystems(GameState gameState) {
        addSystem(new RenderSystem());
        addSystem(new MovementSystem());
        addSystem(new FactionSystem(this));
        addSystem(new CooldownSystem(this));

        addEntityListener(GlobalPositionListener.getFamily(), new GlobalPositionListener(gameState));
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
