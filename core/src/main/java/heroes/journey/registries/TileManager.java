package heroes.journey.registries;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;

public class TileManager extends ArrayList<Tile> {

    public static final List<Tile> baseTiles = new ArrayList<>();
    private static TileManager tileManager;

    public static TileManager get() {
        if (tileManager == null)
            tileManager = new TileManager();
        return tileManager;
    }

    public Tile getBaseTile(Terrain terrain) {
        for (Tile t : baseTiles) {
            if (t.getTerrain() == terrain) {
                return t;
            }
        }
        return null;
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
