package heroes.journey.systems.listeners;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.systems.GameWorld;

import java.util.UUID;

import static heroes.journey.initializers.base.actions.CarriageActions.createCarriageAction;

@All({PositionComponent.class, LocationComponent.class, IdComponent.class})
public class LocationPositionSyncSystem extends IteratingSystem {

    private final GameState gameState;

    public LocationPositionSyncSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void inserted(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);
        LocationComponent loc = LocationComponent.get(world, id);

        pos.sync();
        gameState.getEntities().addLocation(id, pos.getX(), pos.getY());

        if (loc.capital())
            // TODO make this happen in carriage system on insert?
            createCarriageAction(gameState, id);
    }

    @Override
    public void removed(int entityId) {
        // TODO make sure its target and current pos is cleared
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);

        gameState.getEntities().removeLocation(pos.getX(), pos.getY());
        pos.sync();
        gameState.getEntities().removeLocation(pos.getX(), pos.getY());
    }

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);
        if (pos.getTargetX() != pos.getX() || pos.getTargetY() != pos.getY()) {
            // System.out.println(pos.getX() + ", " + pos.getY());
            // System.out.println(pos.getTargetX() + ", " + pos.getTargetY());
            gameState.getEntities().removeLocation(pos.getX(), pos.getY());
            pos.sync();
            gameState.getEntities().addLocation(id, pos.getX(), pos.getY());
        }

    }
}
