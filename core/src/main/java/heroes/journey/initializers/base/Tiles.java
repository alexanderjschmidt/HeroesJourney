package heroes.journey.initializers.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.helpers.WangCorner;
import heroes.journey.tilemap.helpers.WangCornerAnimated;
import heroes.journey.tilemap.wavefunction.AnimatedTile;
import heroes.journey.tilemap.wavefunction.BaseTile;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMaps;

public class Tiles implements InitializerInterface {

    public static Tile WATER, SAND, PLAINS, HILLS, CLIFF;

    static {
        TextureRegion[][] tiles = ResourceManager.get(TextureMaps.OverworldTileset);

        Terrain water = new Terrain("Water", 50);
        Terrain plains = new Terrain("Plains", 2);
        Terrain hills = new Terrain("Hills", 2);
        Terrain sand = new Terrain("Sand", 3);

        Terrain plainsToHill = new Terrain("Cliff", 10);
        Terrain sandToHill = new Terrain("Cliff", 10);
        Terrain plainsToSand = new Terrain("Sand", 3);
        Terrain plainsToWater = new Terrain("Water", 50);
        Terrain hillToWater = new Terrain("Cliff", 50);
        Terrain sandToWater = new Terrain("Water", 50);

        WATER = new AnimatedTile(water, 200, WangCornerAnimated.getFrames(tiles, 21, 11, 4, 5), .2f);
        PLAINS = new BaseTile(plains, 1000, tiles[1][5]);
        HILLS = new BaseTile(hills, 500, tiles[1][5]);
        SAND = new BaseTile(sand, 200, tiles[1][17]);

        basicTile(WATER, water);
        basicTile(PLAINS, plains);
        basicTile(HILLS, hills);
        basicTile(SAND, sand);

        WangCorner.create(plainsToHill, plains, hills, tiles, 10, 0, 10);
        WangCorner.create(sandToHill, sand, hills, tiles, 10, 0, 13);
        WangCorner.create(plainsToSand, plains, sand, tiles, 10, 0, 16);
        WangCornerAnimated.create(plainsToWater, plains, water, tiles, 10, 20, 10);
        WangCornerAnimated.create(hillToWater, hills, water, tiles, 10, 20, 13);
        WangCornerAnimated.create(sandToWater, sand, water, tiles, 10, 20, 16);
    }

    public static void basicTile(Tile tile, Terrain terrain) {
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
