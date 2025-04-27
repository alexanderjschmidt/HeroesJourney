package heroes.journey.utils.worldgen;

import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.Random;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WaveFunctionCollapse {

    public static final WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();
    public static final List<Tile> baseTiles = new ArrayList<>();

    public static Tile[][] applyWaveFunctionCollapse(
        final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime) {
        int width = possibleTilesMapPrime.length;
        Tile[][] map = new Tile[width][width];
        WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[map.length][map.length];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (possibleTilesMapPrime[x][y] == null) {
                    possibleTilesMapPrime[x][y] = new WeightedRandomPicker<>(possibleTiles);
                }
                possibleTilesMap[x][y] = new WeightedRandomPicker(possibleTilesMapPrime[x][y]);
            }
        }
        outer:
        while (true) {
            // Step 1: Find the cell with the highest entropy (most definitive possibile value)
            int maxEntropyX = -1, maxEntropyY = -1;
            long maxEntropy = Integer.MIN_VALUE;
            int startX = Random.get().nextInt(map.length);
            int startY = Random.get().nextInt(map.length);
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map.length; y++) {
                    int ax = (startX + x) % map.length;
                    int ay = (startY + y) % map.length;
                    if (map[ax][ay] == null && possibleTilesMap[ax][ay].isEmpty()) {
                        clearHole(map, possibleTilesMap, possibleTilesMapPrime, ax, ay, 3);
                        continue outer;
                    }
                    // TODO update this to use the highest value in the weighted random picker not the total weight
                    // ie it contains a plains tile that is weighted at 1000000000
                    if (map[ax][ay] == null && possibleTilesMap[ax][ay].getTotalWeight() > maxEntropy &&
                        !possibleTilesMap[ax][ay].isEmpty()) {
                        maxEntropy = possibleTilesMap[ax][ay].getTotalWeight();
                        maxEntropyX = ax;
                        maxEntropyY = ay;
                    }

                }
            }
            // System.out.println("Highest Entrophy: " + maxEntropy + ": " + maxEntropyX + ", " + maxEntropyY);
            // If all cells are collapsed, exit the loop
            if (maxEntropy == Integer.MIN_VALUE) {
                break;
            }

            // Step 2: Collapse the chosen cell to one of its possible states
            collapseInnerRandom(map, possibleTilesMap, maxEntropyX, maxEntropyY);
            propagateConstraints(map, possibleTilesMap, maxEntropyX, maxEntropyY);
        }
        // System.out.println("Tiles: " + (map.length * map.length));
        return map;
    }

    private static void clearHole(
        Tile[][] map,
        WeightedRandomPicker<Tile>[][] possibleTilesMap,
        final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime,
        int x,
        int y,
        int radius) {
        //System.out.println("Clear Hole: " + x + " " + y);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < map.length && y + dy >= 0 && y + dy < map.length) {
                    map[x + dx][y + dy] = null;
                }
            }
        }
        updateSurrounding(map, possibleTilesMap, possibleTilesMapPrime, x, y, radius);
    }

    private static void updateSurrounding(
        Tile[][] map,
        WeightedRandomPicker<Tile>[][] possibleTilesMap,
        final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime,
        int x,
        int y,
        int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < possibleTilesMap.length && y + dy >= 0 &&
                    y + dy < possibleTilesMap.length) {
                    possibleTilesMap[x + dx][y + dy] = getPossibleTiles(map, possibleTilesMap,
                        possibleTilesMapPrime, x + dx, y + dy);
                    //System.out.println("(" + (radius + dx) + ", " + (radius + dy) + ")");
                }
            }
        }
    }

    private static WeightedRandomPicker<Tile> getPossibleTiles(
        Tile[][] map,
        WeightedRandomPicker<Tile>[][] possibleTilesMap,
        final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime,
        int x,
        int y) {
        WeightedRandomPicker<Tile> tiles = new WeightedRandomPicker<>(possibleTilesMapPrime[x][y]);

        updateBasedOnNeighbor(map, tiles, x - 1, y + 1, Direction.NORTHWEST);
        updateBasedOnNeighbor(map, tiles, x, y + 1, Direction.NORTH);
        updateBasedOnNeighbor(map, tiles, x + 1, y + 1, Direction.NORTHEAST);
        updateBasedOnNeighbor(map, tiles, x + 1, y, Direction.EAST);
        updateBasedOnNeighbor(map, tiles, x + 1, y - 1, Direction.SOUTHEAST);
        updateBasedOnNeighbor(map, tiles, x, y - 1, Direction.SOUTH);
        updateBasedOnNeighbor(map, tiles, x - 1, y - 1, Direction.SOUTHWEST);
        updateBasedOnNeighbor(map, tiles, x - 1, y, Direction.WEST);

        return tiles;
    }

    private static void updateBasedOnNeighbor(
        Tile[][] map,
        WeightedRandomPicker<Tile> tiles,
        int nx,
        int ny,
        Direction dir) {
        if (nx >= 0 && nx < map.length && ny >= 0 && ny < map.length && map[nx][ny] != null) {
            tiles.removeIf(tile -> !tile.aligns(dir, map[nx][ny]));
        }
    }

    private static void propagateConstraints(
        Tile[][] map,
        WeightedRandomPicker<Tile>[][] possibleTilesMap,
        int sx,
        int sy) {
        Queue<int[]> propagationQueue = new LinkedList<>();

        propagationQueue.add(new int[]{sx, sy});
        // Propagate the constraints to neighboring cells
        int processed = 0;
        while (!propagationQueue.isEmpty()) {
            //System.out.println("Queue size: " + propagationQueue.size() + " Processed: " + processed++);
            int[] position = propagationQueue.poll();
            int x = position[0];
            int y = position[1];

            Tile collapsedTile = map[x][y];
            //System.out.println(map[x][y] + " " + x + ", " + y);

            // Check neighbors and remove tiles that don’t match edges
            checkNeighbor(map, possibleTilesMap, x - 1, y + 1, Direction.NORTHWEST, collapsedTile,
                propagationQueue);
            checkNeighbor(map, possibleTilesMap, x, y + 1, Direction.NORTH, collapsedTile, propagationQueue);
            checkNeighbor(map, possibleTilesMap, x + 1, y + 1, Direction.NORTHEAST, collapsedTile,
                propagationQueue);
            checkNeighbor(map, possibleTilesMap, x + 1, y, Direction.EAST, collapsedTile, propagationQueue);
            checkNeighbor(map, possibleTilesMap, x + 1, y - 1, Direction.SOUTHEAST, collapsedTile,
                propagationQueue);
            checkNeighbor(map, possibleTilesMap, x, y - 1, Direction.SOUTH, collapsedTile, propagationQueue);
            checkNeighbor(map, possibleTilesMap, x - 1, y - 1, Direction.SOUTHWEST, collapsedTile,
                propagationQueue);
            checkNeighbor(map, possibleTilesMap, x - 1, y, Direction.WEST, collapsedTile, propagationQueue);
        }
    }

    private static void checkNeighbor(
        Tile[][] map,
        WeightedRandomPicker<Tile>[][] possibleTilesMap,
        int x,
        int y,
        Direction dir,
        Tile collapsedTile,
        Queue<int[]> propagationQueue) {
        if (x >= 0 && x < map.length && y >= 0 && y < map.length && map[x][y] == null) {
            possibleTilesMap[x][y].removeIf(tile -> !collapsedTile.aligns(dir, tile));
            if (possibleTilesMap[x][y].size() == 1) {
                collapseInnerRandom(map, possibleTilesMap, x, y);
                propagationQueue.add(new int[]{x, y});
            }
        }
    }

    private static void collapseInnerRandom(
        Tile[][] map,
        WeightedRandomPicker<Tile>[][] possibleTilesMap,
        int x,
        int y) {
        Tile t = possibleTilesMap[x][y].getRandomItem();
        possibleTilesMap[x][y].clear();
        possibleTilesMap[x][y].addItem(t, t.getWeight());
        map[x][y] = t;
        if (map[x][y] == null) {
            throw new RuntimeException("Collapsed To Null");
        }
    }
}
