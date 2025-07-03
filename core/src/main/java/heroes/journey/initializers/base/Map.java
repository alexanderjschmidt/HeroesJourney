package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.EntityFactory.generateCapital;
import static heroes.journey.initializers.base.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.EntityFactory.generateMine;
import static heroes.journey.initializers.base.EntityFactory.generateTown;
import static heroes.journey.registries.Registries.ItemManager;
import static heroes.journey.registries.Registries.TerrainManager;
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
import heroes.journey.entities.Position;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.FeatureGenerationData;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerationException;
import heroes.journey.utils.worldgen.MapGenerator;
import heroes.journey.utils.worldgen.effects.BasicMapGenerationEffect;
import heroes.journey.utils.worldgen.effects.NoiseMapEffect;
import heroes.journey.utils.worldgen.effects.VoronoiRegionEffect;
import heroes.journey.utils.worldgen.effects.WaveFunctionCollapseMapEffect;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;

public class Map implements InitializerInterface {

    public static int MAP_SIZE = 100;
    // Kingdoms
    public static int NUM_PLAYERS = 3;

    public static FeatureType KINGDOM, TOWN, DUNGEON, MINE;

    @Override
    public void init() {
        // feature types
        KINGDOM = new FeatureType("kingdom", "Kingdom") {
            @Override
            public UUID generateFeatureInner(GameState gs, Position pos) {
                return generateCapital(gs, pos.getX(), pos.getY());
            }
        };
        TOWN = new FeatureType("town", "Town") {
            @Override
            public UUID generateFeatureInner(GameState gs, Position pos) {
                return generateTown(gs, pos.getX(), pos.getY());
            }
        };
        DUNGEON = new FeatureType("dungeon", "Dungeon") {
            @Override
            public UUID generateFeatureInner(GameState gs, Position pos) {
                return generateDungeon(gs, pos.getX(), pos.getY());
            }
        };
        MINE = new FeatureType("mine", "Mine") {
            @Override
            public UUID generateFeatureInner(GameState gs, Position pos) {
                return generateMine(gs, pos.getX(), pos.getY());
            }
        };

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
                                    genData.getFeatureType().getId() + " features for " +
                                    region.getBiome().getId() + " biome.");
                        }
                        region.getFeatures().add(genData.getFeatureType().generateFeature(gameState, pos));
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
                        region.getFeatures().add(genData.getFeatureType().generateFeature(gameState, pos));
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

        // Wave Function collapse keeping houses and path placements
        MapGenerationEffect wfc = new WaveFunctionCollapseMapEffect("waveFunctionCollapse", (gs, pos) -> {
            WeightedRandomPicker<Tile> possibleTilesPicker = new WeightedRandomPicker<>();
            if (gs.getMap().getTileMap()[pos.getX()][pos.getY()] == Tiles.pathDot) {
                for (Tile t : Tiles.pathTiles) {
                    possibleTilesPicker.addItem(t, t.getWeight());
                }
                possibleTilesPicker.remove(Tiles.pathDot);
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
            } else if (gs.getMap().getTileMap()[pos.getX()][pos.getY()] == Tiles.PLAINS) {
                for (Tile t : Tiles.treeTiles) {
                    possibleTiles.addItem(t, t.getWeight());
                }
                possibleTiles.addItem(Tiles.NULL, possibleTiles.getTotalWeight());
            } else {
                possibleTiles.addItem(Tiles.NULL, 1);
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
                        LoadTextures.PLAYER_SPRITE);
                    factory.addMovableComponents(playerId, WanderType.Region);
                    EntityEdit player = factory.addPlayerComponents(playerId);
                    player.create(PlayerComponent.class).playerId(PlayerInfo.get().getUuid());
                    InventoryComponent.get(gameState.getWorld(), playerId)
                        .add(ItemManager.get("health_potion"), 3)
                        .add(ItemManager.get("iron_ingot"), 5)
                        .add(ItemManager.get("chest_plate"));
                    PlayerInfo.get().setPlayerId(playerId);
                } else if (region.ringPos() % 2 == 0) {
                    UUID opponentId = factory.createEntity();
                    factory.addRenderComponents(opponentId, "Opponent", pos.getX(), pos.getY(),
                        LoadTextures.PLAYER_SPRITE);
                    factory.addMovableComponents(opponentId, WanderType.Region);
                    factory.addPlayerComponents(opponentId);
                }
            }
        }).register(MapGenerator.entityPhase);
    }

}
