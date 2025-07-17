package heroes.journey.utils.worldgen.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import heroes.journey.GameState;
import heroes.journey.components.LocationComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.Position;
import heroes.journey.registries.TileManager;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Random;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.RoadPathing;
import heroes.journey.utils.worldgen.MapGenerationException;

public class MapGenUtils {

    public static boolean surroundedBySame(Tile[][] tileMap, int x, int y) {
        Tile t = tileMap[x][y];
        return (!inBounds(x - 1, y + 1) || t == tileMap[x - 1][y + 1]) &&
            (!inBounds(x - 1, y) || t == tileMap[x - 1][y]) &&
            (!inBounds(x - 1, y - 1) || t == tileMap[x - 1][y - 1]) &&
            (!inBounds(x, y - 1) || t == tileMap[x][y - 1]) &&
            (!inBounds(x, y + 1) || t == tileMap[x][y + 1]) &&
            (!inBounds(x + 1, y - 1) || t == tileMap[x + 1][y - 1]) &&
            (!inBounds(x + 1, y) || t == tileMap[x + 1][y]) &&
            (!inBounds(x + 1, y + 1) || t == tileMap[x + 1][y + 1]);
    }

    public static boolean surroundedBySame(Tile[][] tileMap, int x, int y, int radius) {
        Tile center = tileMap[x][y];

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                // Skip the center tile itself
                if (dx == 0 && dy == 0)
                    continue;

                int nx = x + dx;
                int ny = y + dy;

                if (inBounds(nx, ny)) {
                    if (tileMap[nx][ny] != center) {
                        return false;
                    }
                }
                // If out of bounds, treat it as a match (like your original code)
            }
        }

        return true;
    }

    public static void buildRoad(
        GameState gameState,
        Tile connector,
        Position location1,
        Position location2) {
        Cell path = new RoadPathing().getPath(gameState.getMap(), location1.getX(), location1.getY(),
            location2.getX(), location2.getY());
        while (path != null) {
            gameState.getMap().setTile(path.x, path.y, connector);
            path = path.parent;
        }
    }

    public static boolean isLandTile(Tile tile) {
        return tile == TileManager.BASE_TILES.get(Ids.BASE_TILE_PLAINS) ||
            tile == TileManager.BASE_TILES.get(Ids.BASE_TILE_HILLS) ||
            tile == TileManager.BASE_TILES.get(Ids.BASE_TILE_SAND);
    }

    public static boolean inBounds(int x, int y) {
        return inBounds(x, y, GameState.global().getWidth(), GameState.global().getHeight());
    }

    public static boolean inBounds(int x, int y, int width, int height) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public static boolean inBounds(int x, int y, Object[][] array) {
        return x >= 0 && y >= 0 && x < array.length && y < array[x].length;
    }

    public static Predicate<Position> isLandSurrounded(Tile[][] map) {
        return pos -> inBounds(pos.getX(), pos.getY()) && isLandTile(map[pos.getX()][pos.getY()]) &&
            surroundedBySame(map, pos.getX(), pos.getY());
    }

    public static Predicate<Position> isFarFromFeatures(GameState gameState, int minDistance) {
        return pos -> {
            for (int dx = -minDistance; dx <= minDistance; dx++) {
                for (int dy = -minDistance; dy <= minDistance; dy++) {
                    int x = pos.getX() + dx;
                    int y = pos.getY() + dy;
                    if (!inBounds(x, y))
                        continue;

                    if (gameState.getMap().getEnvironment(x, y) != null) {
                        Position other = new Position(x, y);
                        if (pos.distanceTo(other) < minDistance)
                            return false;
                    }
                }
            }
            return true;
        };
    }

    public static Position findTileNear(
        Position center,
        int minDist,
        int maxDist,
        Predicate<Position> isValid) {
        return findTileNear(center, minDist, maxDist, 0, isValid);
    }

    public static Position findTileNear(
        Position center,
        int minDist,
        int maxDist,
        int randomSampling,
        Predicate<Position> isValid) {
        if (randomSampling <= 0) {
            for (int r = minDist; r <= maxDist; r++) {
                List<Position> ring = new ArrayList<>();
                for (int dx = -r; dx <= r; dx++) {
                    for (int dy = -r; dy <= r; dy++) {
                        if (Math.abs(dx) != r && Math.abs(dy) != r)
                            continue; // only outer ring
                        ring.add(new Position(center.getX() + dx, center.getY() + dy));
                    }
                }
                Collections.shuffle(ring, Random.get()); // randomize order of checking
                for (Position pos : ring) {
                    if (isValid.test(pos))
                        return pos;
                }
            }
        } else {
            for (int i = 0; i < randomSampling; i++) {
                int dx = Random.get().nextInt(-maxDist, maxDist);
                int dy = Random.get().nextInt(-maxDist, maxDist);
                if (Math.abs(dx) + Math.abs(dy) < minDist)
                    continue;

                Position pos = new Position(center.getX() + dx, center.getY() + dy);
                if (isValid.test(pos))
                    return pos;
            }
            throw new MapGenerationException(
                "Could not find a randomly sampled location for " + center + " with max dist " + maxDist +
                    " and min dist " + minDist + " after " + randomSampling + " tries.");
        }

        return center; // fallback
    }

    /**
     * Returns a Position in the region that is at least minDist away from all existing features.
     * Scans outward in rings from the region center, shuffling each ring for variance.
     * Falls back to the center if no valid tile is found.
     */
    public static Position poisonDiskSample(
        GameWorld world,
        int minDist,
        RegionComponent region,
        PositionComponent regionPos) {
        // Gather all existing feature positions
        List<Position> featurePositions = new ArrayList<>();
        List<UUID> ids = LocationComponent.get(world);
        for (UUID id : ids) {
            PositionComponent pos = PositionComponent.get(world, id);
            featurePositions.add(new Position(pos.getX(), pos.getY()));
        }
        // All tiles in the region
        List<Position> regionTiles = new ArrayList<>(region.getTiles());
        // Compute max possible radius (Manhattan distance from center to farthest tile)
        int maxRadius = 0;
        for (Position p : regionTiles) {
            int dist = Math.abs(regionPos.getX() - p.getX()) + Math.abs(regionPos.getY() - p.getY());
            if (dist > maxRadius)
                maxRadius = dist;
        }
        // For each ring, starting at radius 0 (center), scan outward
        for (int r = 0; r <= maxRadius; r++) {
            List<Position> ring = new ArrayList<>();
            for (Position p : regionTiles) {
                int dist = Math.abs(regionPos.getX() - p.getX()) + Math.abs(regionPos.getY() - p.getY());
                if (dist == r) {
                    ring.add(p);
                }
            }
            // Shuffle the ring for random starting point
            Collections.shuffle(ring, Random.get());
            for (Position candidate : ring) {
                boolean farEnough = true;
                for (Position existing : featurePositions) {
                    if (candidate.distanceTo(existing) < minDist) {
                        farEnough = false;
                        break;
                    }
                }
                if (farEnough) {
                    return candidate;
                }
            }
        }
        return null;
    }
}
