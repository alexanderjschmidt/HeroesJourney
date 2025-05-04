package heroes.journey.systems.listeners;

import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.FogUtils;

import java.util.UUID;

@All({PositionComponent.class, IdComponent.class})
@Exclude({LocationComponent.class})
public class PositionSyncSystem extends IteratingSystem {

    private final GameState gameState;

    public PositionSyncSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void inserted(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);
        pos.sync();
        gameState.getEntities().addEntity(id, pos.getX(), pos.getY());
    }

    @Override
    public void removed(int entityId) {
        // TODO make sure its target and current pos is cleared
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);

        gameState.getEntities().removeEntity(pos.getX(), pos.getY());
        pos.sync();
        gameState.getEntities().removeEntity(pos.getX(), pos.getY());
    }

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent pos = PositionComponent.get(world, id);
        if (pos.isNotSynced()) {
            // System.out.println(pos.getX() + ", " + pos.getY());
            // System.out.println(pos.getTargetX() + ", " + pos.getTargetY());
            gameState.getEntities().removeEntity(pos.getX(), pos.getY());
            pos.sync();
            gameState.getEntities().addEntity(id, pos.getX(), pos.getY());

            // Update Map
            MapComponent mapComponent = MapComponent.get(GameState.global().getWorld(), id);
            StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), id);
            if (mapComponent != null && statsComponent != null) {
                FogUtils.updateMap(gameState, mapComponent, pos.getX(), pos.getY(),
                    statsComponent.getVision());
            }
        }
    }
}
