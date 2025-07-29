package heroes.journey.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.modlib.worldgen.Terrain;
import heroes.journey.modlib.worldgen.TileLayout;
import heroes.journey.modlib.worldgen.TileBatch;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for tile generation and processing functions extracted from TileLayout and TileBatch.
 * This allows those classes to remain as simple data containers.
 */
public class TilesetUtils {

    private static final Direction[][] directionGrid = {
        {Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST}, 
        {Direction.WEST, null, Direction.EAST},
        {Direction.SOUTHWEST, Direction.SOUTH, Direction.SOUTHEAST}
    };

    /**
     * Generate tiles from a tile layout.
     */
    public static List<Tile> generateTiles(
        TileLayout layout,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault,
        Terrain... terrains) {
        return generateTiles(layout, tiles, weight, x, y, addToDefault, 0, 0, terrains);
    }

    /**
     * Generate animated tiles from a tile layout.
     */
    public static List<Tile> generateAnimatedTiles(
        TileLayout layout,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        int frameCount,
        int dist,
        boolean addToDefault,
        Terrain... terrains) {
        return generateTiles(layout, tiles, weight, x, y, addToDefault, frameCount, dist, terrains);
    }

    /**
     * Internal method for generating tiles with optional animation parameters.
     */
    private static List<Tile> generateTiles(
        TileLayout layout,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault,
        int frameCount,
        int dist,
        Terrain... terrains) {
        Texture texture = ResourceManager.get().getTexture(layout.getPath());
        TextureData data = texture.getTextureData();

        // Make sure it's prepared
        if (!data.isPrepared()) {
            data.prepare();  // This loads the image data into memory
        }
        Pixmap layoutPixmap = data.consumePixmap();

        if (texture.getWidth() % 3 != 0 || texture.getHeight() % 3 != 0) {
            throw new RuntimeException("Tile Layout image must by divisible by 3 for both height and width");
        }

        int tilesWidth = texture.getWidth() / 3;
        int tilesHeight = texture.getHeight() / 3;

        List<Tile> tileSet = new ArrayList<>(tilesWidth * tilesHeight);

        for (int i = 0; i < tilesWidth; i++) {
            for (int j = 0; j < tilesHeight; j++) {

                // Coordinates into tiles[] array
                int tileX = x + i;
                int tileY = y + j;

                // Guard in case tiles[][] is smaller than layout
                if (tileX >= tiles.length || tileY >= tiles[0].length) {
                    continue;
                }

                int color = layoutPixmap.getPixel((i * 3) + 1, (j * 3) + 1);
                int r = (color & 0xff000000) >>> 24;
                int g = (color & 0x00ff0000) >>> 16;
                int b = (color & 0x0000ff00) >>> 8;
                // Normalize R+G+B to a 0.0 - 1.0 scale
                float rgbSum = (r + g + b) / (3f * 255f);
                int adjustedWeight = Math.max(1, Math.round(weight * rgbSum));

                // Get 3x3 layout block for this tile from the layoutPixmap
                Map<Direction,Terrain> terrainMap = terrainMapFrom(layoutPixmap, i * 3, j * 3, terrains);

                Tile tile;
                if (frameCount != 0)
                    tile = new AnimatedTile(terrains[0], adjustedWeight, addToDefault,
                        getFrames(tiles, tileX, tileY, frameCount, dist), 0.2f);
                else {
                    tile = new BaseTile(terrains[0], adjustedWeight, addToDefault, tiles[tileX][tileY]);
                }
                for (Map.Entry<Direction,Terrain> entry : terrainMap.entrySet()) {
                    tile.add(entry.getKey(), entry.getValue());
                }
                tileSet.add(tile);
            }
        }
        layoutPixmap.dispose(); // Clean up when done
        return tileSet;
    }

    /**
     * Generate terrain map from pixmap data.
     */
    private static Map<Direction,Terrain> terrainMapFrom(
        Pixmap pixmap,
        int offsetX,
        int offsetY,
        Terrain[] terrains) {

        // Step 1: Scan all unique colors in the Pixmap
        Set<Integer> uniqueColors = new HashSet<>();
        for (int y = 0; y < pixmap.getHeight() - 2; y += 3) {
            for (int x = 0; x < pixmap.getWidth() - 2; x += 3) {
                for (int dy = 0; dy < 3; dy++) {
                    for (int dx = 0; dx < 3; dx++) {
                        if (dx == 1 && dy == 1)
                            continue; // skip center
                        int px = x + dx;
                        int py = y + dy;
                        if (px < pixmap.getWidth() && py < pixmap.getHeight()) {
                            uniqueColors.add(pixmap.getPixel(px, py));
                        }
                    }
                }
            }
        }

        // Step 2: Sort colors by brightness (or any comparator you prefer)
        List<Integer> sortedColors = new ArrayList<>(uniqueColors);
        sortedColors.sort(Comparator.comparingDouble(TilesetUtils::colorBrightness));

        // Step 3: Map colors to terrain indexes
        Map<Integer,Terrain> colorToTerrain = new HashMap<>();
        for (int i = 0; i < sortedColors.size(); i++) {
            int color = sortedColors.get(i);
            Terrain terrain = terrains[Math.min(i, terrains.length - 1)];
            colorToTerrain.put(color, terrain);
        }

        // Step 4: Use the map to build terrain map
        Map<Direction,Terrain> terrainMap = new EnumMap<>(Direction.class);
        for (int dy = 0; dy < 3; dy++) {
            for (int dx = 0; dx < 3; dx++) {
                Direction dir = directionGrid[dy][dx];
                if (dir == null)
                    continue;

                int pixelColor = pixmap.getPixel(offsetX + dx, offsetY + dy);
                Terrain terrain = colorToTerrain.getOrDefault(pixelColor, terrains[0]);
                terrainMap.put(dir, terrain);
            }
        }

        return terrainMap;
    }

    /**
     * Calculate brightness of an int-packed RGBA color.
     */
    private static double colorBrightness(int color) {
        int r = (color >> 24) & 0xff;
        int g = (color >> 16) & 0xff;
        int b = (color >> 8) & 0xff;
        return (r + g + b) / (3.0 * 255.0);
    }

    /**
     * Get frames for animated tiles.
     */
    public static TextureRegion[] getFrames(TextureRegion[][] tiles, int x, int y, int frameCount, int dist) {
        return getFrames(tiles, x, y, frameCount, dist, false);
    }

    /**
     * Get frames for animated tiles with optional vertical orientation.
     */
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
     * Generate tiles for a TileBatch.
     */
    public static List<Tile> generateTilesForBatch(TileBatch batch) {
        // Look up layout, textureMap, terrains
        TileLayout layoutObj = heroes.journey.mods.Registries.TileLayoutManager.get(batch.getLayout());
        if (layoutObj == null)
            throw new IllegalStateException("No TileLayout with id '" + batch.getLayout() + "'");
        var textureMapObj = heroes.journey.mods.Registries.TextureManager.get(batch.getTextureMap());
        if (textureMapObj == null)
            throw new IllegalStateException("No TextureMap with id '" + batch.getTextureMap() + "'");
        var tilesArr = ResourceManager.get(textureMapObj);
        if (tilesArr == null)
            throw new IllegalStateException("No TextureMap split with id '" + batch.getTextureMap() + "'");
        var terrainRoles = layoutObj.getTerrainRoles();
        Terrain[] terrainObjs = new Terrain[terrainRoles.size()];
        for (int i = 0; i < terrainRoles.size(); i++) {
            String role = terrainRoles.get(i);
            String terrainId = batch.getTerrains().get(role);
            if (terrainId == null)
                throw new IllegalStateException("Missing terrain for role '" + role + "'");
            Terrain t = heroes.journey.mods.Registries.TerrainManager.get(terrainId);
            if (t == null)
                throw new IllegalStateException("No Terrain with id '" + terrainId + "'");
            terrainObjs[i] = t;
        }
        if (batch.getAnimated()) {
            return generateAnimatedTiles(layoutObj, tilesArr, batch.getWeight(), batch.getStartX(), batch.getStartY(), 
                batch.getFrameCount(), batch.getFrameDist(), batch.getAddToDefault(), terrainObjs);
        } else {
            return generateTiles(layoutObj, tilesArr, batch.getWeight(), batch.getStartX(), batch.getStartY(), 
                batch.getAddToDefault(), terrainObjs);
        }
    }

    /**
     * Get a dot tile from a list of tiles.
     */
    public static Tile getDot(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (isDot(tile)) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Check if a tile is a dot (has no matching neighbors).
     */
    private static boolean isDot(Tile tile) {
        for (Direction dir : Direction.getDirections()) {
            if (tile.getNeighbor(dir) == tile.getTerrain()) {
                return false;
            }
        }
        return true;
    }

} 