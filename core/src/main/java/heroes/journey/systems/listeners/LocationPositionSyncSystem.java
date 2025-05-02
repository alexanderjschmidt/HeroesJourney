package heroes.journey.systems.listeners;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.place.LocationComponent;

@All({PositionComponent.class, LocationComponent.class})
public class LocationPositionSyncSystem extends IteratingSystem {

    private final GameState gameState;

    public LocationPositionSyncSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void inserted(int entityId) {
        World world = getWorld();
        PositionComponent pos = PositionComponent.get(world, entityId);

        pos.sync();
        gameState.getEntities().addLocation(entityId, pos.getX(), pos.getY());
    }

    @Override
    public void removed(int entityId) {
        // TODO make sure its target and current pos is cleared
        World world = getWorld();
        PositionComponent pos = PositionComponent.get(world, entityId);

        gameState.getEntities().removeLocation(pos.getX(), pos.getY());
        pos.sync();
        gameState.getEntities().removeLocation(pos.getX(), pos.getY());
    }

    @Override
    protected void process(int entityId) {
        PositionComponent pos = PositionComponent.get(world, entityId);
        if (pos.getTargetX() != pos.getX() || pos.getTargetY() != pos.getY()) {
            // System.out.println(pos.getX() + ", " + pos.getY());
            // System.out.println(pos.getTargetX() + ", " + pos.getTargetY());
            gameState.getEntities().removeLocation(pos.getX(), pos.getY());
            pos.sync();
            gameState.getEntities().addLocation(entityId, pos.getX(), pos.getY());
        }
    }
}
