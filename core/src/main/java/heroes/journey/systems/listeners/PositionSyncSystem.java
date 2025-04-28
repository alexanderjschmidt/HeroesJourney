package heroes.journey.systems.listeners;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;

import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.tilemap.Fog;

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

            // Update Map
            MapComponent mapComponent = MapComponent.get(GameState.global().getWorld(), entityId);
            StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
            if (mapComponent != null && statsComponent != null) {
                int vision = statsComponent.getVision();
                for (int x = -vision; x <= vision; x++) {
                    for (int y = -vision; y <= vision; y++) {
                        if (Math.abs(x) + Math.abs(y) <= vision) {
                            int newX = pos.getX() + x;
                            int newY = pos.getY() + y;

                            // Ensure we are within bounds of the fog array
                            if (newX >= 0 && newX < mapComponent.getFog().length && newY >= 0 &&
                                newY < mapComponent.getFog()[newX].length) {
                                // Set fog to None (null) if within the vision range
                                mapComponent.getFog()[newX][newY] = Fog.LIGHT;
                                Integer location = gameState.getEntities().getLocation(newX, newY);
                                if (location != null) {
                                    mapComponent.getKnownLocations().add(location);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
