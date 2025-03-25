package heroes.journey.tilemap.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.tilemap.wavefunction.BaseTile;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;

public class WangEdge {

    /**
     * @param terrain
     * @param adjacentTerrain
     * @param tiles           [y][x]
     * @param x               top left corner
     * @param y               top left corner
     */
    public static List<Tile> create(
        Terrain terrain,
        Terrain adjacentTerrain,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y) {
        List<Tile> edgeTiles = new ArrayList<>(16);

        int midWeight = weight / 2;
        int lowWeight = 1;
        // Setup Tiles
        // Corners
        Tile northWest = new BaseTile(terrain, midWeight, false, tiles[x + 1][y]);
        Tile northEast = new BaseTile(terrain, midWeight, false, tiles[x + 3][y]);
        Tile southWest = new BaseTile(terrain, midWeight, false, tiles[x + 1][y + 2]);
        Tile southEast = new BaseTile(terrain, midWeight, false, tiles[x + 3][y + 2]);
        // 3 Connections (Edges of Circle)
        Tile north = new BaseTile(terrain, midWeight, false, tiles[x + 2][y]);
        Tile east = new BaseTile(terrain, midWeight, false, tiles[x + 3][y + 1]);
        Tile south = new BaseTile(terrain, midWeight, false, tiles[x + 2][y + 2]);
        Tile west = new BaseTile(terrain, midWeight, false, tiles[x + 1][y + 1]);
        // 1 Connection (End)
        Tile northEnd = new BaseTile(terrain, lowWeight, false, tiles[x][y]);
        Tile eastEnd = new BaseTile(terrain, lowWeight, false, tiles[x + 3][y + 3]);
        Tile southEnd = new BaseTile(terrain, lowWeight, false, tiles[x][y + 2]);
        Tile westEnd = new BaseTile(terrain, lowWeight, false, tiles[x + 1][y + 3]);
        // 2 Connections (Straights)
        Tile northSouth = new BaseTile(terrain, weight, false, tiles[x][y + 1]);
        Tile eastWest = new BaseTile(terrain, weight, false, tiles[x + 2][y + 3]);
        // 4 Connections (Center of Circle)
        Tile fourWay = new BaseTile(terrain, midWeight, false, tiles[x + 2][y + 1]);
        // 0 Connections (Dot)
        Tile dot = new BaseTile(terrain, lowWeight, false, tiles[x][y + 3]);

        edgeTiles.add(dot);
        edgeTiles.add(northWest);
        edgeTiles.add(northEast);
        edgeTiles.add(southEast);
        edgeTiles.add(southWest);
        edgeTiles.add(north);
        edgeTiles.add(east);
        edgeTiles.add(south);
        edgeTiles.add(west);
        edgeTiles.add(northEnd);
        edgeTiles.add(eastEnd);
        edgeTiles.add(southEnd);
        edgeTiles.add(westEnd);
        edgeTiles.add(northSouth);
        edgeTiles.add(eastWest);
        edgeTiles.add(fourWay);

        // Add connections
        // Corners
        northWest.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        northEast.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        southWest.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        southEast.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        // 3 Connections (Edges of Circle)
        north.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        east.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        south.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        west.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        // 1 Connection (End)
        northEnd.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        eastEnd.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        southEnd.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        westEnd.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        // 2 Connections (Straights)
        northSouth.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);
        eastWest.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        // 4 Connections (Center of Circle)
        fourWay.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, terrain);
        // 0 Connections (Dot)
        dot.add(Direction.NORTHWEST, adjacentTerrain)
            .add(Direction.NORTH, adjacentTerrain)
            .add(Direction.NORTHEAST, adjacentTerrain)
            .add(Direction.EAST, adjacentTerrain)
            .add(Direction.SOUTHEAST, adjacentTerrain)
            .add(Direction.SOUTH, adjacentTerrain)
            .add(Direction.SOUTHWEST, adjacentTerrain)
            .add(Direction.WEST, adjacentTerrain);

        return edgeTiles;
    }
}
