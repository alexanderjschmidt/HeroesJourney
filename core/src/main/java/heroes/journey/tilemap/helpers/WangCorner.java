package heroes.journey.tilemap.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.tilemap.wavefunction.BaseTile;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;

public class WangCorner {

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y][x]
     * @param x                 top left corner
     * @param y                 top left corner
     */
    public static void create(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int transitionWeight,
        int x,
        int y) {
        // Center (Not included because it should be one of the adjacent tiles
        // tile.add(new BaseTileRender(tiles[y + 1][x + 1], 0), adjacentTileInner, adjacentTileInner,
        //     adjacentTileInner, adjacentTileInner);
        int cornerWeight = (int)(transitionWeight * (8 / 10d));
        int edgeWeight = (int)(transitionWeight * (5 / 10d));
        int inverseCornerWeight = (int)(transitionWeight * (8 / 10d));
        int diagonalWeight = (int)(transitionWeight * (2 / 10d));
        // Setup Tiles
        // Corners
        Tile northWest = new BaseTile(base, cornerWeight, tiles[x][y]);
        Tile northEast = new BaseTile(base, cornerWeight, tiles[x + 2][y]);
        Tile southWest = new BaseTile(base, cornerWeight, tiles[x][y + 2]);
        Tile southEast = new BaseTile(base, cornerWeight, tiles[x + 2][y + 2]);
        // Edges
        Tile north = new BaseTile(base, edgeWeight, tiles[x + 1][y]);
        Tile east = new BaseTile(base, edgeWeight, tiles[x + 2][y + 1]);
        Tile south = new BaseTile(base, edgeWeight, tiles[x + 1][y + 2]);
        Tile west = new BaseTile(base, edgeWeight, tiles[x][y + 1]);
        // Inverse Corners
        Tile northWestInverse = new BaseTile(base, inverseCornerWeight, tiles[x + 3][y]);
        Tile northEastInverse = new BaseTile(base, inverseCornerWeight, tiles[x + 4][y]);
        Tile southWestInverse = new BaseTile(base, inverseCornerWeight, tiles[x + 3][y + 1]);
        Tile southEastInverse = new BaseTile(base, inverseCornerWeight, tiles[x + 4][y + 1]);
        // Diagonal Corners
        // Diagonal with the cliff on the bottom left and top right
        Tile diagonalSouthWest = new BaseTile(base, diagonalWeight, tiles[x + 3][y + 2]);
        // Diagonal with the cliff on the top left and bottom right
        Tile diagonalNorthWest = new BaseTile(base, diagonalWeight, tiles[x + 4][y + 2]);

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
            .add(Direction.SOUTHWEST, base)
            .add(Direction.WEST, adjacentTileInner);
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
    }
}
