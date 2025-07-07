package heroes.journey.initializers;

import static heroes.journey.initializers.Ids.OVERWORLD_TILESET;
import static heroes.journey.initializers.Ids.TERRAIN_HILLS;
import static heroes.journey.initializers.Ids.TERRAIN_HILL_TO_WATER;
import static heroes.journey.initializers.Ids.TERRAIN_NULL;
import static heroes.journey.initializers.Ids.TERRAIN_PATH;
import static heroes.journey.initializers.Ids.TERRAIN_PLAINS;
import static heroes.journey.initializers.Ids.TERRAIN_PLAINS_TO_HILL;
import static heroes.journey.initializers.Ids.TERRAIN_PLAINS_TO_SAND;
import static heroes.journey.initializers.Ids.TERRAIN_PLAINS_TO_WATER;
import static heroes.journey.initializers.Ids.TERRAIN_SAND;
import static heroes.journey.initializers.Ids.TERRAIN_SAND_TO_HILL;
import static heroes.journey.initializers.Ids.TERRAIN_SAND_TO_WATER;
import static heroes.journey.initializers.Ids.TERRAIN_TREES;
import static heroes.journey.initializers.Ids.TERRAIN_WATER;
import static heroes.journey.utils.art.ResourceManager.TextureManager;
import static heroes.journey.utils.worldgen.MapGenPlan.KINGDOM;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.tilemap.Biome;
import heroes.journey.tilemap.FeatureGenerationData;
import heroes.journey.tilemap.TileHelpers;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.AnimatedTile;
import heroes.journey.tilemap.wavefunctiontiles.BaseTile;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.worldgen.MapGenPlan;

public class Tiles implements InitializerInterface {

    public static Tile NULL;

    public static Tile WATER, SAND, PLAINS, HILLS;
    public static List<Tile> pathTiles, treeTiles;
    public static Tile pathDot;

    public static Biome kingdom;

    @Override
    public void init() {
        TextureRegion[][] tiles = ResourceManager.get(TextureManager.get(OVERWORLD_TILESET));

        // Biomes
        List<FeatureGenerationData> plainsData = new ArrayList<>();
        plainsData.add(new FeatureGenerationData(KINGDOM, 0, 1, 1));
        plainsData.add(new FeatureGenerationData(MapGenPlan.TOWN, 5, 3, 5));
        plainsData.add(new FeatureGenerationData(MapGenPlan.DUNGEON, 5, 1, 2));
        plainsData.add(new FeatureGenerationData(MapGenPlan.MINE, 5, 5, 8));
        kingdom = new Biome("kingdom", "Kingdom", TERRAIN_PLAINS, plainsData).register();

        List<FeatureGenerationData> desertData = new ArrayList<>();
        desertData.add(new FeatureGenerationData(KINGDOM, 0, 1, 1));
        desertData.add(new FeatureGenerationData(MapGenPlan.TOWN, 5, 3, 5));
        desertData.add(new FeatureGenerationData(MapGenPlan.DUNGEON, 5, 1, 2));
        desertData.add(new FeatureGenerationData(MapGenPlan.MINE, 5, 5, 10));
        Biome desertKingdom = new Biome("desert_kingdom", "Desert Kingdom", TERRAIN_SAND,
            desertData).register();

        List<FeatureGenerationData> mesaData = new ArrayList<>();
        mesaData.add(new FeatureGenerationData(KINGDOM, 0, 1, 1));
        mesaData.add(new FeatureGenerationData(MapGenPlan.TOWN, 5, 3, 5));
        mesaData.add(new FeatureGenerationData(MapGenPlan.DUNGEON, 5, 1, 2));
        mesaData.add(new FeatureGenerationData(MapGenPlan.MINE, 5, 7, 12));
        Biome mesaKingdom = new Biome("mesa_kingdom", "Mesa Kingdom", TERRAIN_HILLS, mesaData).register();

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

        TileHelpers.createWangCorner(TERRAIN_PLAINS_TO_HILL, TERRAIN_PLAINS, TERRAIN_HILLS, tiles, 500, 0, 10,
            true);
        TileHelpers.cliffTransitionTapper(TERRAIN_PLAINS_TO_HILL, TERRAIN_PLAINS, TERRAIN_HILLS, tiles, 1, 5,
            9, true);
        TileHelpers.createWangCorner(TERRAIN_SAND_TO_HILL, TERRAIN_SAND, TERRAIN_HILLS, tiles, 50, 0, 13,
            true);
        TileHelpers.cliffTransitionTapper(TERRAIN_SAND_TO_HILL, TERRAIN_SAND, TERRAIN_HILLS, tiles, 1, 5, 13,
            true);
        TileHelpers.cliffTransition(TERRAIN_SAND_TO_HILL, TERRAIN_PLAINS_TO_HILL, TERRAIN_SAND,
            TERRAIN_PLAINS, TERRAIN_HILLS, tiles, 10, 5, 17, true);
        TileHelpers.createWangCorner(TERRAIN_PLAINS_TO_SAND, TERRAIN_PLAINS, TERRAIN_SAND, tiles, 500, 0, 16,
            true);
        TileHelpers.createWangCornerAnimated(TERRAIN_PLAINS_TO_WATER, TERRAIN_PLAINS, TERRAIN_WATER, tiles,
            100, 20, 10, true);
        TileHelpers.createWangCornerAnimated(TERRAIN_HILL_TO_WATER, TERRAIN_HILLS, TERRAIN_WATER, tiles, 10,
            20, 13, true);
        TileHelpers.cliffTransitionAnimated(TERRAIN_PLAINS_TO_WATER, TERRAIN_HILL_TO_WATER, TERRAIN_PLAINS,
            TERRAIN_HILLS, TERRAIN_WATER, tiles, 10, 20, 4, true);
        TileHelpers.createWangCornerAnimated(TERRAIN_SAND_TO_WATER, TERRAIN_SAND, TERRAIN_WATER, tiles, 500,
            20, 16, true);
        TileHelpers.cliffTransitionAnimated(TERRAIN_SAND_TO_WATER, TERRAIN_HILL_TO_WATER, TERRAIN_SAND,
            TERRAIN_HILLS, TERRAIN_WATER, tiles, 10, 20, 6, true);
        TileHelpers.cliffTransitionAnimated(TERRAIN_SAND_TO_WATER, TERRAIN_PLAINS_TO_WATER, TERRAIN_SAND,
            TERRAIN_PLAINS, TERRAIN_WATER, tiles, 10, 20, 8, true);

        treeTiles = TileHelpers.createWangCorner(TERRAIN_TREES, TERRAIN_NULL, TERRAIN_TREES, tiles, 500, 0, 7,
            false);

        pathTiles = TileHelpers.createEdge(TERRAIN_PATH, TERRAIN_PLAINS, tiles, 10000, 12, 0);
        pathDot = TileLayout.getDot(pathTiles);
    }

}
