package heroes.journey.initializers.base;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.*;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.RoadPathing;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMaps;
import heroes.journey.utils.worldgen.*;
import heroes.journey.utils.worldgen.namegen.SyllableDungeonNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableTownNameGenerator;

import java.util.ArrayList;
import java.util.List;

import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.baseTiles;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.possibleTiles;

public class Map implements InitializerInterface {

    public static int MAP_SIZE = 32;

    private static List<Position> housePos;

    public void init() {
        // Generate Smooth Noise
        new MapGenerationEffect(MapGenerationPhase.INIT) {
            @Override
            public void applyEffect(GameState gameState) {
                int width = gameState.getWidth();

                RandomWorldGenerator noiseGen = new RandomWorldGenerator(50, 5, .7f, true);
                int[][] noiseMap = noiseGen.generateMap(width);
                Tile[][] tileMap = convertToTileMap(noiseMap);
                smooth(tileMap, baseTiles);

                gameState.getMap().setTileMap(tileMap);
            }
        };
        // Add Dungeons Houses And Paths
        new MapGenerationEffect(MapGenerationPhase.INIT, 500) {
            @Override
            public void applyEffect(GameState gameState) {
                Tile[][] tileMap = gameState.getMap().getTileMap();
                Tile[][] environment = gameState.getMap().getEnvironment();
                housePos = new ArrayList<>();

                // Gen Houses
                int numHouses = 10;
                for (int i = 0; i < numHouses; i++) {
                    while (true) {
                        int x = (int) (Math.random() * tileMap.length);
                        int y = (int) (Math.random() * tileMap[0].length);
                        if (tileMap[x][y] == Tiles.PLAINS) {
                            generateHouse(x, y);
                            housePos.add(new Position(x, y));
                            environment[x][y] = Tiles.HOUSE;
                            break;
                        }
                    }
                }

                // Gen Paths between houses
                int houseStart = 0;
                int houseEnd = 1;
                while (houseStart < numHouses) {
                    Cell path = new RoadPathing().getPath(gameState.getMap(), housePos.get(houseStart).getX(),
                        housePos.get(houseStart).getY(), housePos.get(houseEnd).getX(),
                        housePos.get(houseEnd).getY());
                    while (path != null) {
                        GameState.global().getMap().setTile(path.x, path.y, Tiles.pathTiles.getFirst());
                        path = path.parent;
                    }
                    houseEnd++;
                    if (houseStart == houseEnd) {
                        houseEnd++;
                    }
                    if (houseEnd >= numHouses) {
                        houseEnd = 0;
                        houseStart++;
                    }
                }

                // Gen Dungeons
                int numDungeons = 8;
                for (int i = 0; i < numDungeons; i++) {
                    while (true) {
                        int x = (int) (Math.random() * tileMap.length);
                        int y = (int) (Math.random() * tileMap[0].length);
                        if (tileMap[x][y] == Tiles.PLAINS) {
                            environment[x][y] = Tiles.DUNGEON;
                            generateDungeon(x, y);
                            break;
                        }
                    }
                }
            }
        };
        // Wave Function collapse keeping houses and path placements
        new MapGenerationEffect(MapGenerationPhase.INIT) {
            @Override
            public void applyEffect(GameState gameState) {
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
            }
        };
        // Wave Function collapse paths to smooth tiles
        new MapGenerationEffect(MapGenerationPhase.SECOND) {
            @Override
            public void applyEffect(GameState gameState) {
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
            }
        };
        // Create Trees
        new MapGenerationEffect(MapGenerationPhase.SECOND) {
            @Override
            public void applyEffect(GameState gameState) {
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
            }
        };
        // Add Entities
        new MapGenerationEffect(MapGenerationPhase.FINAL) {
            @Override
            public void applyEffect(GameState gameState) {
                Entity player = new Entity();
                player.add(new PlayerComponent(GameState.global().getId()))
                    .add(new PositionComponent(housePos.getFirst().getX(), housePos.getFirst().getY()))
                    .add(new GameStateComponent())
                    .add(new RenderComponent(ResourceManager.get(TextureMaps.Sprites)[1][1]))
                    .add(new ActorComponent())
                    .add(new PossibleActionsComponent())
                    .add(new AIComponent(new MCTSAI()))
                    .add(new StatsComponent())
                    .add(new InventoryComponent())
                    .add(new EquipmentComponent())
                    .add(new QuestsComponent())
                    .add(new LoyaltyComponent());
                GameState.global().getEngine().addEntity(player);

                Entity opponent = new Entity();
                opponent.add(new PositionComponent(housePos.getLast().getX(), housePos.getLast().getY()))
                    .add(new GameStateComponent())
                    .add(new RenderComponent(ResourceManager.get(TextureMaps.Sprites)[1][1]))
                    .add(new ActorComponent())
                    .add(new PossibleActionsComponent())
                    .add(new AIComponent(new MCTSAI()))
                    .add(new StatsComponent())
                    .add(new InventoryComponent())
                    .add(new EquipmentComponent())
                    .add(new QuestsComponent())
                    .add(new LoyaltyComponent());
                GameState.global().getEngine().addEntity(opponent);
            }
        };
    }

    private void generateHouse(int x, int y) {
        Quest quest = new Quest("Delve a dungeon") {
            @Override
            public void onComplete(GameState gameState, Entity completer) {
                InventoryComponent inventoryComponent = InventoryComponent.get(completer);
                if (inventoryComponent != null) {
                    inventoryComponent.add(Items.ironSword);
                }
            }

            @Override
            public boolean isComplete(GameState gameState, Entity owner) {
                return !gameState.getHistory().isEmpty() &&
                    gameState.getHistory().getLast() instanceof ActionRecord record &&
                    record.getAction() == BaseActions.delve &&
                    gameState.get(record.getEntity()) == owner;
            }
        };

        Entity house = new Entity();
        house.add(new FactionComponent(SyllableTownNameGenerator.generateName()).addOwnedLocation(
                GameState.global(), house, new Position(x, y)))
            .add(new GameStateComponent())
            .add(new QuestsComponent().addQuest(quest))
            .add(new PossibleActionsComponent().addAction(BaseActions.inn));
        GameState.global().getEngine().addEntity(house);
    }

    private void generateDungeon(int x, int y) {
        Entity dungeon = new Entity();
        dungeon.add(new FactionComponent(SyllableDungeonNameGenerator.generateName()).addOwnedLocation(
                GameState.global(), dungeon, new Position(x, y)))
            .add(new GameStateComponent())
            .add(new PossibleActionsComponent().addAction(BaseActions.delve, dungeon))
            .add(new CooldownComponent());
        GameState.global().getEngine().addEntity(dungeon);
    }

}

