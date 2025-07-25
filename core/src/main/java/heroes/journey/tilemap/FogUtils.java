package heroes.journey.tilemap;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.modlib.Ids;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.Direction;

public class FogUtils {

    private static int dist(int x, int y) {
        int absX = Math.abs(x);
        int absY = Math.abs(y);
        return (int)Math.sqrt((absX * absX) + (absY * absY)); // Euclidean
        // return absX + absY; // Manhattan
    }

    public static Fog[][] getFog(GameState gameState, UUID entityId) {
        Fog[][] fog = new Fog[gameState.getWidth()][gameState.getHeight()];
        // Get Playing entities MapGenPlan
        MapComponent mapComponent = MapComponent.get(gameState.getWorld(), entityId);
        if (mapComponent != null)
            FogUtils.mergeFog(fog, mapComponent.getFog());
        // Get Playing entities vision
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent != null && positionComponent != null) {
            FogUtils.applyVision(fog, positionComponent.getX(), positionComponent.getY(),
                statsComponent.get(Ids.STAT_VISION));
        }
        return fog;
    }

    public static boolean isVisible(int x, int y) {
        return PlayerInfo.get().getFog()[x][y] == Fog.NONE || PlayerInfo.get().getFog()[x][y] == null;
    }

    private static void applyVision(Fog[][] fog, int xBase, int yBase, int vision) {
        for (int x = -vision; x <= vision; x++) {
            for (int y = -vision; y <= vision; y++) {
                if (dist(x, y) <= vision) {
                    int newX = xBase + x;
                    int newY = yBase + y;

                    // Ensure we are within bounds of the fog array
                    if (inBounds(newX, newY, fog)) {
                        // Set fog to None (null) if within the vision range
                        fog[newX][newY] = null; // Assuming `null` indicates visible (no fog).
                    }
                }
            }
        }
    }

    public static void updateMap(GameWorld world, GameState gameState, UUID id) {
        PositionComponent pos = PositionComponent.get(world, id);
        MapComponent mapComponent = MapComponent.get(world, id);
        Attributes statsComponent = StatsComponent.get(world, id);
        if (pos != null && mapComponent != null && statsComponent != null) {
            FogUtils.updateMap(world, gameState, mapComponent, pos.getX(), pos.getY(),
                statsComponent.get(Ids.STAT_VISION));
        }
    }

    private static void updateMap(
        GameWorld world,
        GameState gameState,
        MapComponent mapComponent,
        int xBase,
        int yBase,
        int vision) {
        for (int x = -vision; x <= vision; x++) {
            for (int y = -vision; y <= vision; y++) {
                if (dist(x, y) <= vision) {
                    int newX = xBase + x;
                    int newY = yBase + y;

                    // Ensure we are within bounds of the fog array
                    if (inBounds(newX, newY, mapComponent.getFog())) {
                        mapComponent.getFog()[newX][newY] = Fog.LIGHT;
                        UUID locationUuid = gameState.getEntities().getLocationUUID(newX, newY);
                        if (locationUuid != null) {
                            mapComponent.getKnownLocations().add(locationUuid);
                        }
                    }
                }
            }
        }
    }

    public static void revealCone(
        GameState gameState,
        MapComponent mapComponent,
        int x,
        int y,
        int revealDistance,
        int baseWidth,
        Direction direction) {
        Vector2 dir = direction.getDirVector();
        for (int dist = 1; dist <= revealDistance; dist++) {
            int centerX = (int)(x + dist * dir.x);
            int centerY = (int)(y + dist * dir.y);

            int currentWidth = baseWidth + (dist / 2); // Cone gets wider the farther it goes

            for (int offsetX = -currentWidth; offsetX <= currentWidth; offsetX++) {
                for (int offsetY = -currentWidth; offsetY <= currentWidth; offsetY++) {
                    // Only reveal roughly in cone shape
                    if (dist(offsetX, offsetY) <= currentWidth) {
                        int newX = centerX + offsetX;
                        int newY = centerY + offsetY;

                        if (inBounds(newX, newY, mapComponent.getFog())) {
                            mapComponent.getFog()[newX][newY] = Fog.LIGHT; // Clear fog
                            UUID locationUuid = gameState.getEntities().getLocationUUID(newX, newY);
                            if (locationUuid != null) {
                                mapComponent.getKnownLocations().add(locationUuid);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void mergeFog(Fog[][] fog, Fog[][] fog1) {
        // Assuming fog and fog1 have the same dimensions
        int width = fog.length;
        int height = fog[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Merge each corresponding element from fog and fog1
                fog[i][j] = mergeFog(fog[i][j], fog1[i][j]);
            }
        }
    }

    private static Fog mergeFog(Fog existingFog, Fog newFog) {
        if (newFog == Fog.NONE || existingFog == Fog.NONE) {
            return Fog.NONE;
        } else if (newFog == Fog.LIGHT || existingFog == Fog.LIGHT) {
            return Fog.LIGHT;
        }
        return Fog.DENSE;
    }
}
