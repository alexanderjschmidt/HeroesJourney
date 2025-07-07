package heroes.journey.tilemap;

import static heroes.journey.registries.Registries.TileLayoutManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.registries.Registrable;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;

public class TileLayout extends Registrable {
    private final String id;
    private final String path;
    private final List<String> terrainRoles;

    /**
     * The coloring of the layout should be 0,0,0 (black) for the first terrain passed in
     * (which should also be the base/center tile), and then increasing in
     * rgb (added together) values until 255,255,255 (white) the last terrain
     * The center tile of a 3x3 area defines its weight multiplier.
     *
     * @param path
     */
    public TileLayout(String id, String path, List<String> terrainRoles) {
        super(id, id);
        this.id = id;
        this.path = path;
        this.terrainRoles = terrainRoles;
    }

    public TileLayout register() {
        TileLayoutManager.register(this);
        return this;
    }

    public static TileLayout get(String id) {
        return TileLayoutManager.get(id);
    }

    public String getPath() {
        return path;
    }

    public List<String> getTerrainRoles() {
        return terrainRoles;
    }

    private List<Tile> generateTiles(
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault,
        int frameCount,
        int dist,
        Terrain... terrains) {
        Texture texture = ResourceManager.get().getTexture(path);
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
                //System.out.println(path + " " + i + " " + j + " " + adjustedWeight);

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
     * @param tiles
     * @param weight
     * @param x
     * @param y
     * @param addToDefault adds to the default list of tiles to pull from for Wave function collapse
     * @param terrains
     * @return a list of generated tiles
     */
    public List<Tile> generateTiles(
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault,
        Terrain... terrains) {
        return generateTiles(tiles, weight, x, y, addToDefault, 0, 0, terrains);
    }

    /**
     * @param tiles
     * @param weight
     * @param x
     * @param y
     * @param addToDefault adds to the default list of tiles to pull from for Wave function collapse
     * @param terrains
     * @return a list of generated tiles
     */
    public List<Tile> generateAnimatedTiles(
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        int frameCount,
        int dist,
        boolean addToDefault,
        Terrain... terrains) {
        return generateTiles(tiles, weight, x, y, addToDefault, frameCount, dist, terrains);
    }

    private static final Direction[][] directionGrid = {
        {Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST}, {Direction.WEST, null, Direction.EAST},
        {Direction.SOUTHWEST, Direction.SOUTH, Direction.SOUTHEAST}};

    private Map<Direction,Terrain> terrainMapFrom(
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
        sortedColors.sort(Comparator.comparingDouble(this::colorBrightness));

        // Step 3: MapGenPlan colors to terrain indexes
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

    // Utility function to calculate brightness of an int-packed RGBA color
    private double colorBrightness(int color) {
        int r = (color >> 24) & 0xff;
        int g = (color >> 16) & 0xff;
        int b = (color >> 8) & 0xff;
        return (r + g + b) / (3.0 * 255.0);
    }

    public static TextureRegion[] getFrames(TextureRegion[][] tiles, int x, int y, int frameCount, int dist) {
        return getFrames(tiles, x, y, frameCount, dist, false);
    }

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

}
