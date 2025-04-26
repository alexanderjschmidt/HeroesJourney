package heroes.journey.systems.listeners;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;
import heroes.journey.GameState;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.place.LocationComponent;

@All({PositionComponent.class})
@Exclude({LocationComponent.class})
public class PositionSyncSystem extends IteratingSystem {

    private final GameState gameState;

    public PositionSyncSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void inserted(int entityId) {
        World world = getWorld();
        PositionComponent pos = PositionComponent.get(world, entityId);
        pos.sync();
        gameState.getEntities().addEntity(entityId, pos.getX(), pos.getY());
    }

    @Override
    public void removed(int entityId) {
        // TODO make sure its target and current pos is cleared
        World world = getWorld();
        PositionComponent pos = PositionComponent.get(world, entityId);

        gameState.getEntities().removeEntity(pos.getX(), pos.getY());
        pos.sync();
        gameState.getEntities().removeEntity(pos.getX(), pos.getY());
    }

    @Override
    protected void process(int entityId) {
        PositionComponent pos = PositionComponent.get(world, entityId);
        if (pos.isNotSynced()) {
            // System.out.println(pos.getX() + ", " + pos.getY());
            // System.out.println(pos.getTargetX() + ", " + pos.getTargetY());
            gameState.getEntities().removeEntity(pos.getX(), pos.getY());
            pos.sync();
            gameState.getEntities().addEntity(entityId, pos.getX(), pos.getY());
        }
    }
}
