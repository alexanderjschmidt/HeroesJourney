package heroes.journey.initializers;

import static heroes.journey.initializers.Ids.OVERWORLD_TILESET;
import static heroes.journey.initializers.Ids.TERRAIN_HILLS;
import static heroes.journey.initializers.Ids.TERRAIN_NULL;
import static heroes.journey.initializers.Ids.TERRAIN_PLAINS;
import static heroes.journey.initializers.Ids.TERRAIN_SAND;
import static heroes.journey.initializers.Ids.TERRAIN_WATER;
import static heroes.journey.utils.art.ResourceManager.TextureManager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.tilemap.TileHelpers;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.art.ResourceManager;

public class Tiles implements InitializerInterface {

    public static Tile NULL;

    public static Tile WATER, SAND, PLAINS, HILLS;

    @Override
    public void init() {
        TextureRegion[][] tiles = ResourceManager.get(TextureManager.get(OVERWORLD_TILESET));

        NULL = new BaseTile(TERRAIN_NULL, 100, false, tiles[3][0]);
        TileHelpers.baseTile(NULL, TERRAIN_NULL);

        WATER = new AnimatedTile(TERRAIN_WATER, 300, true, TileLayout.getFrames(tiles, 21, 11, 4, 5), .2f);
        PLAINS = new BaseTile(TERRAIN_PLAINS, 1000, tiles[1][5]);
        HILLS = new BaseTile(TERRAIN_HILLS, 500, tiles[1][11]);
        SAND = new BaseTile(TERRAIN_SAND, 200, tiles[1][17]);

        TileHelpers.baseTile(WATER, TERRAIN_WATER);
        TileHelpers.baseTile(PLAINS, TERRAIN_PLAINS);
        TileHelpers.baseTile(HILLS, TERRAIN_HILLS);
        TileHelpers.baseTile(SAND, TERRAIN_SAND);
    }

}
