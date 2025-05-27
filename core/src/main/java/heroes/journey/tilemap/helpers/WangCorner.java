package heroes.journey.tilemap.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;

import java.util.List;

public class WangCorner {

    public static TileLayout wangCorner = new TileLayout("Textures/wangcorner.png");
    public static TileLayout cliffTransitionTapper = new TileLayout("Textures/cliffTransitionTapper.png");
    public static TileLayout cliffTransition = new TileLayout("Textures/cliffTransition.png");

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y][x]
     * @param x                 top left corner
     * @param y                 top left cornesr
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
        return wangCorner.generateTiles(tiles, transitionWeight, x, y, addToDefault, base, adjacentTileOuter, adjacentTileInner);
    }

    public static void cliffTransitionTapper(
        Terrain base,
        Terrain adjacentTileOuter,
        Terrain adjacentTileInner,
        TextureRegion[][] tiles,
        int transitionWeight,
        int x,
        int y,
        boolean addToDefault) {
        cliffTransitionTapper.generateTiles(tiles, transitionWeight * 1000, x, y, addToDefault, base, adjacentTileOuter, adjacentTileInner);
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
        cliffTransition.generateTiles(tiles, transitionWeight * 1000, x, y, addToDefault, cliff1, cliff2, adjacentTileOuter1, adjacentTileOuter2, adjacentTileInner);
    }
}
