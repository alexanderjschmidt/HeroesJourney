package heroes.journey.utils.worldgen;

import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.Random;

import java.util.*;

@SuppressWarnings("unchecked")
public class WaveFunctionCollapse {

    public static final WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();
    public static final List<Tile> baseTiles = new ArrayList<>();

    public static Tile[][] applyWaveFunctionCollapse(
        final WeightedRandomPicker<Tile>[][] possibleTilesMapPrime) {
        WFCData data = new WFCData(possibleTilesMapPrime);
        Stack<Decision> stack = new Stack<>();

        outer:
        while (true) {
            // Step 1: Find the cell with the highest entropy (most definitive possible value)
            int minEntropyX = -1, minEntropyY = -1;
            long minEntropy = Long.MAX_VALUE;
            int startX = Random.get().nextInt(data.size());
            int startY = Random.get().nextInt(data.size());
            for (int x = 0; x < data.size(); x++) {
                for (int y = 0; y < data.size(); y++) {
                    int ax = (startX + x) % data.size();
                    int ay = (startY + y) % data.size();
                    if (data.map[ax][ay] == null) {
                        if (data.possibleTilesMap[ax][ay].isEmpty()) {
                            // Contradiction detected: trigger backtracking
                            if (!backtrack(data, stack)) {
                                System.out.println("Failed to find suitable map layout");
                                throw new MapGenerationException("Failed to find suitable map layout");
                            }
                            continue outer; // restart entropy search after backtrack
                        } else {
                            long weight = data.possibleTilesMap[ax][ay].getTotalWeight();
                            if (weight < minEntropy) {
                                minEntropy = weight;
                                minEntropyX = ax;
                                minEntropyY = ay;
                            }
                        }
                    }
                }
            }

            if (minEntropy == Long.MAX_VALUE)
                break;

            Tile choice = data.possibleTilesMap[minEntropyX][minEntropyY].getRandomItem();
            if (choice == null)
                System.out.println(data.possibleTilesMap[minEntropyX][minEntropyY]);
            if (!isCompatibleWithNeighbors(data, minEntropyX, minEntropyY, choice)) {
                throw new RuntimeException(
                    "Not Compatible " + choice + " " + minEntropyX + ", " + minEntropyY);
            }

            Decision decision = new Decision(minEntropyX, minEntropyY, choice,
                data.possibleTilesMap[minEntropyX][minEntropyY]);
            collapseCell(data, minEntropyX, minEntropyY, choice);
            //System.out.println("collapsed main " + minEntropyX + ", " + minEntropyY);
            propagateConstraints(data, minEntropyX, minEntropyY, decision.deltas);
            stack.push(decision);
        }

        return data.map;
    }

    private static void propagateConstraints(WFCData data, int sx, int sy, List<ActionDelta> deltas) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sx, sy});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];
            Tile collapsed = data.map[x][y];

            for (Direction dir : Direction.values()) {
                if (dir == Direction.NODIRECTION)
                    continue;
                int nx = (int) (x + dir.getDirVector().x);
                int ny = (int) (y + dir.getDirVector().y);
                if (!inBounds(nx, ny, data.map) || data.map[nx][ny] != null)
                    continue;

                List<Tile> toRemove = new ArrayList<>();
                for (Tile t : data.possibleTilesMap[nx][ny]) {
                    if (!collapsed.aligns(dir, t)) {
                        toRemove.add(t);
                    }
                }

                if (!toRemove.isEmpty()) {
                    for (Tile t : toRemove)
                        data.possibleTilesMap[nx][ny].remove(t);
                    ActionDelta delta = new ActionDelta(nx, ny, toRemove);
                    if (data.possibleTilesMap[nx][ny].size() == 1) {
                        Tile t = data.possibleTilesMap[nx][ny].getRandomItem();
                        collapseCell(data, nx, ny, t);
                        delta.autoCollapsedTo = t; // <== record the collapse
                        //("collapsed prop " + nx + ", " + ny);
                        queue.add(new int[]{nx, ny});
                    }
                    deltas.add(delta);
                }
            }
        }
    }

    private static void collapseCell(WFCData data, int x, int y, Tile t) {
        data.possibleTilesMap[x][y].clear();
        data.possibleTilesMap[x][y].addItem(t, t.getWeight());
        data.map[x][y] = t;
    }

    private static boolean backtrack(WFCData data, Stack<Decision> stack) {
        //System.out.println("backtrack");
        while (!stack.isEmpty()) {
            Decision d = stack.pop();
            //System.out.println("pop " + d.x + ", " + d.y + " " + data.map[d.x][d.y]);
            undoDeltas(data, d.deltas);
            data.map[d.x][d.y] = null;
            data.possibleTilesMap[d.x][d.y].clear();
            for (Tile t : d.remainingOptions) {
                data.possibleTilesMap[d.x][d.y].addItem(t, t.getWeight());
            }
            if (!d.remainingOptions.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void undoDeltas(WFCData data, List<ActionDelta> deltas) {
        for (int i = deltas.size() - 1; i >= 0; i--) {
            ActionDelta delta = deltas.get(i);
            //System.out.println("undo " + delta.x + ", " + delta.y + " " + delta.removedTiles);
            for (Tile t : delta.removedTiles) {
                data.possibleTilesMap[delta.x][delta.y].addItem(t, t.getWeight());
            }
            if (delta.autoCollapsedTo != null) {
                data.map[delta.x][delta.y] = null;
            }
        }
    }

    private static boolean isCompatibleWithNeighbors(WFCData data, int x, int y, Tile candidate) {
        for (Direction dir : Direction.values()) {
            if (dir == Direction.NODIRECTION)
                continue;

            int nx = (int) (x + dir.getDirVector().x);
            int ny = (int) (y + dir.getDirVector().y);

            if (!inBounds(nx, ny, data.map))
                continue;

            Tile neighbor = data.map[nx][ny];
            if (neighbor != null) {
                // Check if candidate aligns with neighbor and vice versa
                if (!candidate.aligns(dir, neighbor) || !neighbor.aligns(dir.inverse(), candidate)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean inBounds(int x, int y, Tile[][] map) {
        return x >= 0 && y >= 0 && x < map.length && y < map[0].length;
    }

    private static class WFCData {
        public WeightedRandomPicker<Tile>[][] possibleTilesMap;
        public Tile[][] map;

        public WFCData(WeightedRandomPicker<Tile>[][] possibleTilesMapPrime) {
            int width = possibleTilesMapPrime.length;
            this.possibleTilesMap = possibleTilesMapPrime;
            map = new Tile[width][width];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (possibleTilesMap[x][y] == null) {
                        possibleTilesMap[x][y] = new WeightedRandomPicker<>(possibleTiles);
                    }
                }
            }
        }

        public int size() {
            return map.length;
        }

    }

    private static class ActionDelta {
        int x, y;
        List<Tile> removedTiles = new ArrayList<>();
        Tile autoCollapsedTo = null;

        ActionDelta(int x, int y, List<Tile> removed) {
            this.x = x;
            this.y = y;
            this.removedTiles.addAll(removed);
        }
    }

    private static class Decision {
        int x, y;
        Tile chosen;
        List<ActionDelta> deltas;
        List<Tile> remainingOptions;

        Decision(int x, int y, Tile chosen, List<Tile> remainingOptions) {
            this.x = x;
            this.y = y;
            this.chosen = chosen;
            this.remainingOptions = new ArrayList<>(remainingOptions);
            this.remainingOptions.remove(chosen);
            this.deltas = new ArrayList<>();
        }
    }
}
