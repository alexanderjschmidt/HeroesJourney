package heroes.journey.tilemap.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.tilemap.wavefunction.AnimatedTile;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;

public class WangCornerAnimated {

    public static TextureRegion[] getFrames(TextureRegion[][] tiles, int x, int y, int frameCount, int dist) {
        return getFrames(tiles, x, y, frameCount, dist, false);
    }

    public static TextureRegion[] getFrames(
        TextureRegion[][] tiles,
        int x,
        int y,
        int frameCount,
        int dist,
        boolean vertical) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            int dx = vertical ? 0 : i * dist;
            int dy = !vertical ? 0 : i * dist;
            frames[i] = tiles[x + dx][y + dy];
        }
        return frames;
    }

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y, x]
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
        // tile.add(new AnimatedTileRender(getFrames(tiles,y + 1, x + 1], 0), adjacentTileInner, adjacentTileInner,
        //     adjacentTileInner, adjacentTileInner);
        int cornerWeight = (int)(transitionWeight * (8 / 10d));
        int edgeWeight = (int)(transitionWeight * (5 / 10d));
        int inverseCornerWeight = (int)(transitionWeight * (8 / 10d));
        int diagonalWeight = (int)(transitionWeight * (2 / 10d));
        // Setup Tiles
        // Corners
        Tile northWest = new AnimatedTile(base, cornerWeight, getFrames(tiles, x, y, 4, 5), .2f);
        Tile northEast = new AnimatedTile(base, cornerWeight, getFrames(tiles, x + 2, y, 4, 5), .2f);
        Tile southWest = new AnimatedTile(base, cornerWeight, getFrames(tiles, x, y + 2, 4, 5), .2f);
        Tile southEast = new AnimatedTile(base, cornerWeight, getFrames(tiles, x + 2, y + 2, 4, 5), .2f);
        // Edges
        Tile north = new AnimatedTile(base, edgeWeight, getFrames(tiles, x + 1, y, 4, 5), .2f);
        Tile east = new AnimatedTile(base, edgeWeight, getFrames(tiles, x + 2, y + 1, 4, 5), .2f);
        Tile south = new AnimatedTile(base, edgeWeight, getFrames(tiles, x + 1, y + 2, 4, 5), .2f);
        Tile west = new AnimatedTile(base, edgeWeight, getFrames(tiles, x, y + 1, 4, 5), .2f);
        // Inverse Corners
        Tile northWestInverse = new AnimatedTile(base, inverseCornerWeight, getFrames(tiles, x + 3, y, 4, 5),
            .2f);
        Tile northEastInverse = new AnimatedTile(base, inverseCornerWeight, getFrames(tiles, x + 4, y, 4, 5),
            .2f);
        Tile southWestInverse = new AnimatedTile(base, inverseCornerWeight,
            getFrames(tiles, x + 3, y + 1, 4, 5), .2f);
        Tile southEastInverse = new AnimatedTile(base, inverseCornerWeight,
            getFrames(tiles, x + 4, y + 1, 4, 5), .2f);
        // Diagonal Corners
        // Diagonal with the cliff on the bottom left and top right
        Tile diagonalSouthWest = new AnimatedTile(base, diagonalWeight, getFrames(tiles, x + 3, y + 2, 4, 5),
            .2f);
        // Diagonal with the cliff on the top left and bottom right
        Tile diagonalNorthWest = new AnimatedTile(base, diagonalWeight, getFrames(tiles, x + 4, y + 2, 4, 5),
            .2f);

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
        int y) {
        // Setup Tiles
        // Sides
        Tile sideNorthWest = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x, y, 4, 4), .2f);
        Tile sideNorthEast = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x + 1, y, 4, 4),
            .2f);
        Tile sideSouthWest = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x, y + 1, 4, 4),
            .2f);
        Tile sideSouthEast = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x + 1, y + 1, 4, 4),
            .2f);
        // Top
        Tile topNorthWest = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x + 2, y, 4, 4), .2f);
        Tile topNorthEast = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x + 3, y, 4, 4), .2f);
        Tile topSouthWest = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x + 2, y + 1, 4, 4),
            .2f);
        Tile topSouthEast = new AnimatedTile(cliff1, transitionWeight, getFrames(tiles, x + 3, y + 1, 4, 4),
            .2f);

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
