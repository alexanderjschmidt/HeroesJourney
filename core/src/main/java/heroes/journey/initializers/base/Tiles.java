package heroes.journey.initializers.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.TileHelpers;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.art.ResourceManager;

import java.util.List;

public class Tiles implements InitializerInterface {

    public static final Terrain NULL_TERRAIN = new Terrain("null", "NULL", 0);
    public static Tile NULL;

    public static Tile WATER, SAND, PLAINS, HILLS;
    public static List<Tile> pathTiles, treeTiles;
    public static Tile pathDot;
    public static Tile CAPITAL, TOWN, DUNGEON;

    @Override
    public void init() {
        TextureRegion[][] tiles = ResourceManager.get(ResourceManager.OverworldTileset);

        // Base Terrains
        Terrain water = new Terrain("water", "Water", 50).register();
        Terrain plains = new Terrain("plains", "Plains", 2).register();
        Terrain hills = new Terrain("hills", "Hills", 2).register();
        Terrain sand = new Terrain("sand", "Sand", 3).register();
        Terrain path = new Terrain("path", "Path", 1).register();

        Terrain capital = new Terrain("capital", "Capital", 0).register();
        Terrain town = new Terrain("town", "Town", 0).register();
        Terrain dungeon = new Terrain("dungeon", "Dungeon", 1).register();
        Terrain trees = new Terrain("trees", "Trees", 1).register();

        // Transition Terrains
        Terrain plainsToHill = new Terrain("plains_to_hill", "Cliff", 10).register();
        Terrain sandToHill = new Terrain("sand_to_hill", "Cliff", 10).register();
        Terrain plainsToSand = new Terrain("plains_to_sand", "Sand", 3).register();
        Terrain plainsToWater = new Terrain("plains_to_water", "Water", 50).register();
        Terrain hillToWater = new Terrain("hill_to_water", "Cliff", 50).register();
        Terrain sandToWater = new Terrain("sand_to_water", "Water", 50).register();

        NULL = new BaseTile(NULL_TERRAIN, 100, false, tiles[3][0]);
        TileHelpers.baseTile(NULL, NULL_TERRAIN);

        CAPITAL = new BaseTile(capital, 0, false, tiles[9][14]);
        TileHelpers.baseTile(CAPITAL, NULL_TERRAIN, false);

        TOWN = new BaseTile(town, 0, false, tiles[7][12]);
        TileHelpers.baseTile(TOWN, NULL_TERRAIN, false);

        DUNGEON = new BaseTile(dungeon, 0, false, tiles[17][4]);
        TileHelpers.baseTile(DUNGEON, NULL_TERRAIN, false);

        WATER = new AnimatedTile(water, 300, true, TileLayout.getFrames(tiles, 21, 11, 4, 5), .2f);
        PLAINS = new BaseTile(plains, 1000, tiles[1][5]);
        HILLS = new BaseTile(hills, 500, tiles[1][11]);
        SAND = new BaseTile(sand, 200, tiles[1][17]);

        TileHelpers.baseTile(WATER, water);
        TileHelpers.baseTile(PLAINS, plains);
        TileHelpers.baseTile(HILLS, hills);
        TileHelpers.baseTile(SAND, sand);

        TileHelpers.createWangCorner(plainsToHill, plains, hills, tiles, 500, 0, 10, true);
        TileHelpers.cliffTransitionTapper(plainsToHill, plains, hills, tiles, 1, 5, 9, true);
        TileHelpers.createWangCorner(sandToHill, sand, hills, tiles, 50, 0, 13, true);
        TileHelpers.cliffTransitionTapper(sandToHill, sand, hills, tiles, 1, 5, 13, true);
        TileHelpers.cliffTransition(sandToHill, plainsToHill, sand, plains, hills, tiles, 1, 5, 17, true);
        TileHelpers.createWangCorner(plainsToSand, plains, sand, tiles, 500, 0, 16, true);
        TileHelpers.createWangCornerAnimated(plainsToWater, plains, water, tiles, 100, 20, 10, true);
        TileHelpers.createWangCornerAnimated(hillToWater, hills, water, tiles, 10, 20, 13, true);
        TileHelpers.cliffTransitionAnimated(plainsToWater, hillToWater, plains, hills, water, tiles, 1, 20,
            4, true);
        TileHelpers.createWangCornerAnimated(sandToWater, sand, water, tiles, 500, 20, 16, true);
        TileHelpers.cliffTransitionAnimated(sandToWater, hillToWater, sand, hills, water, tiles, 1, 20, 6, true);
        TileHelpers.cliffTransitionAnimated(sandToWater, plainsToWater, sand, plains, water, tiles, 1, 20, 8, true);

        treeTiles = TileHelpers.createWangCorner(trees, NULL_TERRAIN, trees, tiles, 500, 0, 7, false);

        pathTiles = TileHelpers.createEdge(path, plains, tiles, 10000, 12, 0);
        pathDot = TileLayout.getDot(pathTiles);
    }

}
