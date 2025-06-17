package heroes.journey.tilemap;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;

public class TileHelpers {

    public static TileLayout wangCorner = new TileLayout("Textures/wangCorner.png");
    public static TileLayout cliffTransitionTapper = new TileLayout("Textures/cliffTransitionTapper.png");
    public static TileLayout cliffTransition = new TileLayout("Textures/cliffTransition.png");
    public static TileLayout wangEdge = new TileLayout("Textures/wangEdge.png");

    public static void baseTile(Tile tile, Terrain terrain, boolean addToBaseTiles) {
        tile.add(Direction.NORTHWEST, terrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, terrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, terrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, terrain)
            .add(Direction.WEST, terrain);
        if (addToBaseTiles)
            TileManager.baseTiles.add(tile);
    }

    public static void baseTile(Tile tile, Terrain terrain) {
        baseTile(tile, terrain, true);
    }

    /**
     * @param terrain
     * @param adjacentTerrain
     * @param tiles           [y][x]
     * @param x               top left corner
     * @param y               top left corner
     */
    public static List<Tile> createEdge(
        Terrain terrain,
        Terrain adjacentTerrain,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y) {
        return wangEdge.generateTiles(tiles, weight, x, y, false, terrain, adjacentTerrain);
    }

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y][x]
     * @param x                 top left corner
     * @param y                 top left cornesr
     * @return
     */
    public static List<Tile> createWangCorner(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        return wangCorner.generateTiles(tiles, weight, x, y, addToDefault, base, adjacentTileOuter,
            adjacentTileInner);
    }

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y, x]
     * @param x                 top left corner
     * @param y                 top left corner
     */
    public static void createWangCornerAnimated(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        wangCorner.generateAnimatedTiles(tiles, weight, x, y, 4, 5, addToDefault, base, adjacentTileOuter,
            adjacentTileInner);
    }

    public static void cliffTransitionTapper(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        cliffTransitionTapper.generateTiles(tiles, weight * 1000, x, y, addToDefault, base, adjacentTileOuter,
            adjacentTileInner);
    }

    public static void cliffTransition(
        Terrain cliff1,
        Terrain cliff2,
        Terrain adjacentTileOuter1,
        Terrain adjacentTileOuter2,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        cliffTransition.generateTiles(tiles, weight * 1000, x, y, addToDefault, cliff1, cliff2,
            adjacentTileOuter1, adjacentTileOuter2, adjacentTileInner);
    }

    public static void cliffTransitionAnimated(
        Terrain cliff1,
        Terrain cliff2,
        Terrain adjacentTileOuter1,
        Terrain adjacentTileOuter2,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        cliffTransition.generateAnimatedTiles(tiles, weight * 1000, x, y, 4, 4, addToDefault, cliff1, cliff2,
            adjacentTileOuter1, adjacentTileOuter2, adjacentTileInner);
    }
}
