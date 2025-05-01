package heroes.journey.tilemap.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;

public class WangCorner {

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y][x]
     * @param x                 top left corner
     * @param y                 top left corner
     * @return
     */
    public static List<Tile> create(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int transitionWeight,
        int x,
        int y,
        boolean addToDefault) {
        List<Tile> tileSet = new ArrayList<>(14);
        // Center (Not included because it should be one of the adjacent tiles
        // tile.add(new BaseTileRender(tiles[y + 1][x + 1], 0), adjacentTileInner, adjacentTileInner,
        //     adjacentTileInner, adjacentTileInner);
        int cornerWeight = (int)(transitionWeight * (70 / 100d));
        int edgeWeight = (int)(transitionWeight * (10 / 100d));
        int inverseCornerWeight = (int)(transitionWeight * (70 / 100d));
        int diagonalWeight = (int)(transitionWeight * (1 / 100d));
        // Setup Tiles
        // Corners
        Tile northWest = new BaseTile(base, cornerWeight, addToDefault, tiles[x][y]);
        Tile northEast = new BaseTile(base, cornerWeight, addToDefault, tiles[x + 2][y]);
        Tile southWest = new BaseTile(base, cornerWeight, addToDefault, tiles[x][y + 2]);
        Tile southEast = new BaseTile(base, cornerWeight, addToDefault, tiles[x + 2][y + 2]);
        // Edges
        Tile north = new BaseTile(base, edgeWeight, addToDefault, tiles[x + 1][y]);
        Tile east = new BaseTile(base, edgeWeight, addToDefault, tiles[x + 2][y + 1]);
        Tile south = new BaseTile(base, edgeWeight, addToDefault, tiles[x + 1][y + 2]);
        Tile west = new BaseTile(base, edgeWeight, addToDefault, tiles[x][y + 1]);
        // Inverse Corners
        Tile northWestInverse = new BaseTile(base, inverseCornerWeight, addToDefault, tiles[x + 3][y]);
        Tile northEastInverse = new BaseTile(base, inverseCornerWeight, addToDefault, tiles[x + 4][y]);
        Tile southWestInverse = new BaseTile(base, inverseCornerWeight, addToDefault, tiles[x + 3][y + 1]);
        Tile southEastInverse = new BaseTile(base, inverseCornerWeight, addToDefault, tiles[x + 4][y + 1]);
        // Diagonal Corners
        // Diagonal with the cliff on the bottom left and top right
        Tile diagonalSouthWest = new BaseTile(base, diagonalWeight, addToDefault, tiles[x + 3][y + 2]);
        // Diagonal with the cliff on the top left and bottom right
        Tile diagonalNorthWest = new BaseTile(base, diagonalWeight, addToDefault, tiles[x + 4][y + 2]);
        // Center
        Tile center = new BaseTile(base, transitionWeight, addToDefault, tiles[x + 1][y + 1]);

        tileSet.add(northWest);
        tileSet.add(northEast);
        tileSet.add(southWest);
        tileSet.add(southEast);
        tileSet.add(north);
        tileSet.add(east);
        tileSet.add(south);
        tileSet.add(west);
        tileSet.add(northWestInverse);
        tileSet.add(northEastInverse);
        tileSet.add(southWestInverse);
        tileSet.add(southEastInverse);
        tileSet.add(diagonalSouthWest);
        tileSet.add(diagonalNorthWest);
        tileSet.add(center);

        // Add connections
        // Corners
        northWest.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, adjacentTileOuter)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, adjacentTileOuter);
        northEast.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, adjacentTileOuter)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, adjacentTileOuter)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, base);
        southWest.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, adjacentTileOuter)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, adjacentTileOuter);
        southEast.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, adjacentTileOuter)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, adjacentTileOuter)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, base);
        // Edges
        north.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, adjacentTileOuter)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, base);
        south.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, adjacentTileOuter)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, base);
        east.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, adjacentTileOuter)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        west.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, adjacentTileOuter);
        // Inverse Corners
        northWestInverse.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, base);
        northEastInverse.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        southWestInverse.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, base);
        southEastInverse.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        // Diagonal Corners
        diagonalSouthWest.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, base);
        diagonalNorthWest.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, base);
        // Center
        center.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);

        return tileSet;
    }

    public static void cliffTransition(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int transitionWeight,
        int x,
        int y,
        boolean addToDefault) {
        // Setup Tiles
        // Sides
        Tile sideNorthWest = new BaseTile(base, transitionWeight, addToDefault, tiles[x][y]);
        Tile sideNorthEast = new BaseTile(base, transitionWeight, addToDefault, tiles[x + 1][y]);
        Tile sideSouthWest = new BaseTile(base, transitionWeight, addToDefault, tiles[x][y + 1]);
        Tile sideSouthEast = new BaseTile(base, transitionWeight, addToDefault, tiles[x + 1][y + 1]);
        // Top
        Tile topNorthWest = new BaseTile(base, transitionWeight, addToDefault, tiles[x][y + 2]);
        Tile topNorthEast = new BaseTile(base, transitionWeight, addToDefault, tiles[x + 1][y + 2]);
        Tile topSouthWest = new BaseTile(base, transitionWeight, addToDefault, tiles[x][y + 3]);
        Tile topSouthEast = new BaseTile(base, transitionWeight, addToDefault, tiles[x + 1][y + 3]);

        // Add Connections
        // Sides
        sideNorthWest.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, adjacentTileOuter);
        sideNorthEast.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, adjacentTileOuter)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, base)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        sideSouthWest.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, adjacentTileOuter);
        sideSouthEast.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, base)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, adjacentTileOuter)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        // Top
        topNorthWest.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, adjacentTileOuter)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        topNorthEast.add(Direction.NORTHWEST, adjacentTileOuter)
            .add(Direction.NORTH, adjacentTileOuter)
            .add(Direction.NORTHEAST, adjacentTileOuter)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, base);
        topSouthWest.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, base)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, adjacentTileOuter)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, adjacentTileInner);
        topSouthEast.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileOuter)
            .add(Direction.SOUTH, adjacentTileOuter)
            .add(Direction.SOUTHWEST, adjacentTileOuter)
            .add(Direction.WEST, base);
    }

    public static void cliffTransition(
        Terrain cliff1,
        Terrain cliff2,
        Terrain adjacentTileOuter1,
        Terrain adjacentTileOuter2,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int transitionWeight,
        int x,
        int y,
        boolean addToDefault) {
        // Setup Tiles
        // Sides
        Tile sideNorthWest = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x][y]);
        Tile sideNorthEast = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x + 1][y]);
        Tile sideSouthWest = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x][y + 1]);
        Tile sideSouthEast = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x + 1][y + 1]);
        // Top
        Tile topNorthWest = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x + 2][y]);
        Tile topNorthEast = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x + 3][y]);
        Tile topSouthWest = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x + 2][y + 1]);
        Tile topSouthEast = new BaseTile(cliff1, transitionWeight, addToDefault, tiles[x + 3][y + 1]);

        // Add Connections
        // Side
        sideNorthWest.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, cliff1)
            .add(Direction.SOUTHEAST, adjacentTileOuter1)
            .add(Direction.SOUTH, adjacentTileOuter1)
            .add(Direction.SOUTHWEST, adjacentTileOuter2)
            .add(Direction.WEST, cliff2);
        sideNorthEast.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, adjacentTileInner)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, cliff2)
            .add(Direction.SOUTHEAST, adjacentTileOuter2)
            .add(Direction.SOUTH, adjacentTileOuter1)
            .add(Direction.SOUTHWEST, adjacentTileOuter1)
            .add(Direction.WEST, cliff1);
        sideSouthWest.add(Direction.NORTHWEST, adjacentTileOuter2)
            .add(Direction.NORTH, adjacentTileOuter1)
            .add(Direction.NORTHEAST, adjacentTileOuter1)
            .add(Direction.EAST, cliff1)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, cliff2);
        sideSouthEast.add(Direction.NORTHWEST, adjacentTileOuter1)
            .add(Direction.NORTH, adjacentTileOuter1)
            .add(Direction.NORTHEAST, adjacentTileOuter2)
            .add(Direction.EAST, cliff2)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, adjacentTileInner)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, cliff1);
        // Top
        topNorthWest.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, cliff2)
            .add(Direction.NORTHEAST, adjacentTileOuter2)
            .add(Direction.EAST, adjacentTileOuter1)
            .add(Direction.SOUTHEAST, adjacentTileOuter1)
            .add(Direction.SOUTH, cliff1)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        topNorthEast.add(Direction.NORTHWEST, adjacentTileOuter2)
            .add(Direction.NORTH, cliff2)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, cliff1)
            .add(Direction.SOUTHWEST, adjacentTileOuter1)
            .add(Direction.WEST, adjacentTileOuter1);
        topSouthWest.add(Direction.NORTHWEST, adjacentTileInner)
            .add(Direction.NORTH, cliff1)
            .add(Direction.NORTHEAST, adjacentTileOuter1)
            .add(Direction.EAST, adjacentTileOuter1)
            .add(Direction.SOUTHEAST, adjacentTileOuter2)
            .add(Direction.SOUTH, cliff2)
            .add(Direction.SOUTHWEST, adjacentTileInner)
            .add(Direction.WEST, adjacentTileInner);
        topSouthEast.add(Direction.NORTHWEST, adjacentTileOuter1)
            .add(Direction.NORTH, cliff1)
            .add(Direction.NORTHEAST, adjacentTileInner)
            .add(Direction.EAST, adjacentTileInner)
            .add(Direction.SOUTHEAST, adjacentTileInner)
            .add(Direction.SOUTH, cliff2)
            .add(Direction.SOUTHWEST, adjacentTileOuter2)
            .add(Direction.WEST, adjacentTileOuter1);
    }
}
