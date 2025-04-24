package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.factories.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.factories.EntityFactory.generateHouse;
import static heroes.journey.initializers.base.factories.EntityFactory.overworldEntity;
import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.baseTiles;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.possibleTiles;

import java.util.ArrayList;
import java.util.List;

import com.artemis.EntityEdit;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.RoadPathing;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerationPhase;
import heroes.journey.utils.worldgen.RandomWorldGenerator;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;
import heroes.journey.utils.worldgen.WeightedRandomPicker;

public class Map implements InitializerInterface {

    public static int MAP_SIZE = 32;

    private static List<Position> housePos;

    public void init() {
        // Generate Smooth Noise
        new MapGenerationEffect(MapGenerationPhase.INIT, gameState -> {
            int width = gameState.getWidth();

            RandomWorldGenerator noiseGen = new RandomWorldGenerator(50, 5, .7f, true);
            int[][] noiseMap = noiseGen.generateMap(width);
            Tile[][] tileMap = convertToTileMap(noiseMap);
            smooth(tileMap, baseTiles);

            gameState.getMap().setTileMap(tileMap);
        });
        // Add Dungeons Houses And Paths
        new MapGenerationEffect(MapGenerationPhase.INIT, 500, gameState -> {
            Tile[][] tileMap = gameState.getMap().getTileMap();
            Tile[][] environment = gameState.getMap().getEnvironment();

            // Gen Houses
            int numHouses = 10;
            housePos = generateRandomFeatures(gameState, Tiles.HOUSE, numHouses, false);

            // Gen Paths between houses
            connectFeatures(gameState, housePos, Tiles.pathTiles.getFirst());

            // Gen Dungeons
            int numDungeons = 8;
            generateRandomFeatures(gameState, Tiles.DUNGEON, numDungeons, true);
        });
        // Wave Function collapse keeping houses and path placements
        new MapGenerationEffect(MapGenerationPhase.INIT, gameState -> {
            int width = gameState.getWidth();

            WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (gameState.getMap().getEnvironment()[x][y] != null ||
                        gameState.getMap().getTileMap()[x][y] == Tiles.pathTiles.getFirst()) {
                        possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                        possibleTilesMap[x][y].addItem(gameState.getMap().getTileMap()[x][y],
                            Integer.MAX_VALUE);
                    } else if (baseTiles.contains(gameState.getMap().getTileMap()[x][y])) {
                        possibleTilesMap[x][y] = new WeightedRandomPicker<>(possibleTiles);
                        possibleTilesMap[x][y].addItem(gameState.getMap().getTileMap()[x][y],
                            possibleTilesMap[x][y].getTotalWeight() * 10);
                    }
                }
            }

            Tile[][] tileMap = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap);
            gameState.getMap().setTileMap(tileMap);
        });
        // Wave Function collapse paths to smooth tiles
        new MapGenerationEffect(MapGenerationPhase.SECOND, gameState -> {
            int width = gameState.getWidth();

            WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (gameState.getMap().getTileMap()[x][y] != Tiles.pathTiles.getFirst()) {
                        possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                        possibleTilesMap[x][y].addItem(gameState.getMap().getTileMap()[x][y],
                            Integer.MAX_VALUE);
                    } else {
                        possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                        for (Tile t : Tiles.pathTiles) {
                            possibleTilesMap[x][y].addItem(t, t.getWeight());
                        }
                    }
                }
            }

            Tile[][] tileMap = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap);
            gameState.getMap().setTileMap(tileMap);
        });
        // Create Trees
        new MapGenerationEffect(MapGenerationPhase.SECOND, gameState -> {
            int width = gameState.getWidth();

            WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                    if (gameState.getMap().getTileMap()[x][y] == Tiles.PLAINS &&
                        gameState.getMap().getEnvironment()[x][y] == null) {
                        for (Tile t : Tiles.treeTiles) {
                            possibleTilesMap[x][y].addItem(t, t.getWeight());
                        }
                    }
                    long totalWeight = possibleTilesMap[x][y].getTotalWeight();
                    possibleTilesMap[x][y].addItem(Tiles.NULL, totalWeight > 0 ? totalWeight : 100);
                }
            }
            Tile[][] environment = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (environment[x][y] == Tiles.NULL)
                        environment[x][y] = null;
                    if (gameState.getMap().getEnvironment()[x][y] != null)
                        environment[x][y] = gameState.getMap().getEnvironment()[x][y];
                }
            }

            gameState.getMap().setEnvironment(environment);
        });
        // Add Entities
        new MapGenerationEffect(MapGenerationPhase.FINAL, gameState -> {
            Integer playerId = overworldEntity(gameState, housePos.getFirst().getX(),
                housePos.getFirst().getY(), ResourceManager.get(LoadTextures.Sprites)[1][1], new MCTSAI());
            EntityEdit player = gameState.getWorld().edit(playerId);
            player.create(PlayerComponent.class).playerId(gameState.getId());
            player.create(NamedComponent.class).name("Player");
            InventoryComponent.get(gameState.getWorld(), playerId)
                .add(Items.healthPotion, 3)
                .add(Items.ironIngot, 5)
                .add(Items.chestPlate);
            GameState.global().getPlayableEntities().add(playerId);

            overworldEntity(gameState, housePos.getLast().getX(), housePos.getLast().getY(),
                ResourceManager.get(LoadTextures.Sprites)[1][1], new MCTSAI());
        });
    }

    private static List<Position> generateRandomFeatures(
        GameState gameState,
        Tile feature,
        int count,
        boolean dungeon) {
        List<Position> featurePos = new ArrayList<>(count);
        Tile[][] tileMap = gameState.getMap().getTileMap();
        Tile[][] environment = gameState.getMap().getEnvironment();
        for (int i = 0; i < count; i++) {
            while (true) {
                int x = (int)(Math.random() * environment.length);
                int y = (int)(Math.random() * environment[0].length);
                if (tileMap[x][y] == Tiles.PLAINS) {
                    if (dungeon)
                        generateDungeon(gameState, x, y);
                    else
                        generateHouse(gameState, x, y);
                    featurePos.add(new Position(x, y));
                    environment[x][y] = feature;
                    break;
                }
            }
        }
        return featurePos;
    }

    private void connectFeatures(GameState gameState, List<Position> featurePos, Tile connector) {
        int featureStart = 0;
        int featureEnd = 1;
        while (featureStart < featurePos.size()) {
            Cell path = new RoadPathing().getPath(gameState.getMap(), featurePos.get(featureStart).getX(),
                featurePos.get(featureStart).getY(), featurePos.get(featureEnd).getX(),
                featurePos.get(featureEnd).getY());
            while (path != null) {
                gameState.getMap().setTile(path.x, path.y, connector);
                path = path.parent;
            }
            featureEnd++;
            if (featureStart == featureEnd) {
                featureEnd++;
            }
            if (featureEnd >= featurePos.size()) {
                featureEnd = 0;
                featureStart++;
            }
        }
    }

}

