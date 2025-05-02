package heroes.journey.utils.worldgen;

import static heroes.journey.initializers.base.Map.inBounds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import heroes.journey.initializers.base.Tiles;
import heroes.journey.tilemap.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.Random;

@SuppressWarnings("unchecked")
public class WaveFunctionCollapse {

    public static final WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();
    public static final List<Tile> baseTiles = new ArrayList<>();

    public static Tile[][] applyWaveFunctionCollapse(
        final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime,
        boolean clearHole) {
        WFCData data = new WFCData(possibleTilesMapPrime);
        outer:
        while (true) {
            // Step 1: Find the cell with the highest entropy (most definitive possibile value)
            int maxEntropyX = -1, maxEntropyY = -1;
            long maxEntropy = Integer.MIN_VALUE;
            int startX = Random.get().nextInt(data.size());
            int startY = Random.get().nextInt(data.size());
            for (int x = 0; x < data.size(); x++) {
                for (int y = 0; y < data.size(); y++) {
                    int ax = (startX + x) % data.size();
                    int ay = (startY + y) % data.size();
                    if (data.map[ax][ay] == null && data.possibleTilesMap[ax][ay].isEmpty()) {
                        if (clearHole)
                            clearHole(data, ax, ay, 1);
                        else
                            data.possibleTilesMap[ax][ay].addItem(Tiles.HOLE, 10);
                    } else if (data.map[ax][ay] == null && data.possibleTilesMap[ax][ay].size() >= 3 &&
                        clearHole) {
                        maxEntropy = Long.MAX_VALUE - data.possibleTilesMap[ax][ay].size();
                        maxEntropyX = ax;
                        maxEntropyY = ay;
                    }
                    // TODO update this to use the highest value in the weighted random picker not the total weight
                    // ie it contains a plains tile that is weighted at 1000000000
                    else if (data.map[ax][ay] == null &&
                        data.possibleTilesMap[ax][ay].getTotalWeight() > maxEntropy &&
                        !data.possibleTilesMap[ax][ay].isEmpty()) {
                        maxEntropy = data.possibleTilesMap[ax][ay].getTotalWeight();
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
            collapseInnerRandom(data, maxEntropyX, maxEntropyY);
            propagateConstraints(data, maxEntropyX, maxEntropyY);
        }
        for (int x = 0; x < data.size(); x++) {
            for (int y = 0; y < data.size(); y++) {
                if (data.map[x][y] == Tiles.HOLE) {
                    //map[x][y] = bestFit(map, x, y);
                }
            }
        }
        // System.out.println("Tiles: " + (data.size() * data.size()));
        return data.map;
    }

    private static Tile bestFit(Tile[][] map, int x, int y) {
        return TileManager.get()
            .stream()
            .max(Comparator.comparingInt(t -> getAlignmentScore(t, map, x, y)))
            .orElse(Tiles.HOLE);
    }

    private static int getAlignmentScore(Tile tile, Tile[][] map, int x, int y) {
        int alignment = 0;
        System.out.println("Trying to find " + x + ", " + y);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.NORTHWEST);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.NORTH);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.NORTHEAST);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.EAST);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.SOUTHEAST);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.SOUTH);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.SOUTHWEST);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.WEST);
        return alignment;
    }

    private static int getAlignmentScoreIndividual(Tile tile, Tile[][] map, int x, int y, Direction dir) {
        int nx = (int)(x + dir.getDirVector().x);
        int ny = (int)(y + dir.getDirVector().y);
        if (inBounds(nx, ny, map) && map[nx][ny] != null) {
            return tile.alignment(dir, map[nx][ny]);
        }
        return 0;
    }

    private static void clearHole(WFCData data, int x, int y, int radius) {
        //System.out.println("Clear Hole: " + x + " " + y);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (inBounds(x + dx, y + dy, data.map)) {
                    data.triedTiles[x + dx][y + dy].add(data.map[x + dx][y + dy]);
                    data.map[x + dx][y + dy] = null;
                }
            }
        }
        updateSurrounding(data, x, y, radius);
    }

    private static void updateSurrounding(WFCData data, int x, int y, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (inBounds(x + dx, y + dy, data.possibleTilesMap)) {
                    data.possibleTilesMap[x + dx][y + dy] = getPossibleTiles(data, x + dx, y + dy);
                    //System.out.println("(" + (radius + dx) + ", " + (radius + dy) + ")");
                }
            }
        }
    }

    private static WeightedRandomPicker<Tile> getPossibleTiles(WFCData data, int x, int y) {
        WeightedRandomPicker<Tile> tiles = new WeightedRandomPicker<>(data.possibleTilesMapPrime[x][y]);
        for (Tile t : data.triedTiles[x][y]) {
            tiles.halveWeight(t);
        }
        if (tiles.isEmpty()) {
            tiles.addItem(Tiles.HOLE, 10);
        }

        updateBasedOnNeighbor(data, tiles, x - 1, y + 1, Direction.NORTHWEST);
        updateBasedOnNeighbor(data, tiles, x, y + 1, Direction.NORTH);
        updateBasedOnNeighbor(data, tiles, x + 1, y + 1, Direction.NORTHEAST);
        updateBasedOnNeighbor(data, tiles, x + 1, y, Direction.EAST);
        updateBasedOnNeighbor(data, tiles, x + 1, y - 1, Direction.SOUTHEAST);
        updateBasedOnNeighbor(data, tiles, x, y - 1, Direction.SOUTH);
        updateBasedOnNeighbor(data, tiles, x - 1, y - 1, Direction.SOUTHWEST);
        updateBasedOnNeighbor(data, tiles, x - 1, y, Direction.WEST);

        return tiles;
    }

    private static void updateBasedOnNeighbor(
        WFCData data,
        WeightedRandomPicker<Tile> tiles,
        int nx,
        int ny,
        Direction dir) {
        if (inBounds(nx, ny, data.map) && data.map[nx][ny] != null) {
            tiles.removeIf(tile -> !tile.aligns(dir, data.map[nx][ny]));
        }
    }

    private static void propagateConstraints(WFCData data, int sx, int sy) {
        Queue<int[]> propagationQueue = new LinkedList<>();

        propagationQueue.add(new int[] {sx, sy});
        // Propagate the constraints to neighboring cells
        int processed = 0;
        while (!propagationQueue.isEmpty()) {
            //System.out.println("Queue size: " + propagationQueue.size() + " Processed: " + processed++);
            int[] position = propagationQueue.poll();
            int x = position[0];
            int y = position[1];

            Tile collapsedTile = data.map[x][y];
            //System.out.println(map[x][y] + " " + x + ", " + y);

            // Check neighbors and remove tiles that don’t match edges
            checkNeighbor(data, x - 1, y + 1, Direction.NORTHWEST, collapsedTile, propagationQueue);
            checkNeighbor(data, x, y + 1, Direction.NORTH, collapsedTile, propagationQueue);
            checkNeighbor(data, x + 1, y + 1, Direction.NORTHEAST, collapsedTile, propagationQueue);
            checkNeighbor(data, x + 1, y, Direction.EAST, collapsedTile, propagationQueue);
            checkNeighbor(data, x + 1, y - 1, Direction.SOUTHEAST, collapsedTile, propagationQueue);
            checkNeighbor(data, x, y - 1, Direction.SOUTH, collapsedTile, propagationQueue);
            checkNeighbor(data, x - 1, y - 1, Direction.SOUTHWEST, collapsedTile, propagationQueue);
            checkNeighbor(data, x - 1, y, Direction.WEST, collapsedTile, propagationQueue);
        }
    }

    private static void checkNeighbor(
        WFCData data,
        int x,
        int y,
        Direction dir,
        Tile collapsedTile,
        Queue<int[]> propagationQueue) {
        if (inBounds(x, y, data.map) && data.map[x][y] == null) {
            data.possibleTilesMap[x][y].removeIf(tile -> !collapsedTile.aligns(dir, tile));
            if (data.possibleTilesMap[x][y].size() == 1) {
                collapseInnerRandom(data, x, y);
                propagationQueue.add(new int[] {x, y});
            }
        }
    }

    private static void collapseInnerRandom(WFCData data, int x, int y) {
        Tile t = data.possibleTilesMap[x][y].getRandomItem();
        data.possibleTilesMap[x][y].clear();
        data.possibleTilesMap[x][y].addItem(t, t.getWeight());
        data.map[x][y] = t;
        if (data.map[x][y] == null) {
            throw new RuntimeException("Collapsed To Null");
        }
    }

    private static class WFCData {
        public WeightedRandomPicker<Tile>[][] possibleTilesMap;
        public final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime;
        public ArrayList<Tile>[][] triedTiles;
        public Tile[][] map;

        public WFCData(WeightedRandomPicker<Tile>[][] possibleTilesMapPrime) {
            this.possibleTilesMapPrime = possibleTilesMapPrime;
            int width = possibleTilesMapPrime.length;
            map = new Tile[width][width];
            possibleTilesMap = new WeightedRandomPicker[width][width];
            triedTiles = new ArrayList[width][width];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (possibleTilesMapPrime[x][y] == null) {
                        possibleTilesMapPrime[x][y] = new WeightedRandomPicker<>(possibleTiles);
                    }
                    possibleTilesMap[x][y] = new WeightedRandomPicker(possibleTilesMapPrime[x][y]);
                    triedTiles[x][y] = new ArrayList();
                }
            }
        }

        public int size() {
            return map.length;
        }

    }
}
