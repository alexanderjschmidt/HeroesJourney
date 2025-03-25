package heroes.journey.initializers.base;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.helpers.WangCorner;
import heroes.journey.tilemap.helpers.WangCornerAnimated;
import heroes.journey.tilemap.helpers.WangEdge;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.tilemap.wavefunction.AnimatedTile;
import heroes.journey.tilemap.wavefunction.BaseTile;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMaps;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;

public class Tiles implements InitializerInterface {

    public static Terrain PATH;
    public static Tile WATER, SAND, PLAINS, HILLS;
    public static List<Tile> pathTiles;
    public static Tile HOUSE;
    public static ActionTerrain house;

    static {
        TextureRegion[][] tiles = ResourceManager.get(TextureMaps.OverworldTileset);

        Terrain water = new Terrain("Water", 50);
        Terrain plains = new Terrain("Plains", 2);
        Terrain hills = new Terrain("Hills", 2);
        Terrain sand = new Terrain("Sand", 3);
        PATH = new Terrain("Path", 1);

        house = new ActionTerrain("House", 0);
        HOUSE = new BaseTile(house, 0, tiles[7][12]);

        Terrain plainsToHill = new Terrain("Cliff", 10);
        Terrain sandToHill = new Terrain("Cliff", 10);
        Terrain plainsToSand = new Terrain("Sand", 3);
        Terrain plainsToWater = new Terrain("Water", 50);
        Terrain hillToWater = new Terrain("Cliff", 50);
        Terrain sandToWater = new Terrain("Water", 50);

        WATER = new AnimatedTile(water, 100, WangCornerAnimated.getFrames(tiles, 21, 11, 4, 5), .2f);
        PLAINS = new BaseTile(plains, 10000, tiles[1][5]);
        HILLS = new BaseTile(hills, 2000, tiles[1][11]);
        SAND = new BaseTile(sand, 500, tiles[1][17]);

        baseTile(WATER, water);
        baseTile(PLAINS, plains);
        baseTile(HILLS, hills);
        baseTile(SAND, sand);

        WangCorner.create(plainsToHill, plains, hills, tiles, 500, 0, 10);
        WangCorner.cliffTransition(plainsToHill, plains, hills, tiles, 1, 5, 9);
        WangCorner.create(sandToHill, sand, hills, tiles, 50, 0, 13);
        WangCorner.cliffTransition(sandToHill, sand, hills, tiles, 1, 5, 13);
        WangCorner.cliffTransition(sandToHill, plainsToHill, sand, plains, hills, tiles, 1, 5, 17);
        WangCorner.create(plainsToSand, plains, sand, tiles, 500, 0, 16);
        WangCornerAnimated.create(plainsToWater, plains, water, tiles, 300, 20, 10);
        WangCornerAnimated.create(hillToWater, hills, water, tiles, 10, 20, 13);
        WangCornerAnimated.cliffTransition(plainsToWater, hillToWater, plains, hills, water, tiles, 1, 20, 4);
        WangCornerAnimated.create(sandToWater, sand, water, tiles, 500, 20, 16);
        WangCornerAnimated.cliffTransition(sandToWater, hillToWater, sand, hills, water, tiles, 1, 20, 6);
        WangCornerAnimated.cliffTransition(sandToWater, plainsToWater, sand, plains, water, tiles, 1, 20, 8);

        pathTiles = WangEdge.create(PATH, plains, tiles, 10000, 12, 0);
    }

    public static void baseTile(Tile tile, Terrain terrain) {
        tile.add(Direction.NORTHWEST, terrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, terrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, terrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, terrain)
            .add(Direction.WEST, terrain);
        WaveFunctionCollapse.baseTiles.add(tile);
    }
}
