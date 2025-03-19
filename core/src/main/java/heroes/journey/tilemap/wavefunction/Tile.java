package heroes.journey.tilemap.wavefunction;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.utils.Direction;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;

public abstract class Tile {

    private final Map<Direction,Terrain> neighbors;

    private final Terrain terrain;

    private final int weight;

    public Tile(Terrain terrain, int weight) {
        this.terrain = terrain;
        neighbors = new HashMap<>();
        this.weight = weight;
    }

    public Tile(Terrain terrain) {
        this(terrain, 10);
    }

    public Tile add(Direction direction, Terrain terrain) {
        neighbors.put(direction, terrain);
        if (neighbors.size() == 8)
            WaveFunctionCollapse.possibleTiles.addItem(this, weight);
        return this;
    }

    public boolean aligns(Direction direction, Tile tile) {
        switch (direction) {
            case NORTH -> {
                return tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.NORTHWEST) &&
                    tile.neighbors.get(Direction.SOUTH) == neighbors.get(Direction.NORTH) &&
                    tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.NORTHEAST);
            }
            case EAST -> {
                return tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.NORTHEAST) &&
                    tile.neighbors.get(Direction.WEST) == neighbors.get(Direction.EAST) &&
                    tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.SOUTHEAST);
            }
            case SOUTH -> {
                return tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.SOUTHWEST) &&
                    tile.neighbors.get(Direction.NORTH) == neighbors.get(Direction.SOUTH) &&
                    tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.SOUTHEAST);
            }
            case WEST -> {
                return tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.NORTHWEST) &&
                    tile.neighbors.get(Direction.EAST) == neighbors.get(Direction.WEST) &&
                    tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.SOUTHWEST);
            }
        }
        throw new RuntimeException("Invalid Direction");
    }

    public abstract void render(SpriteBatch batch, float elapsed, int x, int y);

    public Terrain getTerrain() {
        return terrain;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return terrain.toString();
    }
}
