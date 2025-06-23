package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.EntityFactory.addOverworldComponents;
import static heroes.journey.initializers.base.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.EntityFactory.generateTown;
import static heroes.journey.registries.Registries.ItemManager;
import static heroes.journey.registries.Registries.RegionManager;
import static heroes.journey.registries.Registries.TerrainManager;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.poisonDiskSample;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.surroundedBySame;
import static heroes.journey.utils.worldgen.utils.WaveFunctionCollapse.possibleTiles;

import java.util.List;
import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.registries.FeatureManager;
import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FeatureGenerationData;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.tilemap.Region;
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
    public static int NUM_KINGDOMS = 3;

    public static FeatureType KINGDOM, TOWN, DUNGEON;

    @Override
    public void init() {
        // feature types
        KINGDOM = new FeatureType("kingdom", "Kingdom") {
            @Override
            public Feature generateFeature(GameState gs, Position pos) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.CAPITAL);
                UUID kingdomId = generateTown(gs, pos.getX(), pos.getY(), true);
                return new Feature(kingdomId, KINGDOM, pos);
            }
        };
        TOWN = new FeatureType("town", "Town") {
            @Override
            public Feature generateFeature(GameState gs, Position pos) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.TOWN);
                UUID townId = generateTown(gs, pos.getX(), pos.getY(), false);
                Feature town = new Feature(townId, TOWN, pos);
                return town;
            }
        };
        DUNGEON = new FeatureType("dungeon", "Dungeon") {
            @Override
            public Feature generateFeature(GameState gs, Position pos) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.DUNGEON);
                UUID dungeonId = generateDungeon(gs, pos.getX(), pos.getY());
                return new Feature(dungeonId, DUNGEON, pos);
            }
        };

        /**
         * New Gen Plan
         * Noise (create continent)
         * Generate kingdom region starting points
         * generate voronoi regions using starting points as seeds and X regions to gen
         * apply region generation
         *  set tile to biome base tile
         *  generate features random in region
         * cross region generation (roads)
         * wavefunctioncollapse tilemap layer
         * wavefunctioncollapse env layer
         * Add players (this could be a part of kingdom biome generation?)
         */

        /**
         * Ideal Gen Plan
         * Noise (create continent)
         * generate voronoi regions using ring region inputs
         * apply region generation
         *  set tile to biome base tile
         *  generate features random in region
         *  Add entities (players)
         * cross region generation (roads)
         * wavefunctioncollapse tilemap and env layer
         */

        // Generate Smooth Noise
        new NoiseMapEffect("base_noise", 50, 0.7f, 5, 2).register(MapGenerator.noisePhase);

        // Capitals
        MapGenerationEffect voronoiRegion = new VoronoiRegionEffect("voronoiRegions",
            List.of(new Integer[] {NUM_KINGDOMS * 2, NUM_KINGDOMS, 1}),
            List.of(new Boolean[] {false, true, false})).register(MapGenerator.worldGenPhase);

        MapGenerationEffect biomeGen = new BasicMapGenerationEffect("biomeGen", gameState -> {
            Tile[][] map = gameState.getMap().getTileMap();
            // Set tiles to base tiles
            for (Region region : RegionManager.values()) {
                for (Position pos : region.getTiles()) {
                    map[pos.getX()][pos.getY()] = TileManager.get()
                        .getBaseTile(TerrainManager.get(region.getBiome().getBaseTerrain()));
                }
                for (FeatureGenerationData genData : region.getBiome().getFeatureGenerationData()) {
                    for (int i = 0; i < genData.getMinInRegion(); i++) {
                        Position pos = poisonDiskSample(genData.getMinDist(), FeatureManager.get(), region);
                        if (pos == null) {
                            throw new MapGenerationException(
                                "Failed to generate minimum number (" + genData.getMinInRegion() + ") of " +
                                    genData.getFeatureType().getId() + " features for " +
                                    region.getBiome().getId() + " biome.");
                        }
                        region.getFeatures().add(genData.getFeatureType().generateFeature(gameState, pos));
                    }
                }
                for (FeatureGenerationData genData : region.getBiome().getFeatureGenerationData()) {
                    int bound = genData.getMaxInRegion() - genData.getMinInRegion();
                    if (bound <= 0) {
                        continue;
                    }
                    int randomVariance = Random.get().nextInt(bound);
                    for (int i = 0; i < randomVariance; i++) {
                        Position pos = poisonDiskSample(genData.getMinDist(), FeatureManager.get(), region);
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
                surroundedBySame(gs.getMap().getTileMap(), pos.getX(), pos.getY())) {
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
            List<Feature> kingdoms = FeatureManager.get(KINGDOM);
            for (Feature kingdom : kingdoms) {
                if (kingdom == kingdoms.getFirst()) {
                    EntityEdit player = gameState.getWorld().createEntity().edit();
                    UUID playerId = addOverworldComponents(gameState.getWorld(), player,
                        kingdom.location.getX(), kingdom.location.getY(), LoadTextures.PLAYER_SPRITE,
                        new MCTSAI());
                    player.create(PlayerComponent.class).playerId(PlayerInfo.get().getUuid());
                    player.create(NamedComponent.class).name("Player");
                    InventoryComponent.get(gameState.getWorld(), playerId)
                        .add(ItemManager.get("health_potion"), 3)
                        .add(ItemManager.get("iron_ingot"), 5)
                        .add(ItemManager.get("chest_plate"));
                    PlayerInfo.get().setPlayerId(playerId);
                } else {
                    EntityEdit opponent = gameState.getWorld().createEntity().edit();
                    addOverworldComponents(gameState.getWorld(), opponent, kingdom.location.getX(),
                        kingdom.location.getY(), LoadTextures.PLAYER_SPRITE, new MCTSAI());
                }
            }
        }).register(MapGenerator.entityPhase);
    }

}
