package heroes.journey.initializers.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.Biome;
import heroes.journey.tilemap.FeatureGenerationData;
import heroes.journey.tilemap.TileHelpers;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.art.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import static heroes.journey.initializers.base.LoadTextures.OVERWORLD_TILESET;
import static heroes.journey.initializers.base.Map.KINGDOM;
import static heroes.journey.utils.art.ResourceManager.TextureManager;

public class Tiles implements InitializerInterface {

    public static final Terrain NULL_TERRAIN = new Terrain("null", "NULL", 0);
    public static Tile NULL;

    public static Tile WATER, SAND, PLAINS, HILLS;
    public static List<Tile> pathTiles, treeTiles;
    public static Tile pathDot;

    public static Biome kingdom;

    @Override
    public void init() {
        TextureRegion[][] tiles = ResourceManager.get(TextureManager.get(OVERWORLD_TILESET));

        // Base Terrains
        Terrain water = new Terrain("water", "Water", 50).register();
        Terrain plains = new Terrain("plains", "Plains", 2).register();
        Terrain hills = new Terrain("hills", "Hills", 2).register();
        Terrain sand = new Terrain("sand", "Sand", 3).register();
        Terrain path = new Terrain("path", "Path", 1).register();

        Terrain trees = new Terrain("trees", "Trees", 1).register();

        // Transition Terrains
        Terrain plainsToHill = new Terrain("plains_to_hill", "Cliff", 10).register();
        Terrain sandToHill = new Terrain("sand_to_hill", "Cliff", 10).register();
        Terrain plainsToSand = new Terrain("plains_to_sand", "Sand", 3).register();
        Terrain plainsToWater = new Terrain("plains_to_water", "Water", 50).register();
        Terrain hillToWater = new Terrain("hill_to_water", "Cliff", 50).register();
        Terrain sandToWater = new Terrain("sand_to_water", "Water", 50).register();

        // Biomes
        List<FeatureGenerationData> plainsData = new ArrayList<>();
        plainsData.add(new FeatureGenerationData(KINGDOM, 0, 1, 1));
        plainsData.add(new FeatureGenerationData(Map.TOWN, 5, 3, 5));
        plainsData.add(new FeatureGenerationData(Map.DUNGEON, 5, 1, 2));
        plainsData.add(new FeatureGenerationData(Map.MINE, 5, 5, 8));
        kingdom = new Biome("kingdom", "Kingdom", plains.getId(), plainsData).register();

        List<FeatureGenerationData> desertData = new ArrayList<>();
        desertData.add(new FeatureGenerationData(KINGDOM, 0, 1, 1));
        desertData.add(new FeatureGenerationData(Map.TOWN, 5, 3, 5));
        desertData.add(new FeatureGenerationData(Map.DUNGEON, 5, 1, 2));
        desertData.add(new FeatureGenerationData(Map.MINE, 5, 5, 10));
        Biome desertKingdom = new Biome("desert_kingdom", "Desert Kingdom", sand.getId(),
            desertData).register();

        List<FeatureGenerationData> mesaData = new ArrayList<>();
        mesaData.add(new FeatureGenerationData(KINGDOM, 0, 1, 1));
        mesaData.add(new FeatureGenerationData(Map.TOWN, 5, 3, 5));
        mesaData.add(new FeatureGenerationData(Map.DUNGEON, 5, 1, 2));
        mesaData.add(new FeatureGenerationData(Map.MINE, 5, 7, 12));
        Biome mesaKingdom = new Biome("mesa_kingdom", "Mesa Kingdom", hills.getId(), mesaData).register();

        NULL = new BaseTile(NULL_TERRAIN, 100, false, tiles[3][0]);
        TileHelpers.baseTile(NULL, NULL_TERRAIN);

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
        TileHelpers.cliffTransition(sandToHill, plainsToHill, sand, plains, hills, tiles, 10, 5, 17, true);
        TileHelpers.createWangCorner(plainsToSand, plains, sand, tiles, 500, 0, 16, true);
        TileHelpers.createWangCornerAnimated(plainsToWater, plains, water, tiles, 100, 20, 10, true);
        TileHelpers.createWangCornerAnimated(hillToWater, hills, water, tiles, 10, 20, 13, true);
        TileHelpers.cliffTransitionAnimated(plainsToWater, hillToWater, plains, hills, water, tiles, 10, 20,
            4, true);
        TileHelpers.createWangCornerAnimated(sandToWater, sand, water, tiles, 500, 20, 16, true);
        TileHelpers.cliffTransitionAnimated(sandToWater, hillToWater, sand, hills, water, tiles, 10, 20, 6,
            true);
        TileHelpers.cliffTransitionAnimated(sandToWater, plainsToWater, sand, plains, water, tiles, 10, 20, 8,
            true);

        treeTiles = TileHelpers.createWangCorner(trees, NULL_TERRAIN, trees, tiles, 500, 0, 7, false);

        pathTiles = TileHelpers.createEdge(path, plains, tiles, 10000, 12, 0);
        pathDot = TileLayout.getDot(pathTiles);
    }

}
