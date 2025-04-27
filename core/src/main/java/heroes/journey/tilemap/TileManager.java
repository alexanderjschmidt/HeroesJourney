package heroes.journey.tilemap;

import heroes.journey.tilemap.wavefunction.Tile;

import java.util.ArrayList;

public class TileManager extends ArrayList<Tile> {

    private static TileManager tileManager;

    public static TileManager get() {
        if (tileManager == null)
            tileManager = new TileManager();
        return tileManager;
    }
}
