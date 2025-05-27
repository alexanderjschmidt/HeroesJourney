package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.factories.EntityFactory.addOverworldComponents;
import static heroes.journey.initializers.base.factories.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.factories.EntityFactory.generateTown;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.buildRoad;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.surroundedBySame;

import java.util.List;
import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.PlayerInfo;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.factories.MonsterFactory;
import heroes.journey.registries.FeatureManager;
import heroes.journey.registries.FeatureType;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.registries.NewMapManager;
import heroes.journey.utils.worldgen.effects.BasicMapGenerationEffect;
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

    @Override
    public void init() {
        // Generate Smooth Noise
        NoiseMapEffect.builder()
            .name("base-noise")
            .amplitude(50)
            .roughness(.7f)
            .octaves(5)
            .smooths(2)
            .build()
            .register(NewMapManager.noisePhase);
        // Add Monsters
        BasicMapGenerationEffect.builder()
            .name("monsters")
            .applyEffect(gs -> MonsterFactory.init(gs.getWorld()))
            .build()
            .register(NewMapManager.noisePhase);
        // Capitals
        MapGenerationEffect kingdomsGen = FeatureGenRadialMapEffect.builder()
            .name("kingdoms")
            .distToCenter(KINGDOM_DIST_TO_CENTER)
            .numOfFeature(NUM_KINGDOMS)
            .generateFeature((gs, pos) -> {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.CAPITAL);
                UUID kingdomId = generateTown(gs, pos.getX(), pos.getY(), true);
                new Feature(kingdomId, FeatureType.KINGDOM, pos);
                //System.out.println("Capital: " + pos.getX() + ", " + pos.getY());
            })
            .build()
            .register(NewMapManager.worldGenPhase);
        // Add Towns off of kingdoms
        MapGenerationEffect townsGen = FeatureGenOffFeatureMapEffect.builder()
            .name("towns")
            .minPerFeature(townsPerKingdomMin)
            .maxPerFeature(townsPerKingdomMax)
            .minDistanceFromFeature(minDistanceBetweenTowns)
            .maxDistanceFromFeature(minDistanceBetweenTowns * 2)
            .offFeature(FeatureType.KINGDOM)
            .generateFeature(input -> {
                input.getGameState()
                    .getMap()
                    .setEnvironment(input.getPosition().getX(), input.getPosition().getY(), Tiles.TOWN);
                UUID townId = generateTown(input.getGameState(), input.getPosition().getX(),
                    input.getPosition().getY(), false);
                Feature town = new Feature(townId, FeatureType.TOWN, input.getPosition());
                input.getOffFeature().add(town);
            })
            .build()
            .register(kingdomsGen);
        // Add Paths between capitals and towns
        MapGenerationEffect paths = BasicMapGenerationEffect.builder()
            .name("paths")
            .applyEffect(gameState -> {
                // Capitals to towns
                List<Feature> kingdoms = FeatureManager.get(FeatureType.KINGDOM);
                for (Feature kingdom : kingdoms) {
                    for (UUID townId : kingdom.connections) {
                        Feature town = FeatureManager.get().get(townId);
                        buildRoad(gameState, Tiles.pathTiles.getFirst(), kingdom.location, town.location);
                    }
                }
                // Capitals to Capitals
                for (int i = 0; i < kingdoms.size(); i++) {
                    for (int j = i + 1; j < kingdoms.size(); j++) {
                        buildRoad(gameState, Tiles.pathTiles.getFirst(), kingdoms.get(i).location,
                            kingdoms.get(j).location);
                    }
                }
            })
            .build()
            .register(townsGen);
        // Add Dungeons off of towns
        MapGenerationEffect dungeonsGen = FeatureGenOffFeatureMapEffect.builder()
            .name("dungeons")
            .minPerFeature(dungeonsPerSettlementMin)
            .maxPerFeature(dungeonsPerSettlementMax)
            .minDistanceFromFeature(minDistanceFromAnyFeature)
            .maxDistanceFromFeature(maxDistanceFromSettlement)
            .offFeature(FeatureType.TOWN)
            .generateFeature(input -> {
                input.getGameState()
                    .getMap()
                    .setEnvironment(input.getPosition().getX(), input.getPosition().getY(), Tiles.DUNGEON);
                UUID dungeonId = generateDungeon(input.getGameState(), input.getPosition().getX(),
                    input.getPosition().getY());
                new Feature(dungeonId, FeatureType.DUNGEON, input.getPosition());
            })
            .build()
            .register(paths);
        // Add more dungeons randomly in the wild
        FeatureGenRandomMapEffect.builder()
            .name("wildDungeons")
            .minFeature(wildDungeonsMin)
            .maxFeature(wildDungeonsMax)
            .generationAttempts(maxAttemptsWildDungeons)
            .minDistanceFromAllFeatures(minDistanceFromAllFeatures)
            .generateFeature((gameState, pos) -> {
                gameState.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.DUNGEON);
                UUID dungeonId = generateDungeon(gameState, pos.getX(), pos.getY());
                new Feature(dungeonId, FeatureType.DUNGEON, pos);
            })
            .build()
            .register(dungeonsGen);
        // Wave Function collapse keeping houses and path placements
        MapGenerationEffect wfc = WaveFunctionCollapseMapEffect.builder()
            .name("waveFunctionCollapse")
            .applyTile((gs, pos) -> {
                WeightedRandomPicker<Tile> possibleTiles = new WeightedRandomPicker<>();
                if (gs.getMap().getTileMap()[pos.getX()][pos.getY()] == Tiles.pathTiles.getFirst()) {
                    for (Tile t : Tiles.pathTiles) {
                        possibleTiles.addItem(t, t.getWeight());
                    }
                    possibleTiles.remove(Tiles.pathTiles.getFirst());
                } else if (gs.getMap().getEnvironment()[pos.getX()][pos.getY()] != null ||
                    surroundedBySame(gs.getMap().getTileMap(), pos.getX(), pos.getY())) {
                    possibleTiles.addItem(gs.getMap().getTileMap()[pos.getX()][pos.getY()], 1);
                } else {
                    return null;
                }
                return possibleTiles;
            })
            .build()
            .register(NewMapManager.postWorldGenPhase);
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
            List<Feature> kingdoms = FeatureManager.get(FeatureType.KINGDOM);
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
        }).build().register(NewMapManager.entityPhase);
    }

}

