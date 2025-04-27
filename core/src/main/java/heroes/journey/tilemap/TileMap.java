package heroes.journey.tilemap;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import heroes.journey.GameCamera;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.actions.Action;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.ai.pathfinding.TileNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileMap implements IndexedGraph<TileNode> {

    @Getter
    private final int width, height;
    private Tile[][] tileMap;
    private Tile[][] environment;
    private float elapsed = 0;

    private TileNode[][] nodes;

    public TileMap(int mapSize) {
        width = mapSize;
        height = mapSize;
        tileMap = new Tile[width][height];
        environment = new Tile[width][height];
        updateGraph();
        GameCamera.get().setZoom();
    }

    public void render(SpriteBatch batch, float delta) {
        elapsed += delta;

        int xo = (int) (GameCamera.get().position.x / GameCamera.get().getSize());
        int yo = (int) (GameCamera.get().position.y / GameCamera.get().getSize());

        int x0 = (int) Math.max(Math.floor(xo - GameCamera.get().getXLow()), 0);
        int y0 = (int) Math.max(Math.floor(yo - GameCamera.get().getYLow()), 0);
        int x1 = (int) Math.min(Math.floor(xo + GameCamera.get().getXHigh()), width);
        int y1 = (int) Math.min(Math.floor(yo + GameCamera.get().getYHigh()), height);

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                if (tileMap[x][y] != null)
                    tileMap[x][y].render(batch, elapsed, x, y);
                if (environment[x][y] != null)
                    environment[x][y].render(batch, elapsed, x, y);
            }
        }
    }

    public Terrain get(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return tileMap[Math.max(0, Math.min(width - 1, x))][Math.max(0,
                Math.min(height - 1, y))].getTerrain();
        } else {
            return tileMap[x][y] == null ? null : tileMap[x][y].getTerrain();
        }
    }

    public void setEnvironment(int x, int y, Tile tile) {
        environment[x][y] = tile;
    }

    public ActionTerrain getEnvironment(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            Tile env = environment[Math.max(0, Math.min(width - 1, x))][Math.max(0, Math.min(height - 1, y))];
            return env == null ? null : (ActionTerrain) env.getTerrain();
        } else {
            return environment[x][y] == null ? null : (ActionTerrain) environment[x][y].getTerrain();
        }
    }

    public void setTile(int x, int y, Tile tile) {
        tileMap[x][y] = tile;
    }

    public List<Action> getTileActions(int x, int y) {
        Tile tile = environment[x][y];
        if (tile == null) {
            ArrayList<Action> options = new ArrayList<Action>(1);
            options.add(BaseActions.wait);
            return options;
        }
        return ((ActionTerrain) tile.getTerrain()).getActions();
    }

    public TileMap clone(EntityManager entityManager) {
        TileMap map = new TileMap(width);
        map.tileMap = Arrays.copyOf(tileMap, tileMap.length);
        map.environment = Arrays.copyOf(environment, environment.length);
        for (int x = 0; x < width; x++) {
            map.tileMap[x] = Arrays.copyOf(tileMap[x], tileMap[x].length);
            map.environment[x] = Arrays.copyOf(environment[x], environment[x].length);
        }
        return map;
    }

    public int getTerrainCost(int x, int y, Integer selected) {
        return (tileMap[x][y] == null ? 1 : tileMap[x][y].getTerrain().getTerrainCost()) +
            (environment[x][y] == null ? 0 : environment[x][y].getTerrain().getTerrainCost());
    }

    public void setTileMap(Tile[][] tileMap) {
        this.tileMap = tileMap;
    }

    public void setEnvironment(Tile[][] environment) {
        this.environment = environment;
    }

    private void updateGraph() {
        nodes = new TileNode[width][height];
        // Create Nodes
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new TileNode(x, y);
            }
        }

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
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public Tile[][] getEnvironment() {
        return environment;
    }

    public TileNode[][] getNodes() {
        return nodes;
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
}

