package heroes.journey.tilemap.wavefunctiontiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.tilemap.TileManager;
import heroes.journey.utils.Direction;
import heroes.journey.utils.worldgen.utils.WaveFunctionCollapse;
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

    public Tile add(Direction direction, Terrain terrain) {
        neighbors.put(direction, terrain);
        if (neighbors.size() == 8 && addToDefaultTiles) {
            WaveFunctionCollapse.possibleTiles.addItem(this, weight);
        }
        return this;
    }

    public boolean aligns(Direction direction, Tile tile) {
        if (tile == null)
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
                    (tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.NORTHWEST) ? 2 : 0) +
                        (tile.neighbors.get(Direction.SOUTH) == neighbors.get(Direction.NORTH) ? 3 : 0) + (
                        tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.NORTHEAST) ?
                            2 :
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
                    (tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.NORTHEAST) ? 2 : 0) +
                        (tile.neighbors.get(Direction.WEST) == neighbors.get(Direction.EAST) ? 3 : 0) +
                        (tile.neighbors.get(Direction.SOUTHWEST) == neighbors.get(Direction.SOUTHEAST) ?
                            2 :
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
                    (tile.neighbors.get(Direction.NORTHWEST) == neighbors.get(Direction.SOUTHWEST) ? 2 : 0) +
                        (tile.neighbors.get(Direction.NORTH) == neighbors.get(Direction.SOUTH) ? 3 : 0) + (
                        tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.SOUTHEAST) ?
                            2 :
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
                    (tile.neighbors.get(Direction.NORTHEAST) == neighbors.get(Direction.NORTHWEST) ? 2 : 0) +
                        (tile.neighbors.get(Direction.EAST) == neighbors.get(Direction.WEST) ? 3 : 0) +
                        (tile.neighbors.get(Direction.SOUTHEAST) == neighbors.get(Direction.SOUTHWEST) ?
                            2 :
                            0);
            }
        }
        throw new RuntimeException("Invalid Direction");
    }

    public float similarityTo(Tile other) {
        float similarity = 1;
        for (Direction dir : neighbors.keySet()) {
            if (other.neighbors.get(dir).equals(this.neighbors.get(dir)))
                similarity += dir.isCardinal() ? 3 : 1;
        }
        return similarity / (4 * (3 + 1));
    }

    public abstract void render(SpriteBatch batch, float elapsed, int x, int y);

    public String toString() {
        return "\n" + neighbors.get(Direction.NORTHWEST).toString() +
            neighbors.get(Direction.NORTH).toString() + neighbors.get(Direction.NORTHEAST).toString() + '\n' +
            neighbors.get(Direction.WEST).toString() + "  " + neighbors.get(Direction.EAST).toString() +
            '\n' + neighbors.get(Direction.SOUTHWEST).toString() + neighbors.get(Direction.SOUTH).toString() +
            neighbors.get(Direction.SOUTHEAST).toString();
    }

    public String getLayout(Map<String,Integer> terrainMap) {
        return terrainMap.get(neighbors.get(Direction.NORTHWEST).toString()) + "," +
            terrainMap.get(neighbors.get(Direction.NORTH).toString()) + "," +
            terrainMap.get(neighbors.get(Direction.NORTHEAST).toString()) + "," +
            terrainMap.get(neighbors.get(Direction.WEST).toString()) + "," +
            terrainMap.get(terrain.toString()) + "," +
            terrainMap.get(neighbors.get(Direction.EAST).toString()) + "," +
            terrainMap.get(neighbors.get(Direction.SOUTHWEST).toString()) + "," +
            terrainMap.get(neighbors.get(Direction.SOUTH).toString()) + "," +
            terrainMap.get(neighbors.get(Direction.SOUTHEAST).toString());
    }

    public boolean matchesLayout(String[] layout) {
        return Objects.equals(layout[0], getNeighborString(Direction.NORTHWEST)) &&
            Objects.equals(layout[1], getNeighborString(Direction.NORTH)) &&
            Objects.equals(layout[2], getNeighborString(Direction.NORTHEAST)) &&
            Objects.equals(layout[3], getNeighborString(Direction.WEST)) &&
            Objects.equals(layout[4], terrain.toString()) &&
            Objects.equals(layout[5], getNeighborString(Direction.EAST)) &&
            Objects.equals(layout[6], getNeighborString(Direction.SOUTHWEST)) &&
            Objects.equals(layout[7], getNeighborString(Direction.SOUTH)) &&
            Objects.equals(layout[8], getNeighborString(Direction.SOUTHEAST));
    }

    private String getNeighborString(Direction direction) {
        Terrain neighbor = neighbors.get(direction);
        return neighbor != null ? neighbor.toString() : "null";
    }

    public Terrain getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }
}
