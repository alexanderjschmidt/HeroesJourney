package heroes.journey.utils.worldgen;

import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.Random;

public class WaveFunctionCollapse {

    public static final WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();
    public static final List<Tile> baseTiles = new ArrayList<>();

    private final Tile[][] map;
    private final WeightedRandomPicker<Tile>[][] possibleTilesMap;

    public WaveFunctionCollapse(int size) {
        map = new Tile[size][size];
        possibleTilesMap = new WeightedRandomPicker[size][size];
        RandomWorldGenerator noiseGen = new RandomWorldGenerator(50, 5, .7f, true);
        int[][] noiseMap = noiseGen.generateMap(size);
        Tile[][] tileMap = convertToTileMap(noiseMap);
        smooth(tileMap, baseTiles);

        //possibleTiles.testDistribution();
        int max = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                //map[x][y] = tileMap[x][y];
                possibleTilesMap[x][y] = new WeightedRandomPicker<>(possibleTiles);
                possibleTilesMap[x][y].addItem(tileMap[x][y], possibleTilesMap[x][y].getTotalWeight() * 100);
                if (noiseMap[x][y] > max) {
                    max = noiseMap[x][y];
                }
            }
        }
        System.out.println("max noise: " + max);
        //possibleTilesMap[map.length / 2][map.length / 2].testDistribution();
    }

    public Tile[][] applyWaveFunctionCollapse() {
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
                        //System.out.println("Skipping " + ax + ", " + ay);
                        clearHole(ax, ay, 1);
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
            collapseInnerRandom(maxEntropyX, maxEntropyY);
            propagateConstraints(maxEntropyX, maxEntropyY);
        }
        System.out.println("Tiles: " + (map.length * map.length));
        return map;
    }

    private void clearHole(int x, int y, int radius) {
        //System.out.println("Clear Hole: " + x + " " + y);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < map.length && y + dy >= 0 && y + dy < map.length)
                    map[x + dx][y + dy] = null;
            }
        }
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < map.length && y + dy >= 0 && y + dy < map.length) {
                    possibleTilesMap[x + dx][y + dy] = getPossibleTiles(x + dx, y + dy);
                    //System.out.println("(" + (radius + dx) + ", " + (radius + dy) + ")");
                }
            }
        }
    }

    private boolean solve(Tile[][] map, WeightedRandomPicker<Tile>[][] currentPossibleTilesMap) {
        Stack<Placement> stack = new Stack<>();
        int x = 0, y = 0;
        int[][] tileIndex = new int[map.length][map.length];

        while (true) {
            //System.out.println(x + ", " + y + " " + currentPossibleTilesMap[x][y].size());
            if (y >= map.length) {
                System.out.println("Finished checking");
                return true; // Completed the grid
            }

            if (tileIndex[x][y] < currentPossibleTilesMap[x][y].size()) {
                Tile tile = currentPossibleTilesMap[x][y].get(tileIndex[x][y]);

                //System.out.println(isValid(map, tile, x, y));
                if (isValid(map, tile, x, y)) {
                    map[y][x] = tile;
                    stack.push(new Placement(x, y, tileIndex[x][y])); // Save state

                    // Move to next position
                    x = (x + 1) % map.length;
                    if (x == 0)
                        y++;
                    if (y == map.length) {
                        System.out.println(Arrays.deepToString(map));
                        return false; // No solution
                    }
                    tileIndex[x][y] = 0;
                    continue;
                }
                tileIndex[x][y]++;
            } else {
                // Backtrack: No valid tile, revert previous placement
                if (stack.isEmpty()) {
                    System.out.println(Arrays.deepToString(map));
                    return false; // No solution
                }

                Placement last = stack.pop();
                x = last.x;
                y = last.y;
                tileIndex[x][y] = last.tileIndex + 1;
                map[y][x] = null;
            }
        }
    }

    private boolean isValid(Tile[][] map, Tile tile, int x, int y) {
        boolean valid = true;
        if (x > 0 && y < map.length - 1 && map[x - 1][y + 1] != null)
            valid &= tile.aligns(Direction.NORTHWEST, map[x - 1][y + 1]);
        if (y < map.length - 1 && map[x][y + 1] != null)
            valid &= tile.aligns(Direction.NORTH, map[x][y + 1]);
        if (x < map.length - 1 && y < map.length - 1 && map[x + 1][y + 1] != null)
            valid &= tile.aligns(Direction.NORTHEAST, map[x + 1][y + 1]);
        if (x < map.length - 1 && map[x + 1][y] != null)
            valid &= tile.aligns(Direction.EAST, map[x + 1][y]);
        if (x < map.length - 1 && y > 0 && map[x + 1][y - 1] != null)
            valid &= tile.aligns(Direction.SOUTHEAST, map[x + 1][y - 1]);
        if (y > 0 && map[x][y - 1] != null)
            valid &= tile.aligns(Direction.SOUTH, map[x][y - 1]);
        if (x > 0 && y > 0 && map[x - 1][y - 1] != null)
            valid &= tile.aligns(Direction.SOUTHWEST, map[x - 1][y - 1]);
        if (x > 0 && map[x - 1][y] != null)
            valid &= tile.aligns(Direction.WEST, map[x - 1][y]);

        return valid;
    }

    private WeightedRandomPicker<Tile> getPossibleTiles(int x, int y) {
        WeightedRandomPicker<Tile> tiles = new WeightedRandomPicker<>(possibleTiles);

        checkNeighborFresh(tiles, x - 1, y + 1, Direction.NORTHWEST);
        checkNeighborFresh(tiles, x, y + 1, Direction.NORTH);
        checkNeighborFresh(tiles, x + 1, y + 1, Direction.NORTHEAST);
        checkNeighborFresh(tiles, x + 1, y, Direction.EAST);
        checkNeighborFresh(tiles, x + 1, y - 1, Direction.SOUTHEAST);
        checkNeighborFresh(tiles, x, y - 1, Direction.SOUTH);
        checkNeighborFresh(tiles, x - 1, y - 1, Direction.SOUTHWEST);
        checkNeighborFresh(tiles, x - 1, y, Direction.WEST);

        return tiles;
    }

    private void checkNeighborFresh(WeightedRandomPicker<Tile> tiles, int x, int y, Direction dir) {
        if (x >= 0 && x < map.length && y >= 0 && y < map.length && map[x][y] != null) {
            tiles.removeIf(tile -> !tile.aligns(dir, map[x][y]));
        }
    }

    private void propagateConstraints(int sx, int sy) {
        Queue<int[]> propagationQueue = new LinkedList<>();

        propagationQueue.add(new int[] {sx, sy});
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
            checkNeighbor(x - 1, y + 1, heroes.journey.utils.Direction.NORTHWEST, collapsedTile,
                propagationQueue);
            checkNeighbor(x, y + 1, heroes.journey.utils.Direction.NORTH, collapsedTile, propagationQueue);
            checkNeighbor(x + 1, y + 1, heroes.journey.utils.Direction.NORTHEAST, collapsedTile,
                propagationQueue);
            checkNeighbor(x + 1, y, heroes.journey.utils.Direction.EAST, collapsedTile, propagationQueue);
            checkNeighbor(x + 1, y - 1, heroes.journey.utils.Direction.SOUTHEAST, collapsedTile,
                propagationQueue);
            checkNeighbor(x, y - 1, heroes.journey.utils.Direction.SOUTH, collapsedTile, propagationQueue);
            checkNeighbor(x - 1, y - 1, heroes.journey.utils.Direction.SOUTHWEST, collapsedTile,
                propagationQueue);
            checkNeighbor(x - 1, y, heroes.journey.utils.Direction.WEST, collapsedTile, propagationQueue);
        }
    }

    private void checkNeighbor(
        int x,
        int y,
        Direction dir,
        Tile collapsedTile,
        Queue<int[]> propagationQueue) {
        if (x >= 0 && x < map.length && y >= 0 && y < map.length && map[x][y] == null) {
            possibleTilesMap[x][y].removeIf(tile -> !collapsedTile.aligns(dir, tile));
            if (possibleTilesMap[x][y].size() == 1) {
                collapseInnerRandom(x, y);
                propagationQueue.add(new int[] {x, y});
            }
        }
    }

    private void collapseInnerRandom(int x, int y) {
        Tile t = possibleTilesMap[x][y].getRandomItem();
        map[x][y] = t;
        if (map[x][y] == null) {
            throw new RuntimeException("Collapsed To Null");
        }
    }

    public void collapseTo(int x, int y, Tile tile) {
        map[x][y] = tile;
        possibleTilesMap[x][y] = new WeightedRandomPicker<>();
        possibleTilesMap[x][y].addItem(tile, tile.getWeight());
        propagateConstraints(x, y);
    }

    public void printMap(Tile[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

}

// Helper class to track tile placements
class Placement {
    int x, y, tileIndex;

    public Placement(int x, int y, int tileIndex) {
        this.x = x;
        this.y = y;
        this.tileIndex = tileIndex;
    }
}
