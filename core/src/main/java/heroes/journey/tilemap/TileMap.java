package heroes.journey.tilemap;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.RenderBounds;
import heroes.journey.utils.ai.pathfinding.TileNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static heroes.journey.registries.Registries.TerrainManager;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;

public class TileMap implements IndexedGraph<TileNode> {

    @Getter
    private final int width, height;
    @Setter
    @Getter
    private Tile[][] tileMap;
    @Setter
    @Getter
    private Tile[][] environment;
    @Setter
    @Getter
    int[][] regionMap;
    private float elapsed = 0;

    // For Pathfinding
    @Getter
    private TileNode[][] nodes;

    public TileMap(int mapSize) {
        width = mapSize;
        height = mapSize;
        tileMap = new Tile[width][height];
        environment = new Tile[width][height];
        regionMap = new int[width][height];
        updateGraph();
    }

    public void render(SpriteBatch batch, float delta) {
        elapsed += delta;

        RenderBounds bounds = RenderBounds.get();

        for (int x = bounds.x0; x < bounds.x1; x++) {
            for (int y = bounds.y0; y < bounds.y1; y++) {
                if (tileMap[x][y] != null)
                    tileMap[x][y].render(batch, elapsed, x, y);
                if (environment[x][y] != null)
                    environment[x][y].render(batch, elapsed, x, y);
            }
        }
    }

    public Terrain get(int x, int y) {
        if (!inBounds(x, y, width, height)) {
            return tileMap[Math.max(0, Math.min(width - 1, x))][Math.max(0,
                Math.min(height - 1, y))].getTerrain();
        } else {
            return tileMap[x][y] == null ? null : tileMap[x][y].getTerrain();
        }
    }

    public void setEnvironment(int x, int y, Tile tile) {
        environment[x][y] = tile;
    }

    public Terrain getEnvironment(int x, int y) {
        if (!inBounds(x, y, width, height)) {
            Tile env = environment[Math.max(0, Math.min(width - 1, x))][Math.max(0, Math.min(height - 1, y))];
            return env == null ? null : env.getTerrain();
        } else {
            return environment[x][y] != null ? environment[x][y].getTerrain() : null;
        }
    }

    public void setTile(int x, int y, Tile tile) {
        tileMap[x][y] = tile;
    }

    public TileMap clone() {
        TileMap map = new TileMap(width);
        map.tileMap = Arrays.copyOf(tileMap, tileMap.length);
        map.environment = Arrays.copyOf(environment, environment.length);
        long startTime = System.nanoTime();
        for (int x = 0; x < width; x++) {
            map.tileMap[x] = Arrays.copyOf(tileMap[x], tileMap[x].length);
            map.environment[x] = Arrays.copyOf(environment[x], environment[x].length);
        }
        Utils.logTime("connect nodes", startTime, 10);
        return map;
    }

    public int getTerrainCost(int x, int y, UUID selected) {
        return (tileMap[x][y] == null ? 1 : tileMap[x][y].getTerrain().terrainCost) +
            (environment[x][y] == null ? 0 : environment[x][y].getTerrain().terrainCost);
    }

    private void updateGraph() {
        nodes = new TileNode[width][height];
        long startTime = System.nanoTime();
        // Create Nodes
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new TileNode(x, y);
            }
        }
        Utils.logTime("Create nodes " + width, startTime, 10);

        // Connect Nodes (4-directional movement)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x > 0)
                    nodes[x][y].getConnections().add(new DefaultConnection<>(nodes[x][y], nodes[x - 1][y]));
                if (x < width - 1)
                    nodes[x][y].getConnections().add(new DefaultConnection<>(nodes[x][y], nodes[x + 1][y]));
                if (y > 0)
                    nodes[x][y].getConnections().add(new DefaultConnection<>(nodes[x][y], nodes[x][y - 1]));
                if (y < height - 1)
                    nodes[x][y].getConnections().add(new DefaultConnection<>(nodes[x][y], nodes[x][y + 1]));
            }
        }
        Utils.logTime("connect nodes " + height, startTime, 10);
    }

    @Override
    public int getIndex(TileNode node) {
        return node.x * tileMap.length + node.y;
    }

    @Override
    public int getNodeCount() {
        return tileMap.length * tileMap.length;
    }

    @Override
    public Array<Connection<TileNode>> getConnections(TileNode fromNode) {
        return fromNode.getConnections();
    }

    public TileMapSaveData getSaveData() {
        // Save Terrain -> Int Terrain Map
        Map<String, Integer> terrainMap = new HashMap<>(TerrainManager.size());
        int count = 0;
        terrainMap.put("null", count++);
        for (String terrain : TerrainManager.keySet()) {
            terrainMap.put(terrain, count++);
        }
        Map<String, Integer> tileToIntMap = new HashMap<>();
        int tileCount = 0;
        int[][] map = new int[width][height];
        int[][] env = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String layout = tileMap[x][y].getLayout(terrainMap);
                if (!tileToIntMap.containsKey(layout)) {
                    tileToIntMap.put(layout, tileCount++);
                }
                map[x][y] = tileToIntMap.get(layout);

                String layoutEnv;
                if (environment[x][y] == null) {
                    layoutEnv = "0,0,0,0,0,0,0,0,0";
                } else {
                    layoutEnv = environment[x][y].getLayout(terrainMap);
                }
                if (!tileToIntMap.containsKey(layoutEnv)) {
                    tileToIntMap.put(layoutEnv, tileCount++);
                }
                env[x][y] = tileToIntMap.get(layoutEnv);
            }
        }
        return new TileMapSaveData(terrainMap, tileToIntMap, map, env);
    }

    public void load(TileMapSaveData saveData) {
        Map<Integer, String> intToTileMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : saveData.getTileToIntMap().entrySet()) {
            intToTileMap.put(entry.getValue(), entry.getKey());
        }
        Map<Integer, String> intToTerrainMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : saveData.getTerrainMap().entrySet()) {
            intToTerrainMap.put(entry.getValue(), entry.getKey());
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String[] layoutStr = intToTileMap.get(saveData.getMap()[x][y]).split(",");
                // Convert layoutStr to ints
                String[] layout = new String[layoutStr.length];
                for (int i = 0; i < layoutStr.length; i++) {
                    layout[i] = intToTerrainMap.get(Integer.parseInt(layoutStr[i]));
                }
                assert (tileMap[x][y] == TileManager.get().getTileMatch(layout));
                tileMap[x][y] = TileManager.get().getTileMatch(layout);

                String[] layoutStrEnv = intToTileMap.get(saveData.getEnvironment()[x][y]).split(",");
                // Convert layoutStr to ints
                String[] layoutEnv = new String[layoutStrEnv.length];
                for (int i = 0; i < layoutStrEnv.length; i++) {
                    layoutEnv[i] = intToTerrainMap.get(Integer.parseInt(layoutStrEnv[i]));
                }
                assert (environment[x][y] == TileManager.get().getTileMatch(layoutEnv));
                environment[x][y] = TileManager.get().getTileMatch(layoutEnv);
            }
        }
    }
}

