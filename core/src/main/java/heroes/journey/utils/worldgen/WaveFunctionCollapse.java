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
            // Step 1: Find the cell with the highest entropy (most definitive possible value)
            int minEntropyX = -1, minEntropyY = -1;
            long minEntropy = Long.MAX_VALUE;
            for (int x = 0; x < data.size(); x++) {
                for (int y = 0; y < data.size(); y++) {
                    if (data.map[x][y] == null && data.possibleTilesMap[x][y].isEmpty()) {
                        if (clearHole) {
                            clearHole(data, x, y, 2);
                            continue outer;
                        } else
                            data.possibleTilesMap[x][y].addItem(Tiles.HOLE, 10);
                    }
                    // TODO update this to use the highest value in the weighted random picker not the total weight
                    // ie it contains a plains tile that is weighted at 1000000000
                    else if (data.map[x][y] == null &&
                        data.possibleTilesMap[x][y].getTotalWeight() < minEntropy) {
                        minEntropy = data.possibleTilesMap[x][y].getTotalWeight();
                        minEntropyX = x;
                        minEntropyY = y;
                    }

                }
            }
            // System.out.println("Highest Entrophy: " + minEntropy + ": " + minEntropyX + ", " + minEntropyY);
            // If all cells are collapsed, exit the loop
            if (minEntropy == Long.MAX_VALUE) {
                break;
            }

            // Step 2: Collapse the chosen cell to one of its possible states
            collapseInnerRandom(data, minEntropyX, minEntropyY);
            propagateConstraints(data, minEntropyX, minEntropyY);
        }
        int holes = 0;
        for (int x = 0; x < data.size(); x++) {
            for (int y = 0; y < data.size(); y++) {
                if (data.map[x][y] == Tiles.HOLE) {
                    data.map[x][y] = bestFit(data.map, x, y);
                    holes++;
                }
            }
        }
        System.out.println("Holes: " + holes + " in " + (data.size() * data.size()) + " tiles");
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
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.NORTH);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.EAST);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.SOUTH);
        alignment += getAlignmentScoreIndividual(tile, map, x, y, Direction.WEST);
        //System.out.println("Trying to find " + x + ", " + y + " " + alignment);
        return alignment;
    }

    private static int getAlignmentScoreIndividual(Tile tile, Tile[][] map, int x, int y, Direction dir) {
        int nx = (int)(x + dir.getDirVector().x);
        int ny = (int)(y + dir.getDirVector().y);
        if (inBounds(nx, ny, map) && map[nx][ny] != null) {
            return tile.alignment(dir, map[nx][ny]);
        }
        return 1;
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
        if (tiles.isEmpty() || tiles.getTotalWeight() == tiles.size()) {
            tiles.addItem(Tiles.HOLE, 10);
        }

        updateBasedOnNeighbor(data, tiles, x - 1, y + 1, heroes.journey.utils.Direction.NORTHWEST);
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
