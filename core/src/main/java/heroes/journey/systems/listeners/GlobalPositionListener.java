package heroes.journey.systems.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import heroes.journey.GameState;
import heroes.journey.components.GameStateComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.components.overworld.place.FactionComponent;

public class GlobalPositionListener implements EntityListener {

    private GameState gameState;

    public GlobalPositionListener(GameState gameState) {
        this.gameState = gameState;
    }

    public static Family getFamily() {
        return Family.all(GameStateComponent.class, PositionComponent.class).get();
    }

    @Override
    public void entityAdded(Entity entity) {
        FactionComponent factionComponent = FactionComponent.get(entity);
        if (factionComponent != null) {
            return;
        }
        GameStateComponent gameStateComponent = GameStateComponent.get(entity);
        if (gameStateComponent.isGlobal()) {
            gameState.getEntities().registerEntity(gameStateComponent.getId(), entity);
        }
        gameState.getEntities().addEntity(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
        FactionComponent factionComponent = FactionComponent.get(entity);
        if (factionComponent != null) {
            return;
        }
        GameStateComponent gameStateComponent = GameStateComponent.get(entity);
        gameState.getEntities().removeEntity(entity);
        if (gameStateComponent.isGlobal())
            gameState.getEntities().unregisterEntity(gameStateComponent.getId());
    }
}
