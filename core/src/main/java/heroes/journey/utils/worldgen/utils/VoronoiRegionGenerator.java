package heroes.journey.utils.worldgen.utils;

import static heroes.journey.mods.Registries.BiomeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.modlib.utils.Position;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.Random;

public class VoronoiRegionGenerator {

    public static List<List<Position>> generateInwardRegionRings(
        Position center,
        float baseRadius,
        List<Integer> regionsPerRing,
        List<Boolean> offsetRings,
        float jitterFactor
        // e.g., 0.2f = 20% jitter of base radius
    ) {
        List<List<Position>> ringedRegions = new ArrayList<>();

        int ringCount = regionsPerRing.size();
        float maxRadius = baseRadius * (ringCount - 1);

        for (int ringIndex = 0; ringIndex < ringCount; ringIndex++) {
            int regionsInRing = regionsPerRing.get(ringIndex);
            boolean offset = offsetRings.get(ringIndex);

            // Apply regular stagger offset if requested
            float angleOffset = offset ? (float)(Math.PI * 2 / regionsInRing / 2f) : 0f;

            // Add random angle rotation to rotate the whole ring
            float angleOffsetJitter = (float)(Math.random() * Math.PI * 2);
            angleOffset += angleOffsetJitter;
            float radius = maxRadius - ringIndex * baseRadius;

            List<Position> ring = new ArrayList<>();

            for (int i = 0; i < regionsInRing; i++) {
                float angle = (float)((i * Math.PI * 2 / regionsInRing) + angleOffset);

                float x = center.getX() + (float)Math.cos(angle) * radius;
                float y = center.getY() + (float)Math.sin(angle) * radius;

                // Add jitter
                x += (float)((Math.random() - 0.5f) * baseRadius * jitterFactor);
                y += (float)((Math.random() - 0.5f) * baseRadius * jitterFactor);

                ring.add(new Position((int)x, (int)y));
            }

            ringedRegions.add(ring);
        }

        return ringedRegions;
    }

    public static int[][] generateRegionMap(
        boolean[][] isLand,
        List<Position> initialSeeds,
        int desiredRegionCount) {
        List<Position> regionSeeds = new ArrayList<>(initialSeeds);
        int totalLandTiles = countLandTiles(isLand);

        // Adaptive Poisson-disc radius
        double estimatedAreaPerRegion = ((double)totalLandTiles / desiredRegionCount) * (4 / 5.0);
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

    public static void buildRegionsFromMap(
        GameState gameState,
        List<List<Position>> centerPoints,
        int[][] regionMap) {
        GameWorld world = gameState.getWorld();
        Map<Integer,UUID> regionIdMap = new HashMap<>();
        int width = regionMap.length;
        int height = regionMap[0].length;

        // === Step 1: Create Regions with ring index ===
        int regionId = 0;
        for (int ringIndex = 0; ringIndex < centerPoints.size(); ringIndex++) {
            List<Position> ring = centerPoints.get(ringIndex);
            for (int i = 0; i < ring.size(); i++) {
                UUID regionUUId = world.getEntityFactory().createRegion(ringIndex, i);
                regionIdMap.put(regionId, regionUUId);
                regionId++;
            }
        }
        world.basicProcess();

        // === Step 2: Add tiles to each region ===
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int id = regionMap[x][y];
                if (id == -1)
                    continue;
                RegionComponent region = RegionComponent.get(world, regionIdMap.get(id));
                region.addTile(new Position(x, y));
            }
        }

        // === Step 3: Finalize center and assign biome ===
        for (UUID id : regionIdMap.values()) {
            RegionComponent region = RegionComponent.get(world, id);
            int sumX = 0, sumY = 0;
            for (Position p : region.getTiles()) {
                sumX += p.getX();
                sumY += p.getY();
            }
            int count = region.getTiles().size();
            world.edit(id).create(PositionComponent.class).setPos(sumX / count, sumY / count).sync();

            int randomBiome = Random.get().nextInt(BiomeManager.size());
            region.setBiome(BiomeManager.values().stream().toList().get(randomBiome));
        }
        world.basicProcess();

        // === Build regionIdToRingIndex ===
        Map<Integer,Integer> regionIdToRingIndex = new HashMap<>();
        int id = 0;
        for (int ring = 0; ring < centerPoints.size(); ring++) {
            for (int i = 0; i < centerPoints.get(ring).size(); i++) {
                regionIdToRingIndex.put(id++, ring);
            }
        }

        // === Same-ring wraparound connections ===
        List<List<Integer>> ringRegionIds = new ArrayList<>();
        int currentId = 0;
        for (List<Position> ring : centerPoints) {
            List<Integer> idsInRing = new ArrayList<>();
            for (int i = 0; i < ring.size(); i++) {
                idsInRing.add(currentId++);
            }
            ringRegionIds.add(idsInRing);
        }

        for (List<Integer> ring : ringRegionIds) {
            int size = ring.size();
            for (int i = 0; i < size; i++) {
                int thisId = ring.get(i);
                int leftId = ring.get((i - 1 + size) % size);
                int rightId = ring.get((i + 1) % size);

                UUID thisUUID = regionIdMap.get(thisId);
                UUID leftUUID = regionIdMap.get(leftId);
                UUID rightUUID = regionIdMap.get(rightId);

                RegionComponent thisRegion = RegionComponent.get(world, thisUUID);
                RegionComponent left = RegionComponent.get(world, leftUUID);
                RegionComponent right = RegionComponent.get(world, rightUUID);

                thisRegion.addNeighbor(leftUUID);
                left.addNeighbor(thisUUID);

                thisRegion.addNeighbor(rightUUID);
                right.addNeighbor(thisUUID);
            }
        }

        // Add connections to regions touching
        int[][] directions8 = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

        UUID[][] regionUUIDMap = new UUID[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                currentId = regionMap[x][y];
                if (currentId == -1)
                    continue;
                UUID currentUUID = regionIdMap.get(currentId);
                regionUUIDMap[x][y] = currentUUID;

                RegionComponent region = RegionComponent.get(world, currentUUID);
                int currentRing = regionIdToRingIndex.get(currentId);

                for (int[] dir : directions8) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (nx < 0 || ny < 0 || nx >= width || ny >= height)
                        continue;

                    int neighborId = regionMap[nx][ny];
                    if (neighborId == -1 || neighborId == currentId)
                        continue;
                    UUID neighborUUID = regionIdMap.get(neighborId);

                    int neighborRing = regionIdToRingIndex.get(neighborId);
                    if (Math.abs(currentRing - neighborRing) == 1) {
                        region.addNeighbor(neighborUUID);
                        RegionComponent.get(world, neighborUUID).addNeighbor(currentUUID);
                    }
                }
            }
        }
        gameState.getMap().setRegionMap(regionUUIDMap);
        world.basicProcess();
    }
}
