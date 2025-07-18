package heroes.journey.tilemap;

import static heroes.journey.mods.Registries.TerrainManager;
import static heroes.journey.mods.Registries.TileBatchManager;
import static heroes.journey.mods.Registries.TileLayoutManager;

import java.util.List;
import java.util.Map;

import heroes.journey.modlib.registries.Registrable;
import heroes.journey.modlib.worldgen.ITileBatch;
import heroes.journey.mods.Registries;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;

public class TileBatch extends Registrable implements ITileBatch {
    private final String layoutId;
    private final String textureMapId;
    private final Map<String,String> terrainMapping;
    private final int weight;
    private final int startX;
    private final int startY;
    private final boolean addToDefault;
    private final boolean animated;
    private final int frameCount;
    private final int frameDist;
    private List<Tile> tiles;
    private boolean tilesGenerated = false;

    public TileBatch(
        String id,
        String layoutId,
        String textureMapId,
        Map<String,String> terrainMapping,
        int weight,
        int startX,
        int startY,
        boolean addToDefault,
        boolean animated,
        int frameCount,
        int frameDist) {
        super(id);
        this.layoutId = layoutId;
        this.textureMapId = textureMapId;
        this.terrainMapping = terrainMapping;
        this.weight = weight;
        this.startX = startX;
        this.startY = startY;
        this.addToDefault = addToDefault;
        this.animated = animated;
        this.frameCount = frameCount;
        this.frameDist = frameDist;
        this.tiles = null;
    }

    @Override
    public TileBatch register() {
        Registries.TileBatchManager.register(this);
        return this;
    }

    @Override
    public String getLayout() {
        return layoutId;
    }

    @Override
    public String getTextureMap() {
        return textureMapId;
    }

    @Override
    public Map<String,String> getTerrains() {
        return terrainMapping;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getStartX() {
        return startX;
    }

    @Override
    public int getStartY() {
        return startY;
    }

    @Override
    public boolean getAddToDefault() {
        return addToDefault;
    }

    @Override
    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public int getFrameDist() {
        return frameDist;
    }

    public void generateTilesIfNeeded() {
        if (tilesGenerated)
            return;
        // Look up layout, textureMap, terrains
        TileLayout layoutObj = TileLayoutManager.get(layoutId);
        if (layoutObj == null)
            throw new IllegalStateException("No TileLayout with id '" + layoutId + "'");
        var textureMapObj = Registries.TextureManager.get(textureMapId);
        if (textureMapObj == null)
            throw new IllegalStateException("No TextureMap with id '" + textureMapId + "'");
        var tilesArr = ResourceManager.get(textureMapObj);
        if (tilesArr == null)
            throw new IllegalStateException("No TextureMap split with id '" + textureMapId + "'");
        var terrainRoles = layoutObj.getTerrainRoles();
        Terrain[] terrainObjs = new Terrain[terrainRoles.size()];
        for (int i = 0; i < terrainRoles.size(); i++) {
            String role = terrainRoles.get(i);
            String terrainId = terrainMapping.get(role);
            if (terrainId == null)
                throw new IllegalStateException("Missing terrain for role '" + role + "'");
            Terrain t = TerrainManager.get(terrainId);
            if (t == null)
                throw new IllegalStateException("No Terrain with id '" + terrainId + "'");
            terrainObjs[i] = t;
        }
        if (animated) {
            this.tiles = layoutObj.generateAnimatedTiles(tilesArr, weight, startX, startY, frameCount,
                frameDist, addToDefault, terrainObjs);
        } else {
            this.tiles = layoutObj.generateTiles(tilesArr, weight, startX, startY, addToDefault, terrainObjs);
        }
        this.tilesGenerated = true;
    }

    /**
     * @return Tile from a list whose neighbors dont match it.
     */
    public Tile getDot() {
        for (Tile tile : tiles) {
            if (isDot(tile)) {
                return tile;
            }
        }
        return null;
    }

    private static boolean isDot(Tile tile) {
        for (Direction dir : Direction.getDirections()) {
            if (tile.getNeighbor(dir) == tile.getTerrain()) {
                return false;
            }
        }
        return true;
    }

    public String getTextureMapId() {
        return textureMapId;
    }

    public String getTileLayoutId() {
        return layoutId;
    }

    public Map<String,String> getTerrainMapping() {
        return terrainMapping;
    }

    public boolean isAddToDefault() {
        return addToDefault;
    }

    public boolean isAnimated() {
        return animated;
    }

    public List<Tile> getTiles() {
        generateTilesIfNeeded();
        return tiles;
    }

    public static void finalizeAllBatches() {
        for (TileBatch batch : TileBatchManager.values()) {
            batch.generateTilesIfNeeded();
        }
    }

    // After ResourceManager has loaded and split textures, call TilesDSL.finalizeAllBaseTiles() to generate all base tiles.
}
