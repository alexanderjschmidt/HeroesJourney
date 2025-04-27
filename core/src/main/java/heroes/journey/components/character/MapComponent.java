package heroes.journey.components.character;

import com.artemis.World;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.tilemap.Fog;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class MapComponent extends PooledClonableComponent<MapComponent> {

    private Fog[][] fog;

    public MapComponent() {
        fog = new Fog[GameState.global().getWidth()][GameState.global().getHeight()];
        for (Fog[] fogs : fog) {
            Arrays.fill(fogs, Fog.DENSE);
        }
    }

    public void setFog(GameState gameState, Integer entityId) {
        if (entityId == null) {
            return;
        }
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        int vision = statsComponent.getBody() + 3;

        int x = positionComponent.getX();
        int y = positionComponent.getY();

        // Iterate through the grid and set fog based on the vision range.
        for (int i = -vision; i <= vision; i++) {
            for (int j = -vision; j <= vision; j++) {
                int newX = x + i;
                int newY = y + j;

                // Ensure we are within bounds of the fog array
                if (newX >= 0 && newX < fog.length && newY >= 0 && newY < fog.length) {
                    // Set fog to None if within the vision range
                    fog[newX][newY] = null; // Assuming `null` indicates no fog or visible.
                }
            }
        }
    }

    public static MapComponent get(World world, int entityId) {
        return world.getMapper(MapComponent.class).get(entityId);
    }

    @Override
    public void copy(MapComponent from) {
        this.fog = from.fog;
    }
}
