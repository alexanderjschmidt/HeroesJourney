package heroes.journey.tilemap;

import java.util.ArrayList;

import heroes.journey.tilemap.wavefunctiontiles.Tile;

public class TileManager extends ArrayList<Tile> {

    private static TileManager tileManager;

    public static TileManager get() {
        if (tileManager == null)
            tileManager = new TileManager();
        return tileManager;
    }
}
