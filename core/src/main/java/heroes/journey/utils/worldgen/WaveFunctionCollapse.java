package heroes.journey.utils.worldgen;

import java.util.LinkedList;
import java.util.Queue;

import heroes.journey.initializers.base.Tiles;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;

public class WaveFunctionCollapse {

    public static WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();

    private final Tile[][] map;
    private final Tile[][] lockedIn;
    private final WeightedRandomPicker<Tile>[][] possibleTilesMap;

    public WaveFunctionCollapse(int size) {
        map = new Tile[size][size];
        lockedIn = new Tile[size][size];
        possibleTilesMap = new WeightedRandomPicker[size][size];
        RandomWorldGenerator noiseGen = new RandomWorldGenerator(40, 6, 1f, true);
        int[][] noiseMap = noiseGen.generateMap(size);
        int max = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[x][y] = getTile(noiseMap[x][y]);
                possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                possibleTilesMap[x][y].addItem(map[x][y], 100);
                if (noiseMap[x][y] > max) {
                    max = noiseMap[x][y];
                }
            }
        }
        System.out.println(max);

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if ((isSame(x + 1, y, map[x][y]) + isSame(x - 1, y, map[x][y]) + isSame(x, y + 1, map[x][y]) +
                    isSame(x, y - 1, map[x][y])) == 4) {
                    continue;
                }
                clearHole(x, y, 0);
            }
        }
        //possibleTiles.testDistribution();
    }

    private int isSame(int x, int y, Tile tile) {
        if (x >= 0 && x < map.length && y >= 0 && y < map.length) {
            if (map[x][y] == null)
                return 1;
            return map[x][y] == tile ? 1 : 0;
        }
        return 1;
    }

    private static Tile getTile(int nextInt) {
        if (nextInt < 20)
            return Tiles.WATER;
        else if (nextInt < 35)
            return Tiles.SAND;
        else if (nextInt < 80)
            return Tiles.PLAINS;
        else
            return Tiles.HILLS;
    }

    public Tile[][] applyWaveFunctionCollapse() {
        outer:
        while (true) {
            // Step 1: Find the cell with the lowest entropy (fewest possible states)
            int minEntropyX = -1, minEntropyY = -1;
            int minEntropy = Integer.MAX_VALUE;
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map.length; y++) {
                    if (map[x][y] == null && possibleTilesMap[x][y].isEmpty()) {
                        clearHole(x, y, 1);
                        continue outer;
                    }
                    if (map[x][y] == null && possibleTilesMap[x][y].size() < minEntropy) {
                        minEntropy = possibleTilesMap[x][y].size();
                        minEntropyX = x;
                        minEntropyY = y;
                    }

                }
            }
            //System.out.println("Lowest Entrophy: " + minEntropy + ": " + minEntropyX + ", " + minEntropyY);
            // If all cells are collapsed, exit the loop
            if (minEntropy == Integer.MAX_VALUE) {
                break;
            }

            // Step 2: Collapse the chosen cell to one of its possible states
            collapseInnerRandom(minEntropyX, minEntropyY);
            propagateConstraints(minEntropyX, minEntropyY);
        }
        return map;
    }

    private void clearHole(int x, int y, int radius) {
        //System.out.println("Clear Hole: " + minEntropyX + " " + minEntropyY);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < map.length && y + dy >= 0 && y + dy < map.length &&
                    lockedIn[x + dx][y + dy] == null)
                    map[x + dx][y + dy] = null;
            }
        }
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < map.length && y + dy >= 0 && y + dy < map.length)
                    possibleTilesMap[x + dx][y + dy] = getPossibleTiles(x + dx, y + dy);
            }
        }
    }

    private WeightedRandomPicker<Tile> getPossibleTiles(int x, int y) {
        WeightedRandomPicker<Tile> tiles = new WeightedRandomPicker<>(possibleTiles);

        checkNeighborFresh(tiles, x, y + 1, Direction.NORTH);
        checkNeighborFresh(tiles, x + 1, y, Direction.EAST);
        checkNeighborFresh(tiles, x, y - 1, Direction.SOUTH);
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
            checkNeighbor(x, y + 1, heroes.journey.utils.Direction.NORTH, collapsedTile, propagationQueue);
            checkNeighbor(x + 1, y, heroes.journey.utils.Direction.EAST, collapsedTile, propagationQueue);
            checkNeighbor(x, y - 1, heroes.journey.utils.Direction.SOUTH, collapsedTile, propagationQueue);
            checkNeighbor(x - 1, y, heroes.journey.utils.Direction.WEST, collapsedTile, propagationQueue);
        }
    }

    private void checkNeighbor(
        int nx,
        int ny,
        Direction dir,
        Tile collapsedTile,
        Queue<int[]> propagationQueue) {
        if (nx >= 0 && nx < map.length && ny >= 0 && ny < map.length && map[nx][ny] == null) {
            possibleTilesMap[nx][ny].removeIf(tile -> !collapsedTile.aligns(dir, tile));
            if (possibleTilesMap[nx][ny].size() == 1) {
                collapseInnerRandom(nx, ny);
                propagationQueue.add(new int[] {nx, ny});
            }
        }
    }

    private void collapseInnerRandom(int x, int y) {
        map[x][y] = possibleTilesMap[x][y].getRandomItem();
        //System.out.println("collapsed: " + map[x][y] + " " + x + ", " + y);
        if (map[x][y] == null) {
            throw new RuntimeException("Collapsed To Null");
        }
    }

    public void collapseTo(int x, int y, Tile tile) {
        map[x][y] = tile;
        lockedIn[x][y] = tile;
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
