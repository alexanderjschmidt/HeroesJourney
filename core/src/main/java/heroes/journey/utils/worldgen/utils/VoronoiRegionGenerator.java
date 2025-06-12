package heroes.journey.utils.worldgen.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import heroes.journey.entities.Position;
import heroes.journey.registries.RegionManager;
import heroes.journey.tilemap.Region;
import heroes.journey.utils.Random;

public class VoronoiRegionGenerator {

    public static int[][] generateRegionMap(
        boolean[][] isLand,
        List<Position> initialSeeds,
        int desiredRegionCount) {
        List<Position> regionSeeds = new ArrayList<>(initialSeeds);
        int totalLandTiles = countLandTiles(isLand);

        // Adaptive Poisson-disc radius
        double estimatedAreaPerRegion = (double)totalLandTiles / desiredRegionCount;
        int adaptiveRadius = (int)Math.sqrt(estimatedAreaPerRegion / Math.PI);
        adaptiveRadius = Math.max(6, Math.min(24, adaptiveRadius)); // Clamp radius to [6, 24]

        System.out.printf("Using adaptive Poisson-disc radius: %d (based on %d land tiles and %d regions)\n",
            adaptiveRadius, totalLandTiles, desiredRegionCount);

        if (regionSeeds.size() < desiredRegionCount) {
            Set<String> existing = new HashSet<>();
            for (Position p : regionSeeds)
                existing.add(p.getX() + "," + p.getY());

            List<Position> poissonSeeds = generatePoissonDiskLandPositions(isLand, desiredRegionCount * 3,
                adaptiveRadius);
            for (Position p : poissonSeeds) {
                if (regionSeeds.size() >= desiredRegionCount)
                    break;
                String key = p.getX() + "," + p.getY();
                if (!existing.contains(key)) {
                    regionSeeds.add(p);
                    existing.add(key);
                }
            }
        }

        return assignTilesToClosestSeed(isLand, regionSeeds);
    }

    private static int countLandTiles(boolean[][] isLand) {
        int count = 0;
        for (int x = 0; x < isLand.length; x++) {
            for (int y = 0; y < isLand[0].length; y++) {
                if (isLand[x][y])
                    count++;
            }
        }
        return count;
    }

    private static int[][] assignTilesToClosestSeed(boolean[][] isLand, List<Position> seeds) {
        int[][] regionMap = new int[isLand.length][isLand[0].length];
        for (int x = 0; x < isLand.length; x++)
            Arrays.fill(regionMap[x], -1);

        for (int x = 0; x < isLand.length; x++) {
            for (int y = 0; y < isLand[0].length; y++) {
                if (!isLand[x][y])
                    continue;

                double closestDist = Double.MAX_VALUE;
                int closestSeedIndex = -1;
                for (int i = 0; i < seeds.size(); i++) {
                    Position s = seeds.get(i);
                    double d = distSq(x, y, s.getX(), s.getY());
                    if (d < closestDist) {
                        closestDist = d;
                        closestSeedIndex = i;
                    }
                }

                regionMap[x][y] = closestSeedIndex;
            }
        }

        return regionMap;
    }

    private static double distSq(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2, dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    public static List<Position> generatePoissonDiskLandPositions(boolean[][] isLand, int count, int radius) {
        List<Position> accepted = new ArrayList<>();

        for (int attempts = 0; attempts < count * 10 && accepted.size() < count; attempts++) {
            int x = Random.get().nextInt(isLand.length);
            int y = Random.get().nextInt(isLand[0].length);
            if (!isLand[x][y])
                continue;

            boolean farEnough = true;
            for (Position p : accepted) {
                if (distSq(x, y, p.getX(), p.getY()) < radius * radius) {
                    farEnough = false;
                    break;
                }
            }

            if (farEnough) {
                accepted.add(new Position(x, y));
            }
        }

        return accepted;
    }

    public static void buildRegionsFromMap(int[][] regionMap) {
        Map<Integer,Region> regionMapObj = new HashMap<>();
        int width = regionMap.length;
        int height = regionMap[0].length;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int id = regionMap[x][y];
                if (id == -1)
                    continue;
                Region region = regionMapObj.computeIfAbsent(id, Region::new);
                region.addTile(new Position(x, y));
            }
        }

        for (Region region : regionMapObj.values()) {
            region.finalizeCenter();
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int id = regionMap[x][y];
                if (id == -1)
                    continue;
                Region region = regionMapObj.get(id);
                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                        int neighborId = regionMap[nx][ny];
                        if (neighborId != -1 && neighborId != id) {
                            region.addNeighbor(neighborId);
                        }
                    }
                }
            }
        }

        for (Region region : regionMapObj.values()) {
            RegionManager.get().put(region.id, region);
        }
    }
}
