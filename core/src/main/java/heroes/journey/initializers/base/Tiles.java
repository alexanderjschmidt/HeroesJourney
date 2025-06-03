package heroes.journey.initializers.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.TileHelpers;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.*;
import heroes.journey.utils.art.ResourceManager;

import java.util.List;

public class Tiles implements InitializerInterface {

    public static final Terrain NULL_TERRAIN = new Terrain("NULL", 0);
    public static Tile NULL;

    public static Terrain path, plains;
    public static Tile WATER, SAND, PLAINS, HILLS;
    public static List<Tile> pathTiles, treeTiles;
    public static Tile pathDot;
    public static Tile CAPITAL, TOWN, DUNGEON;
    public static ActionTerrain capital, town, trees, dungeon;


    @Override
    public void init() {
        TextureRegion[][] tiles = ResourceManager.get(LoadTextures.OverworldTileset);

        NULL = new BaseTile(NULL_TERRAIN, 100, false, tiles[3][0]);
        TileHelpers.baseTile(NULL, NULL_TERRAIN);

        // Base Terrains
        Terrain water = new Terrain("Water", 50);
        plains = new Terrain("Plains", 2);
        Terrain hills = new Terrain("Hills", 2);
        Terrain sand = new Terrain("Sand", 3);
        path = new Terrain("Path", 1);

        capital = new ActionTerrain("Capital", 0);
        CAPITAL = new BaseTile(capital, 0, false, tiles[9][14]);
        TileHelpers.baseTile(CAPITAL, NULL_TERRAIN, false);

        town = new ActionTerrain("Town", 0);
        TOWN = new BaseTile(town, 0, false, tiles[7][12]);
        TileHelpers.baseTile(TOWN, NULL_TERRAIN, false);

        dungeon = new ActionTerrain("Dungeon", 1);
        DUNGEON = new BaseTile(dungeon, 0, false, tiles[17][4]);
        TileHelpers.baseTile(DUNGEON, NULL_TERRAIN, false);

        trees = new ActionTerrain("Trees", 1);

        // Transition Terrains
        Terrain plainsToHill = new Terrain("Cliff", 10);
        Terrain sandToHill = new Terrain("Cliff", 10);
        Terrain plainsToSand = new Terrain("Sand", 3);
        Terrain plainsToWater = new Terrain("Water", 50);
        Terrain hillToWater = new Terrain("Cliff", 50);
        Terrain sandToWater = new Terrain("Water", 50);

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
        TileHelpers.createWangCornerAnimated(plainsToWater, plains, water, tiles, 300, 20, 10);
        TileHelpers.createWangCornerAnimated(hillToWater, hills, water, tiles, 10, 20, 13);
        TileHelpers.cliffTransitionAnimated(plainsToWater, hillToWater, plains, hills, water, tiles, 1, 20, 4);
        TileHelpers.createWangCornerAnimated(sandToWater, sand, water, tiles, 500, 20, 16);
        TileHelpers.cliffTransitionAnimated(sandToWater, hillToWater, sand, hills, water, tiles, 1, 20, 6);
        TileHelpers.cliffTransitionAnimated(sandToWater, plainsToWater, sand, plains, water, tiles, 1, 20, 8);

        treeTiles = TileHelpers.createWangCorner(trees, NULL_TERRAIN, trees, tiles, 500, 0, 7, false);

        pathTiles = TileHelpers.createEdge(path, plains, tiles, 10000, 12, 0);
        pathDot = TileLayout.getDot(pathTiles);
    }

}
