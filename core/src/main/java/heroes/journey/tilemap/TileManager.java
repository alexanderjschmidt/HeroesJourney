package heroes.journey.tilemap;

import static heroes.journey.mods.Registries.TerrainManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heroes.journey.mods.Registries;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;

public class TileManager extends ArrayList<Tile> {

    public static final Map<String,Tile> BASE_TILES = new HashMap<>();
    private static final List<BaseTileDef> baseTileDefs = new ArrayList<>();
    private static TileManager tileManager;

    public static TileManager get() {
        if (tileManager == null)
            tileManager = new TileManager();
        return tileManager;
    }

    public Tile getBaseTile(Terrain terrain) {
        for (Tile t : BASE_TILES.values()) {
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

    public static void addBaseTileDef(BaseTileDef def) {
        baseTileDefs.add(def);
    }

    public static void finalizeAllBaseTiles() {
        for (BaseTileDef def : baseTileDefs) {
            Terrain terrainObj = Registries.TerrainManager.get(def.getTerrain());
            if (terrainObj == null)
                throw new IllegalArgumentException("No Terrain with id '" + def.getTerrain() + "'");
            var textureMapObj = Registries.TextureManager.get(def.getTextureMap());
            if (textureMapObj == null)
                throw new IllegalArgumentException("No TextureMap with id '" + def.getTextureMap() + "'");
            var tiles = ResourceManager.get(textureMapObj);
            if (tiles == null)
                throw new IllegalArgumentException(
                    "No TextureMap split with id '" + def.getTextureMap() + "'");
            Tile tile;
            if (def.getFrameDist() > 0 && def.getFrameCount() > 0) {
                tile = new AnimatedTile(terrainObj, def.getWeight(), def.getAddToBaseTiles(),
                    TileLayout.getFrames(tiles, def.getX(), def.getY(), def.getFrameCount(),
                        def.getFrameDist()), def.getFrameRate());
            } else {
                tile = new BaseTile(terrainObj, def.getWeight(), def.getAddToBaseTiles(),
                    tiles[def.getX()][def.getY()]);
            }
            baseTile(tile, def.getTerrain());
            if (def.getAddToBaseTiles())
                BASE_TILES.put(def.getId(), tile);
        }
    }

    private static void baseTile(Tile tile, String terrainStr) {
        Terrain terrain = TerrainManager.get(terrainStr);
        tile.add(Direction.NORTHWEST, terrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, terrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, terrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, terrain)
            .add(Direction.WEST, terrain);
    }
}
