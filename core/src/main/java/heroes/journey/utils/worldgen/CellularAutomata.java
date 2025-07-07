package heroes.journey.utils.worldgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heroes.journey.Ids;
import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Tile;

public class CellularAutomata {

    private static int tileCount = 6;

    public static int[][] mirrorX(int[][] grid, boolean xAxis) {
        if (xAxis) {
            for (int x = 0; x < grid.length / 2; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    grid[grid.length - x - 1][y] = grid[x][y];
                }
            }
        } else {
            for (int x = 0; x < grid.length / 2; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    grid[x][y] = grid[grid.length - x - 1][y];
                }
            }
        }
        return grid;
    }

    public static int[][] mirrorY(int[][] grid, boolean yAxis) {
        if (yAxis) {
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length / 2; y++) {
                    grid[x][grid[0].length - y - 1] = grid[x][y];
                }
            }
        } else {
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length / 2; y++) {
                    grid[x][y] = grid[x][grid[0].length - y - 1];
                }
            }
        }
        return grid;
    }

    public static Tile[][] convertToTileMap(int[][] noiseMap) {
        Tile[][] tileMap = new Tile[noiseMap.length][noiseMap[0].length];
        for (int x = 0; x < noiseMap.length; x++) {
            for (int y = 0; y < noiseMap.length; y++) {
                tileMap[x][y] = getTile(noiseMap[x][y]);
            }
        }
        return tileMap;
    }

    private static Tile getTile(int nextInt) {
        if (nextInt < 20)
            return TileManager.BASE_TILES.get(Ids.BASE_TILE_WATER);
        else if (nextInt < 35)
            return TileManager.BASE_TILES.get(Ids.BASE_TILE_SAND);
        else if (nextInt < 80)
            return TileManager.BASE_TILES.get(Ids.BASE_TILE_PLAINS);
        else
            return TileManager.BASE_TILES.get(Ids.BASE_TILE_HILLS);
    }

    public static void smooth(Tile[][] map, List<Tile> baseTiles) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Map<Tile,Integer> tiles = new HashMap<>();
                for (Tile t : baseTiles) {
                    tiles.put(t, 0);
                }
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        Tile t = map[Math.max(0, Math.min(map.length - 1, k))][Math.max(0,
                            Math.min(map[i].length - 1, l))];
                        tiles.put(t, tiles.get(t) + 1);
                    }
                }
                int max = 0;
                Tile maxTile = map[i][j];
                for (Tile t : tiles.keySet()) {
                    if (tiles.get(t) > max) {
                        max = tiles.get(t);
                        maxTile = t;
                    }
                }
                map[i][j] = maxTile;
            }
        }
    }

    /*
    private static void smoothSandAndWater(Tile[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].ordinal() <= 1) {
                    int[] tiles = new int[tileCount];
                    for (int k = i - 1; k <= i + 1; k++) {
                        tiles[map[Math.max(0, Math.min(map.length - 1, k))][Math.max(0, Math.min(map[i].length - 1, j))].ordinal()]++;
                    }
                    for (int l = j - 1; l <= j + 1; l++) {
                        tiles[map[Math.max(0, Math.min(map.length - 1, i))][Math.max(0, Math.min(map[i].length - 1, l))].ordinal()]++;
                    }
                    if (tiles[map[i][j].ordinal()] <= 3)
                        map[i][j] = TileManager.getTile(tiles[3] > tiles[2] ? 3 : 2);
                }
            }
        }
    }*/

    public void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

}
