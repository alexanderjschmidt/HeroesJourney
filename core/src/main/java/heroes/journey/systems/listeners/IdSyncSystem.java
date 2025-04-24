package heroes.journey.systems.listeners;

import com.artemis.BaseEntitySystem;
import com.artemis.World;
import com.artemis.annotations.All;

import heroes.journey.GameState;
import heroes.journey.components.character.IdComponent;

@All({IdComponent.class})
public class IdSyncSystem extends BaseEntitySystem {

    private final GameState gameState;

    public IdSyncSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void inserted(int entityId) {
        World world = getWorld();
        IdComponent idComponent = IdComponent.get(world, entityId);
        if (idComponent.id() == -1) {
            int id = gameState.getEntities().register(entityId);
            idComponent.id(id);
        } else {
            gameState.getEntities().register(entityId, idComponent.id());
        }
    }

    @Override
    public void removed(int entityId) {
        // TODO make sure its target and current pos is cleared
        World world = getWorld();
        IdComponent idComponent = IdComponent.get(world, entityId);
        gameState.getEntities().unregister(idComponent.id());
    }

    @Override
    protected void processSystem() {

    }
}
