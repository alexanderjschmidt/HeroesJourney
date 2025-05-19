package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.factories.EntityFactory.addOverworldComponents;
import static heroes.journey.initializers.base.factories.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.factories.EntityFactory.generateTown;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.buildRoad;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.findTileNear;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isFarFromFeatures;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandSurrounded;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandTile;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.surroundedBySame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.PlayerInfo;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.factories.MonsterFactory;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.effects.BasicMapGenerationEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenRadialMapEffect;
import heroes.journey.utils.worldgen.effects.NoiseMapEffect;
import heroes.journey.utils.worldgen.effects.WaveFunctionCollapseMapEffect;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;

@SuppressWarnings("unchecked")
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

    static {
        // Generate Smooth Noise
        MapGenerationEffect noise = NoiseMapEffect.builder()
            .name("noise")
            .amplitude(50)
            .roughness(.7f)
            .octaves(5)
            .smooths(2)
            .build()
            .register();
        // Capitals
        MapGenerationEffect kingdomsGen = FeatureGenRadialMapEffect.builder()
            .name("kingdoms")
            .dependsOn(new String[] {noise.getName()})
            .distToCenter(KINGDOM_DIST_TO_CENTER)
            .numOfFeature(NUM_KINGDOMS)
            .generateFeatured((gs, pos) -> {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.CAPITAL);
                UUID kingdomId = generateTown(gs, pos.getX(), pos.getY(), true);
                Feature kingdom = new Feature(kingdomId, FeatureType.KINGDOM, pos);
                //System.out.println("Capital: " + pos.getX() + ", " + pos.getY());
            })
            .build()
            .register();
        // Add Towns off of kingdoms
        MapGenerationEffect townsGen = BasicMapGenerationEffect.builder()
            .name("towns")
            .dependsOn(new String[] {kingdomsGen.getName()})
            .applyEffect(gameState -> {
                for (Feature kingdom : FeatureManager.get(FeatureType.KINGDOM)) {
                    int numTowns = Random.get().nextInt(townsPerKingdomMin, townsPerKingdomMax);

                    for (int i = 0; i < numTowns; i++) {
                        Position candidate = findTileNear(kingdom.location, minDistanceBetweenTowns,
                            minDistanceBetweenTowns * 2,
                            isFarFromFeatures(gameState, minDistanceBetweenTowns).and(
                                isLandSurrounded(gameState.getMap().getTileMap())));

                        gameState.getMap().setEnvironment(candidate.getX(), candidate.getY(), Tiles.TOWN);
                        UUID townId = generateTown(gameState, candidate.getX(), candidate.getY(), false);
                        Feature town = new Feature(townId, FeatureType.TOWN, candidate);
                        kingdom.add(town);
                    }
                }
            })
            .build()
            .register();
        // Add Paths between capitals and towns
        MapGenerationEffect paths = BasicMapGenerationEffect.builder()
            .name("paths")
            .dependsOn(new String[] {townsGen.getName()})
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
            .register();
        // Add Monsters
        // TODO this really shouldn't be an effect
        MapGenerationEffect monsters = BasicMapGenerationEffect.builder()
            .name("monsters")
            .dependsOn(new String[] {paths.getName()})
            .applyEffect(gameState -> {
                MonsterFactory.goblin(gameState.getWorld());
                MonsterFactory.hobGoblin(gameState.getWorld());
            })
            .build()
            .register();
        // Add Dungeons off of towns
        MapGenerationEffect dungeonsGen = BasicMapGenerationEffect.builder()
            .name("dungeons")
            .dependsOn(new String[] {monsters.getName()})
            .applyEffect(gameState -> {
                List<Feature> settlements = new ArrayList<>();
                for (Feature kingdom : FeatureManager.get(FeatureType.KINGDOM)) {
                    settlements.add(kingdom);
                    for (UUID connectionId : kingdom.connections) {
                        settlements.add(FeatureManager.get().get(connectionId));
                    }
                }

                for (Feature settlement : settlements) {
                    int numDungeons = Random.get()
                        .nextInt(dungeonsPerSettlementMin, dungeonsPerSettlementMax);

                    for (int i = 0; i < numDungeons; i++) {
                        Position candidate = findTileNear(settlement.location, minDistanceFromAnyFeature,
                            maxDistanceFromSettlement,
                            isFarFromFeatures(gameState, minDistanceFromAnyFeature).and(
                                isLandSurrounded(gameState.getMap().getTileMap())));

                        gameState.getMap().setEnvironment(candidate.getX(), candidate.getY(), Tiles.DUNGEON);
                        UUID dungeonId = generateDungeon(gameState, candidate.getX(), candidate.getY());
                        Feature dungeon = new Feature(dungeonId, FeatureType.DUNGEON, candidate);

                    }
                }
            })
            .build()
            .register();
        // Add more dungeons randomly in the wild
        MapGenerationEffect wildDungeons = BasicMapGenerationEffect.builder()
            .name("wildDungeons")
            .dependsOn(new String[] {dungeonsGen.getName()})
            .applyEffect(gameState -> {
                int numWildernessDungeons = Random.get()
                    .nextInt(wildDungeonsMin, wildDungeonsMax); // Adjust how many you want

                for (int i = 0; i < numWildernessDungeons; i++) {
                    boolean placed = false;

                    for (int attempt = 0; attempt < maxAttemptsWildDungeons; attempt++) {
                        int x = Random.get().nextInt(0, MAP_SIZE - 1);
                        int y = Random.get().nextInt(0, MAP_SIZE - 1);
                        Position candidate = new Position(x, y);

                        if (inBounds(x, y) && isLandTile(gameState.getMap().getTileMap()[x][y]) &&
                            isFarFromFeatures(gameState, minDistanceFromAllFeatures).test(candidate) &&
                            surroundedBySame(gameState.getMap().getTileMap(), x, y)) {
                            gameState.getMap().setEnvironment(x, y, Tiles.DUNGEON);
                            UUID dungeonId = generateDungeon(gameState, x, y);
                            Feature dungeon = new Feature(dungeonId, FeatureType.DUNGEON, candidate);
                            placed = true;
                            System.out.println("Placed a wilderness dungeon!");
                            break;
                        }
                    }

                    if (!placed) {
                        System.out.println("Warning: Could not place a wilderness dungeon!");
                    }
                }
            })
            .build()
            .register();
        // Wave Function collapse keeping houses and path placements
        MapGenerationEffect wfc = WaveFunctionCollapseMapEffect.builder()
            .name("waveFunctionCollapse")
            .dependsOn(new String[] {wildDungeons.getName()})
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
            .register();
        // Create Trees
        MapGenerationEffect trees = WaveFunctionCollapseMapEffect.builder()
            .name("trees")
            .dependsOn(new String[] {wildDungeons.getName()})
            .environment(true)
            .applyTile((gs, pos) -> {
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
            })
            .build()
            .register();
        // Add Entities
        MapGenerationEffect entities = BasicMapGenerationEffect.builder()
            .name("entities")
            .dependsOn(new String[] {trees.getName()})
            .applyEffect(gameState -> {
                List<Feature> kingdoms = FeatureManager.get(FeatureType.KINGDOM);
                for (Feature kingdom : kingdoms) {
                    if (kingdom == kingdoms.getFirst()) {
                        Feature playerTown = FeatureManager.get()
                            .get(kingdom.connections.stream().toList().getFirst());
                        EntityEdit player = gameState.getWorld().createEntity().edit();
                        UUID playerId = addOverworldComponents(gameState.getWorld(), player,
                            playerTown.location.getX(), playerTown.location.getY(),
                            LoadTextures.PLAYER_SPRITE, new MCTSAI());
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
            })
            .build()
            .register();
    }

}

