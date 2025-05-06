package heroes.journey.tilemap.wavefunctiontiles;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.initializers.base.Tiles;
import heroes.journey.tilemap.TileManager;
import heroes.journey.utils.Direction;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;
import lombok.Getter;

public abstract class Tile {

    private final Map<Direction,Terrain> neighbors;
    private final boolean addToDefaultTiles;

    @Getter private final Terrain terrain;

    @Getter private final long weight;

    public Tile(Terrain terrain, int baseWeight, boolean addToDefaultTiles) {
        this.terrain = terrain;
        neighbors = new HashMap<>();
        this.weight = baseWeight == 0 ? 1 : baseWeight;
        this.addToDefaultTiles = addToDefaultTiles;
        TileManager.get().add(this);
    }

    public Tile(Terrain terrain, int baseWeight) {
        this(terrain, baseWeight, true);
    }

    public Tile add(Direction direction, Terrain terrain) {
        neighbors.put(direction, terrain);
        if (neighbors.size() == 8 && addToDefaultTiles) {
            WaveFunctionCollapse.possibleTiles.addItem(this, weight);
        }
        return this;
    }

    public boolean aligns(Direction direction, Tile tile) {
        if (tile == null || tile == Tiles.HOLE)
            return true;
        switch (direction) {
            case NORTHWEST -> {
                return tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.NORTHWEST);
            }
            case NORTH -> {
                return tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.NORTHWEST) &&
                    tile.neighbors.get(Direction.SOUTH) == neighbors.get(Direction.NORTH) &&
                    tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.NORTHEAST);
            }
            case NORTHEAST -> {
                return tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.NORTHEAST);
            }
            case EAST -> {
                return tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.NORTHEAST) &&
                    tile.neighbors.get(Direction.WEST) == neighbors.get(Direction.EAST) &&
                    tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.SOUTHEAST);
            }
            case SOUTHEAST -> {
                return tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.SOUTHEAST);
            }
            case SOUTH -> {
                return tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.SOUTHWEST) &&
                    tile.neighbors.get(Direction.NORTH) == neighbors.get(Direction.SOUTH) &&
                    tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.SOUTHEAST);
            }
            case SOUTHWEST -> {
                return tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.SOUTHWEST);
            }
            case WEST -> {
                return tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.NORTHWEST) &&
                    tile.neighbors.get(Direction.EAST) == neighbors.get(Direction.WEST) &&
                    tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.SOUTHWEST);
            }
        }
        throw new RuntimeException("Invalid Direction");
    }

    public int alignment(Direction direction, Tile tile) {
        switch (direction) {
            case NORTHWEST -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.SOUTHEAST) + direction);
                return tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.NORTHWEST) ? 1 : 0;
            }
            case NORTH -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.SOUTH) + direction + ", ");
                return
                    (tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.NORTHWEST) ? 1 : 0) +
                        (tile.neighbors.get(Direction.SOUTH) == neighbors.get(Direction.NORTH) ? 1 : 0) + (
                        tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.NORTHEAST) ?
                            1 :
                            0);
            }
            case NORTHEAST -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.SOUTHWEST) + direction);
                return (tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.NORTHEAST) ?
                    1 :
                    0);
            }
            case EAST -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.WEST) + direction + ", ");
                return
                    (tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.NORTHEAST) ? 1 : 0) +
                        (tile.neighbors.get(Direction.WEST) == neighbors.get(Direction.EAST) ? 1 : 0) +
                        (tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.SOUTHEAST) ?
                            1 :
                            0);
            }
            case SOUTHEAST -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.NORTHWEST) + direction);
                return (tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.SOUTHEAST) ?
                    1 :
                    0);
            }
            case SOUTH -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.NORTH) + direction + ", ");
                return
                    (tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.SOUTHWEST) ? 1 : 0) +
                        (tile.neighbors.get(Direction.NORTH) == neighbors.get(Direction.SOUTH) ? 1 : 0) + (
                        tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.SOUTHEAST) ?
                            1 :
                            0);
            }
            case SOUTHWEST -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.NORTHEAST) + direction);
                return (tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.SOUTHWEST) ?
                    1 :
                    0);
            }
            case WEST -> {
                //System.out.println("wants " + tile.neighbors.get(Direction.EAST) + direction + ", ");
                return
                    (tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.NORTHWEST) ? 1 : 0) +
                        (tile.neighbors.get(Direction.EAST) == neighbors.get(Direction.WEST) ? 1 : 0) +
                        (tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.SOUTHWEST) ?
                            1 :
                            0);
            }
        }
        throw new RuntimeException("Invalid Direction");
    }

    public abstract void render(SpriteBatch batch, float elapsed, int x, int y);

    public String toString() {
        return "" + neighbors.get(Direction.NORTHWEST).toString().charAt(1) +
            neighbors.get(Direction.NORTH).toString().charAt(1) +
            neighbors.get(Direction.NORTHEAST).toString().charAt(1) + '\n' +
            neighbors.get(Direction.WEST).toString().charAt(1) + " " +
            neighbors.get(Direction.EAST).toString().charAt(1) + '\n' +
            neighbors.get(Direction.SOUTHWEST).toString().charAt(1) +
            neighbors.get(Direction.SOUTH).toString().charAt(1) +
            neighbors.get(Direction.SOUTHEAST).toString().charAt(1);
    }
}
