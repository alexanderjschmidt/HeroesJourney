package heroes.journey.initializers.base;

import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.baseTiles;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.possibleTiles;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.AIComponent;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.ActorComponent;
import heroes.journey.components.FactionComponent;
import heroes.journey.components.GameStateComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.LoyaltyComponent;
import heroes.journey.components.MovementComponent;
import heroes.journey.components.PlayerComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RenderComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.ActionQueue;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.entities.ai.MonsterFactionAI;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.systems.GameEngine;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.RoadPathing;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMaps;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerationPhase;
import heroes.journey.utils.worldgen.RandomWorldGenerator;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;
import heroes.journey.utils.worldgen.WeightedRandomPicker;

public class Map implements InitializerInterface {

    public static int MAP_SIZE = 32;

    private static final int numHouses = 15;
    private static final Position[] housePos = new Position[numHouses];
    private static int houseStart = 0;
    private static int houseEnd = 1;

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
        // Add Houses And Paths
        new MapGenerationEffect(MapGenerationPhase.INIT, 500) {
            @Override
            public void applyEffect(GameState gameState) {
                Tile[][] tileMap = gameState.getMap().getTileMap();
                Tile[][] environment = gameState.getMap().getEnvironment();
                houseStart = 0;
                houseEnd = 1;
                for (int i = 0; i < numHouses; i++) {
                    while (true) {
                        int x = (int)(Math.random() * tileMap.length);
                        int y = (int)(Math.random() * tileMap[0].length);
                        if (tileMap[x][y] == Tiles.PLAINS) {
                            housePos[i] = new Position(x, y);
                            environment[x][y] = Tiles.HOUSE;
                            break;
                        }
                    }
                }
                while (houseStart < numHouses) {
                    Cell path = new RoadPathing().getPath(gameState.getMap(), housePos[houseStart].getX(),
                        housePos[houseStart].getY(), housePos[houseEnd].getX(), housePos[houseEnd].getY());
                    while (path != null) {
                        GameState.global().getMap().setTile(path.i, path.j, Tiles.pathTiles.getFirst());
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
        // Add Entities
        new MapGenerationEffect(MapGenerationPhase.FINAL) {
            @Override
            public void applyEffect(GameState gameState) {
                Entity goblins = new Entity();
                goblins.add(new FactionComponent("Goblins").addOwnedLocation(new Position(16, 10)))
                    .add(new GameStateComponent())
                    .add(new AIComponent(new MonsterFactionAI()));
                GameEngine.get().addEntity(goblins);

                Entity player = new Entity();
                player.add(new PlayerComponent(ActionQueue.get().getID()))
                    .add(new PositionComponent(GameState.global().getWidth() / 2,
                        GameState.global().getHeight() / 2))
                    .add(new GameStateComponent())
                    .add(new RenderComponent(ResourceManager.get(TextureMaps.Sprites)[1][1]))
                    .add(new ActorComponent())
                    .add(new MovementComponent())
                    .add(new ActionComponent())
                    .add(new AIComponent(new MCTSAI()))
                    .add(new StatsComponent())
                    .add(new InventoryComponent())
                    .add(new LoyaltyComponent().putLoyalty(goblins, Loyalties.ENEMY));
                GameEngine.get().addEntity(player);
            }
        };
    }

}

