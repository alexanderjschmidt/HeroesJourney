package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.factories.EntityFactory.addOverworldComponents;
import static heroes.journey.initializers.base.factories.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.factories.EntityFactory.generateTown;
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
import heroes.journey.initializers.base.factories.MonsterFactory;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerator;
import heroes.journey.utils.worldgen.effects.BasicMapGenerationEffect;
import heroes.journey.utils.worldgen.effects.BuildRoadBetweenFeaturesEffect;
import heroes.journey.utils.worldgen.effects.FeatureConnectionsEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenOffFeatureMapEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenRadialMapEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenRandomMapEffect;
import heroes.journey.utils.worldgen.effects.NoiseMapEffect;
import heroes.journey.utils.worldgen.effects.WaveFunctionCollapseMapEffect;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;

public class Map implements InitializerInterface {

    public static int MAP_SIZE = 64;
    // Kingdoms
    public static int NUM_KINGDOMS = 3;
    public static int KINGDOM_DIST_TO_CENTER = 3;

    // Towns
    public static int townsPerKingdomMin = 3;
    public static int townsPerKingdomMax = 6;
    public static int minDistanceBetweenTowns = 6;

    // Dungeons
    public static int dungeonsPerSettlementMin = 2;
    public static int dungeonsPerSettlementMax = 4;
    public static int minDistanceFromAnyFeature = 5;
    public static int maxDistanceFromSettlement = 10;
    // Wild Dungeons
    public static int minDistanceFromAllFeatures = 5;
    public static int maxAttemptsWildDungeons = 250;
    public static int wildDungeonsMin = 8;
    public static int wildDungeonsMax = 16;

    public static FeatureType KINGDOM, TOWN, DUNGEON;

    @Override
    public void init() {
        // feature types
        KINGDOM = new FeatureType("Kingdom") {
            @Override
            public Feature generateFeature(GameState gs, Position pos, Feature... connections) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.CAPITAL);
                UUID kingdomId = generateTown(gs, pos.getX(), pos.getY(), true);
                return new Feature(kingdomId, KINGDOM, pos);
            }
        };
        TOWN = new FeatureType("Town") {
            @Override
            public Feature generateFeature(GameState gs, Position pos, Feature... connections) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.TOWN);
                UUID townId = generateTown(gs, pos.getX(), pos.getY(), false);
                Feature town = new Feature(townId, TOWN, pos);
                for (Feature connection : connections)
                    town.add(connection);
                return town;
            }
        };
        DUNGEON = new FeatureType("Dungeon") {
            @Override
            public Feature generateFeature(GameState gs, Position pos, Feature... connections) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.DUNGEON);
                UUID dungeonId = generateDungeon(gs, pos.getX(), pos.getY());
                return new Feature(dungeonId, DUNGEON, pos);
            }
        };

        // Generate Smooth Noise
        NoiseMapEffect.builder()
            .name("base-noise")
            .amplitude(50)
            .roughness(.7f)
            .octaves(5)
            .smooths(2)
            .build()
            .register(MapGenerator.noisePhase);
        // Add Monsters
        BasicMapGenerationEffect.builder()
            .name("monsters")
            .applyEffect(gs -> MonsterFactory.init(gs.getWorld()))
            .build()
            .register(MapGenerator.noisePhase);
        // Capitals
        MapGenerationEffect kingdomsGen = FeatureGenRadialMapEffect.builder()
            .name("kingdoms")
            .distToCenter(KINGDOM_DIST_TO_CENTER)
            .numOfFeature(NUM_KINGDOMS)
            .featureType(KINGDOM)
            .build()
            .register(MapGenerator.worldGenPhase);
        FeatureConnectionsEffect.builder()
            .name("kingdomConnections")
            .featureType(KINGDOM)
            .featurePredicate((feature, featureToConnect) -> featureToConnect.getType() == KINGDOM)
            .build()
            .register(kingdomsGen);
        // Add Towns off of kingdoms
        MapGenerationEffect townsGen = FeatureGenOffFeatureMapEffect.builder()
            .name("towns")
            .minPerFeature(townsPerKingdomMin)
            .maxPerFeature(townsPerKingdomMax)
            .minDistanceFromFeature(minDistanceBetweenTowns)
            .maxDistanceFromFeature(minDistanceBetweenTowns * 2)
            .featureType(TOWN)
            .offFeature(KINGDOM)
            .build()
            .register(kingdomsGen);
        // Add Paths between capitals and towns
        MapGenerationEffect kingdomPaths = BuildRoadBetweenFeaturesEffect.builder()
            .name("kingdomPaths")
            .featureType(KINGDOM)
            .featureTypeToConnect(KINGDOM)
            .build()
            .register(townsGen);
        MapGenerationEffect paths = BuildRoadBetweenFeaturesEffect.builder()
            .name("paths")
            .featureType(KINGDOM)
            .featureTypeToConnect(TOWN)
            .build()
            .register(townsGen);
        // Add Dungeons off of towns
        MapGenerationEffect dungeonsGen = FeatureGenOffFeatureMapEffect.builder()
            .name("dungeons")
            .minPerFeature(dungeonsPerSettlementMin)
            .maxPerFeature(dungeonsPerSettlementMax)
            .minDistanceFromFeature(minDistanceFromAnyFeature)
            .maxDistanceFromFeature(maxDistanceFromSettlement)
            .featureType(DUNGEON)
            .offFeature(TOWN)
            .build()
            .register(paths);
        // Add more dungeons randomly in the wild
        FeatureGenRandomMapEffect.builder()
            .name("wildDungeons")
            .minFeature(wildDungeonsMin)
            .maxFeature(wildDungeonsMax)
            .generationAttempts(maxAttemptsWildDungeons)
            .minDistanceFromAllFeatures(minDistanceFromAllFeatures)
            .featureType(DUNGEON)
            .build()
            .register(dungeonsGen);
        // Wave Function collapse keeping houses and path placements
        MapGenerationEffect wfc = WaveFunctionCollapseMapEffect.builder()
            .name("waveFunctionCollapse")
            .applyTile((gs, pos) -> {
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
            })
            .build()
            .register(MapGenerator.postWorldGenPhase);
        // Create Trees
        WaveFunctionCollapseMapEffect.builder().name("trees").environment(true).applyTile((gs, pos) -> {
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
        }).build().register(wfc);
        // Add Entities
        BasicMapGenerationEffect.builder().name("entities").applyEffect(gameState -> {
            List<Feature> kingdoms = FeatureManager.get(KINGDOM);
            for (Feature kingdom : kingdoms) {
                if (kingdom == kingdoms.getFirst()) {
                    Feature playerTown = FeatureManager.get()
                        .get(kingdom.connections.stream().toList().getFirst());
                    EntityEdit player = gameState.getWorld().createEntity().edit();
                    UUID playerId = addOverworldComponents(gameState.getWorld(), player,
                        playerTown.location.getX(), playerTown.location.getY(), LoadTextures.PLAYER_SPRITE,
                        new MCTSAI());
                    player.create(PlayerComponent.class).playerId(PlayerInfo.get().getUuid());
                    player.create(NamedComponent.class).name("Player");
                    InventoryComponent.get(gameState.getWorld(), playerId)
                        .add(Items.healthPotion, 3)
                        .add(Items.ironIngot, 5)
                        .add(Items.chestPlate);
                    PlayerInfo.get().setPlayerId(playerId);
                } else {
                    Feature opponentTown = FeatureManager.get()
                        .get(kingdom.connections.stream().toList().getLast());
                    EntityEdit opponent = gameState.getWorld().createEntity().edit();
                    addOverworldComponents(gameState.getWorld(), opponent, opponentTown.location.getX(),
                        opponentTown.location.getY(), LoadTextures.PLAYER_SPRITE, new MCTSAI());
                }
            }
        }).build().register(MapGenerator.entityPhase);
    }

}

