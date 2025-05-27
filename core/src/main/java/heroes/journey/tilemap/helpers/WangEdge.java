package heroes.journey.tilemap.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;

import java.util.List;

public class WangEdge {

    public static TileLayout wangEdge = new TileLayout("Textures/wangEdge.png");

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
        return wangEdge.generateTiles(tiles, weight, x, y, false, terrain, adjacentTerrain);
    }
}
