package heroes.journey.utils.worldgen;

import static heroes.journey.modlib.Ids.BASE_TILE_NULL;
import static heroes.journey.modlib.Ids.BASE_TILE_PLAINS;
import static heroes.journey.mods.Registries.FeatureTypeManager;
import static heroes.journey.mods.Registries.ItemManager;
import static heroes.journey.mods.Registries.TerrainManager;
import static heroes.journey.mods.Registries.TileBatchManager;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.poisonDiskSample;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.surroundedBySame;
import static heroes.journey.utils.worldgen.utils.WaveFunctionCollapse.possibleTiles;

import java.util.List;
import java.util.UUID;

import com.artemis.EntityEdit;
import com.artemis.utils.IntBag;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.utils.WanderType;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.utils.Position;
import heroes.journey.modlib.worldgen.FeatureGenerationData;
import heroes.journey.systems.EntityFactory;
import heroes.journey.tilemap.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.effects.BasicMapGenerationEffect;
import heroes.journey.utils.worldgen.effects.NoiseMapEffect;
import heroes.journey.utils.worldgen.effects.VoronoiRegionEffect;
import heroes.journey.utils.worldgen.effects.WaveFunctionCollapseMapEffect;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;

public class MapGenPlan {

    public static int MAP_SIZE = 100;
    // Kingdoms
    public static int NUM_PLAYERS = 3;

    // feature types are now defined in mod scripts

    public void init() {
        /**
         * New Gen Plan
         * Noise (create continent)
         * Generate starting cities
         * generate voronoi regions using starting points as seeds and X regions to gen
         * apply region generation
         *  set tile to biome base tile
         *  generate features random in region
         * cross region generation (roads)
         * wavefunctioncollapse tilemap layer
         * wavefunctioncollapse env layer
         * Add players (this could be a part of kingdom biome generation?)
         */

        // Generate Smooth Noise
        new NoiseMapEffect("base_noise", 50, 0.7f, 5, 2).register(MapGenerator.noisePhase);

        // Capitals
        MapGenerationEffect voronoiRegion = new VoronoiRegionEffect("voronoiRegions",
            List.of(new Integer[] {NUM_PLAYERS * 2, NUM_PLAYERS, 1}),
            List.of(new Boolean[] {false, true, false})).register(MapGenerator.worldGenPhase);

        MapGenerationEffect biomeGen = new BasicMapGenerationEffect("biomeGen", gameState -> {
            Tile[][] map = gameState.getMap().getTileMap();
            // Set tiles to base tiles
            IntBag regions = GameState.global().getWorld().getRegionSubscription().getEntities();
            int[] regionIds = regions.getData();

            for (int r = 0; r < regions.size(); r++) {
                int entityId = regionIds[r];
                UUID id = IdComponent.get(GameState.global().getWorld(), entityId);
                RegionComponent region = RegionComponent.get(GameState.global().getWorld(), id);
                PositionComponent regionCenter = PositionComponent.get(GameState.global().getWorld(), id);
                for (Position pos : region.getTiles()) {
                    map[pos.getX()][pos.getY()] = TileManager.get()
                        .getBaseTile(TerrainManager.get(region.getBiome().getBaseTerrain()));
                }
                for (FeatureGenerationData genData : region.getBiome().getFeatureGenerationData()) {
                    for (int i = 0; i < genData.getMinInRegion(); i++) {
                        Position pos = poisonDiskSample(gameState.getWorld(), genData.getMinDist(), region,
                            regionCenter);
                        if (pos == null) {
                            throw new MapGenerationException(
                                "Failed to generate minimum number (" + genData.getMinInRegion() + ") of " +
                                    genData.getFeatureTypeId() + " features for " +
                                    region.getBiome().getId() + " biome.");
                        }
                        region.getFeatures()
                            .add(FeatureTypeManager.get(genData.getFeatureTypeId())
                                .generateFeature(gameState, pos));
                        //TODO remove this at some point, seems aggressive
                        gameState.getWorld().basicProcess();
                    }
                }
                for (FeatureGenerationData genData : region.getBiome().getFeatureGenerationData()) {
                    int bound = genData.getMaxInRegion() - genData.getMinInRegion();
                    if (bound <= 0) {
                        continue;
                    }
                    int randomVariance = Random.get().nextInt(bound);
                    for (int i = 0; i < randomVariance; i++) {
                        Position pos = poisonDiskSample(gameState.getWorld(), genData.getMinDist(), region,
                            regionCenter);
                        if (pos == null)
                            break;
                        region.getFeatures()
                            .add(FeatureTypeManager.get(genData.getFeatureTypeId())
                                .generateFeature(gameState, pos));
                    }
                }
            }

            // for every feature data in biome, create min number using poison disk sampling on all features.
            // then generate up to max extras
            gameState.getMap().setTileMap(map);
        }).register(voronoiRegion);

        // Add Paths between capitals and towns
        /*MapGenerationEffect kingdomPaths = new BuildRoadBetweenFeaturesEffect("kingdomPaths", KINGDOM,
            KINGDOM).register(kingdomConnection);
        MapGenerationEffect paths = new BuildRoadBetweenFeaturesEffect("paths", KINGDOM, TOWN).register(
            kingdomPaths);*/

        Tile pathDot = TileBatchManager.get(Ids.TILE_BATCH_PATH_EDGE).getDot();
        // Wave Function collapse keeping houses and path placements
        MapGenerationEffect wfc = new WaveFunctionCollapseMapEffect("waveFunctionCollapse", (gs, pos) -> {
            WeightedRandomPicker<Tile> possibleTilesPicker = new WeightedRandomPicker<>();
            if (gs.getMap().getTileMap()[pos.getX()][pos.getY()] == pathDot) {
                for (Tile t : TileBatchManager.get(Ids.TILE_BATCH_PATH_EDGE).getTiles()) {
                    possibleTilesPicker.addItem(t, t.getWeight());
                }
                possibleTilesPicker.remove(pathDot);
            } else if (gs.getMap().getEnvironment()[pos.getX()][pos.getY()] != null ||
                surroundedBySame(gs.getMap().getTileMap(), pos.getX(), pos.getY(), 1)) {
                possibleTilesPicker.addItem(gs.getMap().getTileMap()[pos.getX()][pos.getY()], 1);
            } else {
                for (Tile t : possibleTiles) {
                    possibleTilesPicker.addItem(t, t.getWeight());
                }
            }
            return possibleTilesPicker;
        }).register(MapGenerator.postWorldGenPhase);

        // Create Trees
        new WaveFunctionCollapseMapEffect("trees", true, (gs, pos) -> {
            WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();
            if (gs.getMap().getEnvironment()[pos.getX()][pos.getY()] != null) {
                possibleTiles.addItem(gs.getMap().getEnvironment()[pos.getX()][pos.getY()], 1);
            } else if (gs.getMap().getTileMap()[pos.getX()][pos.getY()] ==
                TileManager.BASE_TILES.get(BASE_TILE_PLAINS)) {
                for (Tile t : TileBatchManager.get(Ids.TILE_BATCH_TREES_CORNER).getTiles()) {
                    possibleTiles.addItem(t, t.getWeight());
                }
                possibleTiles.addItem(TileManager.BASE_TILES.get(BASE_TILE_NULL),
                    possibleTiles.getTotalWeight());
            } else {
                possibleTiles.addItem(TileManager.BASE_TILES.get(BASE_TILE_NULL), 1);
            }
            return possibleTiles;
        }).register(wfc);

        // Add Entities
        new BasicMapGenerationEffect("entities", gameState -> {
            EntityFactory factory = gameState.getWorld().getEntityFactory();
            IntBag regions = GameState.global().getWorld().getRegionSubscription().getEntities();
            int[] regionIds = regions.getData();

            for (int r = 0; r < regions.size(); r++) {
                int entityId = regionIds[r];
                UUID id = IdComponent.get(GameState.global().getWorld(), entityId);
                RegionComponent region = RegionComponent.get(GameState.global().getWorld(), id);
                if (region.ring() != 0)
                    continue;
                PositionComponent pos = PositionComponent.get(gameState.getWorld(), id);
                if (region.ringPos() == 0) {
                    UUID playerId = factory.createEntity();
                    factory.addRenderComponents(playerId, "Player", pos.getX(), pos.getY(),
                        Ids.ADVENTUROUS_ADOLESCENT);
                    factory.addMovableComponents(playerId, WanderType.Region);
                    factory.addPlayerComponents(playerId);

                    //TODO move to entitiyFactory
                    EntityEdit player = gameState.getWorld().edit(playerId);
                    player.create(PlayerComponent.class).playerId(PlayerInfo.get().getUuid());
                    InventoryComponent.get(gameState.getWorld(), playerId)
                        .add(ItemManager.get(Ids.ITEM_IRON_INGOT), 5)
                        .add(ItemManager.get(Ids.ITEM_CHEST_PLATE));
                    PlayerInfo.get().setPlayerId(playerId);
                } else if (region.ringPos() % 2 == 0) {
                    UUID opponentId = factory.createEntity();
                    factory.addRenderComponents(opponentId, "Opponent", pos.getX(), pos.getY(),
                        Ids.ADVENTUROUS_ADOLESCENT);
                    factory.addMovableComponents(opponentId, WanderType.Region);
                    factory.addPlayerComponents(opponentId);
                }
            }
        }).register(MapGenerator.entityPhase);
    }

}
