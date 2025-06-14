package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.Tiles.water;
import static heroes.journey.initializers.base.factories.EntityFactory.addOverworldComponents;
import static heroes.journey.initializers.base.factories.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.factories.EntityFactory.generateTown;
import static heroes.journey.registries.Registries.ItemManager;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.surroundedBySame;
import static heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator.buildRegionsFromMap;
import static heroes.journey.utils.worldgen.utils.WaveFunctionCollapse.possibleTiles;

import java.util.ArrayList;
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
import heroes.journey.registries.RegionManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerationException;
import heroes.journey.utils.worldgen.MapGenerator;
import heroes.journey.utils.worldgen.effects.BasicMapGenerationEffect;
import heroes.journey.utils.worldgen.effects.BuildRoadBetweenFeaturesEffect;
import heroes.journey.utils.worldgen.effects.FeatureConnectionsEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenOffFeatureMapEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenRadialMapEffect;
import heroes.journey.utils.worldgen.effects.FeatureGenRandomMapEffect;
import heroes.journey.utils.worldgen.effects.NoiseMapEffect;
import heroes.journey.utils.worldgen.effects.WaveFunctionCollapseMapEffect;
import heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;

public class Map implements InitializerInterface {

    public static int MAP_SIZE = 100;
    // Kingdoms
    public static int NUM_KINGDOMS = 3;
    public static int KINGDOM_DIST_TO_CENTER = 3;
    public static int REGIONS = NUM_KINGDOMS * 4;

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
        KINGDOM = new FeatureType("kingdom", "Kingdom") {
            @Override
            public Feature generateFeature(GameState gs, Position pos, Feature... connections) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.CAPITAL);
                UUID kingdomId = generateTown(gs, pos.getX(), pos.getY(), true);
                return new Feature(kingdomId, KINGDOM, pos);
            }
        };
        TOWN = new FeatureType("town", "Town") {
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
        DUNGEON = new FeatureType("dungeon", "Dungeon") {
            @Override
            public Feature generateFeature(GameState gs, Position pos, Feature... connections) {
                gs.getMap().setEnvironment(pos.getX(), pos.getY(), Tiles.DUNGEON);
                UUID dungeonId = generateDungeon(gs, pos.getX(), pos.getY());
                return new Feature(dungeonId, DUNGEON, pos);
            }
        };

        // Generate Smooth Noise
        new NoiseMapEffect("base_noise", 50, 0.7f, 5, 2).register(MapGenerator.noisePhase);

        // Add Monsters
        new BasicMapGenerationEffect("monsters", gs -> MonsterFactory.init(gs.getWorld())).register(
            MapGenerator.noisePhase);

        // Capitals
        MapGenerationEffect kingdomsGen = new FeatureGenRadialMapEffect("kingdoms", KINGDOM_DIST_TO_CENTER,
            NUM_KINGDOMS, KINGDOM).register(MapGenerator.worldGenPhase);

        new FeatureConnectionsEffect("kingdomConnections", KINGDOM,
            (feature, featureToConnect) -> featureToConnect.getType() == KINGDOM).register(kingdomsGen);

        new BasicMapGenerationEffect("voronoiRegions", gs -> {
            List<Position> kingdoms = new ArrayList<>();
            for (Feature kingdom : FeatureManager.get(KINGDOM)) {
                kingdoms.add(kingdom.location);
            }
            boolean[][] isLand = new boolean[gs.getWidth()][gs.getHeight()];

            for (int x = 0; x < gs.getWidth(); x++) {
                for (int y = 0; y < gs.getHeight(); y++) {
                    isLand[x][y] = inBounds(x, y) && gs.getMap().getTileMap()[x][y].getTerrain() != water;
                }
            }

            int[][] result = VoronoiRegionGenerator.generateRegionMap(isLand, kingdoms, REGIONS);
            gs.getMap().setRegionMap(result);
            buildRegionsFromMap(result);
            if (RegionManager.get().size() != REGIONS)
                throw new MapGenerationException("Could not produce enough regions");
        }).register(kingdomsGen);

        // Add Towns off of kingdoms
        MapGenerationEffect townsGen = new FeatureGenOffFeatureMapEffect("towns", townsPerKingdomMin,
            townsPerKingdomMax, minDistanceBetweenTowns, minDistanceBetweenTowns * 2, TOWN, KINGDOM).register(
            kingdomsGen);

        // Add Paths between capitals and towns
        MapGenerationEffect kingdomPaths = new BuildRoadBetweenFeaturesEffect("kingdomPaths", KINGDOM,
            KINGDOM).register(townsGen);
        MapGenerationEffect paths = new BuildRoadBetweenFeaturesEffect("paths", KINGDOM, TOWN).register(
            kingdomPaths);

        // Add Dungeons off of towns
        MapGenerationEffect dungeonsGen = new FeatureGenOffFeatureMapEffect("dungeons",
            dungeonsPerSettlementMin, dungeonsPerSettlementMax, minDistanceFromAnyFeature,
            maxDistanceFromSettlement, DUNGEON, TOWN).register(paths);

        // Add more dungeons randomly in the wild
        new FeatureGenRandomMapEffect("wildDungeons", wildDungeonsMin, wildDungeonsMax,
            maxAttemptsWildDungeons, minDistanceFromAllFeatures, DUNGEON).register(dungeonsGen);

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
                    Feature playerTown = FeatureManager.get()
                        .get(kingdom.connections.stream().toList().getFirst());
                    EntityEdit player = gameState.getWorld().createEntity().edit();
                    UUID playerId = addOverworldComponents(gameState.getWorld(), player,
                        playerTown.location.getX(), playerTown.location.getY(), LoadTextures.PLAYER_SPRITE,
                        new MCTSAI());
                    player.create(PlayerComponent.class).playerId(PlayerInfo.get().getUuid());
                    player.create(NamedComponent.class).name("Player");
                    InventoryComponent.get(gameState.getWorld(), playerId)
                        .add(ItemManager.get("health_potion"), 3)
                        .add(ItemManager.get("iron_ingot"), 5)
                        .add(ItemManager.get("chest_plate"));
                    PlayerInfo.get().setPlayerId(playerId);
                } else {
                    Feature opponentTown = FeatureManager.get()
                        .get(kingdom.connections.stream().toList().getLast());
                    EntityEdit opponent = gameState.getWorld().createEntity().edit();
                    addOverworldComponents(gameState.getWorld(), opponent, opponentTown.location.getX(),
                        opponentTown.location.getY(), LoadTextures.PLAYER_SPRITE, new MCTSAI());
                }
            }
        }).register(MapGenerator.entityPhase);
    }

}
