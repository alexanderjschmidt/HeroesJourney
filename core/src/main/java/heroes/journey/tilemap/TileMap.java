package heroes.journey.tilemap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.GameCamera;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.actions.Action;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;

public class TileMap {

    private int width, height;
    private Tile[][] tileMap;
    private Tile[][] environment;
    private float elapsed = 0;

    public TileMap(int mapSize) {
        width = mapSize;
        height = mapSize;
        tileMap = new Tile[width][height];
        environment = new Tile[width][height];
        GameCamera.get().setZoom();
    }

    public void render(SpriteBatch batch, float delta) {
        elapsed += delta;

        int xo = (int)(GameCamera.get().position.x / GameCamera.get().getSize());
        int yo = (int)(GameCamera.get().position.y / GameCamera.get().getSize());

        int x0 = (int)Math.max(Math.floor(xo - GameCamera.get().getxLow()), 0);
        int y0 = (int)Math.max(Math.floor(yo - GameCamera.get().getyLow()), 0);
        int x1 = (int)Math.min(Math.floor(xo + GameCamera.get().getxHigh()), width);
        int y1 = (int)Math.min(Math.floor(yo + GameCamera.get().getyHigh()), height);

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setEnvironment(int x, int y, Tile tile) {
        environment[x][y] = tile;
    }

    public ActionTerrain getEnvironment(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            Tile env = environment[Math.max(0, Math.min(width - 1, x))][Math.max(0, Math.min(height - 1, y))];
            return env == null ? null : (ActionTerrain)env.getTerrain();
        } else {
            return environment[x][y] == null ? null : (ActionTerrain)environment[x][y].getTerrain();
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
        return ((ActionTerrain)tile.getTerrain()).getActions();
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

    public int getTerrainCost(int x, int y, Entity selected) {
        return 1;
    }

    public void setTileMap(Tile[][] tileMap) {
        this.tileMap = tileMap;
    }

    public void setEnvironment(Tile[][] environment) {
        this.environment = environment;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public Tile[][] getEnvironment() {
        return environment;
    }
}
