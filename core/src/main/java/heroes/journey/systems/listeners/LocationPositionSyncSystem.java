package heroes.journey.systems.listeners;

import java.util.UUID;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

import heroes.journey.components.LocationComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;

@All({PositionComponent.class, LocationComponent.class, IdComponent.class})
public class LocationPositionSyncSystem extends IteratingSystem {

    @Override
    public void inserted(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);

        pos.sync();
        world.getGameState().getEntities().addLocation(id, pos.getX(), pos.getY());
    }

    @Override
    public void removed(int entityId) {
        // TODO make sure its target and current pos is cleared
        GameWorld world = (GameWorld)getWorld();
        
        // Try to get the ID component - if it doesn't exist, all components are gone
        UUID id = null;
        try {
            id = IdComponent.get(world, entityId);
        } catch (Exception e) {
            return;
        }
        
        // If we have the ID, we can safely access other components
        PositionComponent pos = PositionComponent.get(world, id);
        world.getGameState().getEntities().removeLocation(pos.getX(), pos.getY());
        pos.sync();
        world.getGameState().getEntities().removeLocation(pos.getX(), pos.getY());
    }

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);
        if (pos.getTargetX() != pos.getX() || pos.getTargetY() != pos.getY()) {
            // System.out.println(pos.getX() + ", " + pos.getY());
            // System.out.println(pos.getTargetX() + ", " + pos.getTargetY());
            world.getGameState().getEntities().removeLocation(pos.getX(), pos.getY());
            pos.sync();
            world.getGameState().getEntities().addLocation(id, pos.getX(), pos.getY());
        }

    }
}
