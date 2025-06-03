package heroes.journey.tilemap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TileLayout {

    private final String path;

    /**
     * The coloring of the layout should be 0,0,0 (black) for the first terrain passed in
     * (which should also be the base/center tile), and then increasing in
     * rgb (added together) values until 255,255,255 (white) the last terrain
     *
     * @param path
     */
    public TileLayout(String path) {
        this.path = path;
    }

    private List<Tile> generateTiles(
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault, int frameCount, int dist, Terrain... terrains) {
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

                int color = layoutPixmap.getPixel((i * 3) + 1, (j * 3) + 1);
                int r = (color & 0xff000000) >>> 24;
                int g = (color & 0x00ff0000) >>> 16;
                int b = (color & 0x0000ff00) >>> 8;
                // Normalize R+G+B to a 0.0 - 1.0 scale
                float rgbSum = (r + g + b) / (3f * 255f);
                int adjustedWeight = Math.max(1, Math.round(weight * rgbSum));
                System.out.println(path + " " + i + " " + j + " " + adjustedWeight);

                // Guard in case tiles[][] is smaller than layout
                if (tileX >= tiles.length || tileY >= tiles[0].length) continue;

                // Get 3x3 layout block for this tile from the layoutPixmap
                Map<Direction, Terrain> terrainMap = terrainMapFrom(layoutPixmap, i * 3, j * 3, terrains);

                Tile tile;
                if (frameCount != 0)
                    tile = new AnimatedTile(terrains[0], adjustedWeight, addToDefault, getFrames(tiles, tileX, tileY, frameCount, dist), 0.2f);
                else {
                    tile = new BaseTile(terrains[0], adjustedWeight, addToDefault, tiles[tileX][tileY]);
                }
                for (Map.Entry<Direction, Terrain> entry : terrainMap.entrySet()) {
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
        boolean addToDefault, Terrain... terrains) {
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
        int frameCount, int dist,
        boolean addToDefault, Terrain... terrains) {
        return generateTiles(tiles, weight, x, y, addToDefault, frameCount, dist, terrains);
    }

    private static final Direction[][] directionGrid = {
        {Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST},
        {Direction.WEST, null, Direction.EAST},
        {Direction.SOUTHWEST, Direction.SOUTH, Direction.SOUTHEAST}
    };

    private Map<Direction, Terrain> terrainMapFrom(Pixmap pixmap, int offsetX, int offsetY, Terrain[] terrains) {
        Map<Direction, Terrain> terrainMap = new EnumMap<>(Direction.class);

        for (int dy = 0; dy < 3; dy++) {
            for (int dx = 0; dx < 3; dx++) {
                Direction dir = directionGrid[dy][dx];
                if (dir == null) continue;

                int pixel = pixmap.getPixel(offsetX + dx, offsetY + dy);
                int r = (pixel >> 24) & 0xff;
                int g = (pixel >> 16) & 0xff;
                int b = (pixel >> 8) & 0xff;

                float brightness = (r + g + b) / (3f * 255f);
                int terrainIndex = Math.min((int) (brightness * terrains.length), terrains.length - 1);

                terrainMap.put(dir, terrains[terrainIndex]);
            }
        }

        return terrainMap;
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

    /**
     * @return Tile from a list whose neighbors dont match it.
     */
    public static Tile getDot(List<Tile> tiles) {
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

}
