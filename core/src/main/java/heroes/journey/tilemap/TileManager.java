package heroes.journey.tilemap;

import heroes.journey.tilemap.wavefunctiontiles.Tile;

import java.util.ArrayList;

public class TileManager extends ArrayList<Tile> {

    private static TileManager tileManager;

    public static TileManager get() {
        if (tileManager == null)
            tileManager = new TileManager();
        return tileManager;
    }

    // TODO improve performance with map?
    public Tile getTileMatch(String[] layout) {
        for (Tile t : this) {
            if (t.matchesLayout(layout))
                return t;
        }
        return null;
    }
}
