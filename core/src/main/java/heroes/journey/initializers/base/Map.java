package heroes.journey.initializers.base;

import com.artemis.EntityEdit;
import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.wavefunction.Tile;
import heroes.journey.utils.Random;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.RoadPathing;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.RandomWorldGenerator;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;
import heroes.journey.utils.worldgen.WeightedRandomPicker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static heroes.journey.initializers.base.factories.EntityFactory.*;
import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.baseTiles;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.possibleTiles;

@SuppressWarnings("unchecked")
public class Map implements InitializerInterface {

    public static int MAP_SIZE = 64;
    public static int NUM_KINGDOMS = 3;
    public static MapGenerationEffect trees;

    private static List<Feature> kingdoms = new ArrayList<>();
    public static List<Feature> features = new ArrayList<>();

    static {
        // Generate Smooth Noise
        MapGenerationEffect noise = MapGenerationEffect.builder().name("noise").applyEffect(gameState -> {
            int width = gameState.getWidth();

            RandomWorldGenerator noiseGen = new RandomWorldGenerator(50, 5, .7f, true);
            int[][] noiseMap = noiseGen.generateMap(width);
            Tile[][] tileMap = convertToTileMap(noiseMap);
            smooth(tileMap, baseTiles);
            smooth(tileMap, baseTiles);

            gameState.getMap().setTileMap(tileMap);
        }).build().register();
        // Capitals
        MapGenerationEffect kingdomsGen = MapGenerationEffect.builder().name("kingdoms").dependsOn(new String[]{noise.getName()}).applyEffect(gameState -> {
            kingdoms = new ArrayList<>();
            int centerX = MAP_SIZE / 2;
            int centerY = MAP_SIZE / 2;
            int radius = MAP_SIZE / 3; // How far from center the capital should be

            // Random offset in degrees (0 to 360)
            double randomOffsetDeg = Random.get().nextDouble() * 360.0;

            for (int i = 0; i < NUM_KINGDOMS; i++) {
                // Divide full circle (360°) into thirds
                double angleDeg = i * (360.0 / NUM_KINGDOMS) + randomOffsetDeg;
                double angleRad = Math.toRadians(angleDeg);

                // Polar to Cartesian
                int x = centerX + (int) (Math.cos(angleRad) * radius);
                int y = centerY + (int) (Math.sin(angleRad) * radius);

                // Snap to nearest valid land tile
                Position capital = findValidLandTile(x, y, gameState.getMap().getTileMap());
                Feature kingdom = new Feature("kingdom", capital);
                kingdoms.add(kingdom);

                gameState.getMap().setEnvironment(capital.getX(), capital.getY(), Tiles.CAPITAL);
                generateTown(gameState, capital.getX(), capital.getY(), true);
                features.add(kingdom);
                //System.out.println("Capital: " + capital.getX() + ", " + capital.getY());
            }
        }).build().register();
        // Add Towns
        MapGenerationEffect townsGen = MapGenerationEffect.builder().name("towns").dependsOn(new String[]{kingdomsGen.getName()}).applyEffect(gameState -> {
            int minDistanceBetweenTowns = 6;
            int maxAttempts = 100;

            for (Feature kingdom : kingdoms) {
                int numTowns = Random.get().nextInt(3, 6);
                Set<Position> placed = new HashSet<>();
                placed.add(kingdom.location); // avoid placing towns right next to the capital

                for (int i = 0; i < numTowns; i++) {
                    boolean placedTown = false;

                    for (int attempt = 0; attempt < maxAttempts; attempt++) {
                        Position candidate = getNearbyValidTile(gameState.getMap().getTileMap(), kingdom.location, 4, 8);

                        // Check spacing with existing towns in this kingdom
                        boolean tooClose = false;
                        for (Position existing : placed) {
                            if (candidate.distanceTo(existing) < minDistanceBetweenTowns) {
                                tooClose = true;
                                break;
                            }
                        }

                        if (!tooClose) {
                            Feature town = new Feature("town", candidate);
                            kingdom.add(town);
                            features.add(town);
                            gameState.getMap().setEnvironment(candidate.getX(), candidate.getY(), Tiles.TOWN);
                            generateTown(gameState, candidate.getX(), candidate.getY(), false);
                            placed.add(candidate);
                            placedTown = true;
                            break;
                        }
                    }

                    if (!placedTown) {
                        System.out.println("Warning: Could not place all towns for kingdom");
                    }
                }
            }
        }).build().register();
        // Add Paths
        MapGenerationEffect paths = MapGenerationEffect.builder().name("paths").dependsOn(new String[]{townsGen.getName()}).applyEffect(gameState -> {
            // Capitals to towns
            for (Feature kingdom : kingdoms) {
                for (Feature town : kingdom.connections) {
                    buildRoad(gameState, Tiles.pathTiles.getFirst(), kingdom.location, town.location);
                }
            }
            // Capitals to Capitals
            for (int i = 0; i < kingdoms.size(); i++) {
                for (int j = i + 1; j < kingdoms.size(); j++) {
                    buildRoad(gameState, Tiles.pathTiles.getFirst(), kingdoms.get(i).location, kingdoms.get(j).location);
                }
            }
        }).build().register();
        // Add Dungeons
        MapGenerationEffect dungeonsGen = MapGenerationEffect.builder().name("dungeons").dependsOn(new String[]{paths.getName()}).applyEffect(gameState -> {
            int minDistanceFromAnyFeature = 5;
            int minDistanceFromSettlement = 3;
            int maxDistanceFromSettlement = 10;
            int dungeonsPerSettlementMin = 2;
            int dungeonsPerSettlementMax = 4;
            int maxAttempts = 50;

            List<Feature> settlements = new ArrayList<>();
            for (Feature kingdom : kingdoms) {
                settlements.add(kingdom);
                settlements.addAll(kingdom.connections);
            }

            for (Feature settlement : settlements) {
                int numDungeons = Random.get().nextInt(dungeonsPerSettlementMin, dungeonsPerSettlementMax);

                for (int i = 0; i < numDungeons; i++) {
                    boolean placed = false;

                    for (int attempt = 0; attempt < maxAttempts; attempt++) {
                        Position candidate = getNearbyValidTile(gameState.getMap().getTileMap(), settlement.location, minDistanceFromSettlement, maxDistanceFromSettlement);

                        if (isPositionFarFromFeatures(gameState, candidate, minDistanceFromAnyFeature)) {
                            gameState.getMap().setEnvironment(candidate.getX(), candidate.getY(), Tiles.DUNGEON);
                            generateDungeon(gameState, candidate.getX(), candidate.getY());
                            Feature dungeon = new Feature("dungeon", candidate);
                            features.add(dungeon);
                            placed = true;
                            break;
                        }
                    }

                    if (!placed) {
                        System.out.println("Warning: Could not place a dungeon near " + settlement);
                    }
                }
            }
        }).build().register();
        MapGenerationEffect wildDungeons = MapGenerationEffect.builder().name("wildDungeons").dependsOn(new String[]{dungeonsGen.getName()}).applyEffect(gameState -> {
            int numWildernessDungeons = Random.get().nextInt(8, 16); // Adjust how many you want
            int minDistanceFromAllFeatures = 5;
            int maxAttempts = 250;

            for (int i = 0; i < numWildernessDungeons; i++) {
                boolean placed = false;

                for (int attempt = 0; attempt < maxAttempts; attempt++) {
                    int x = Random.get().nextInt(0, MAP_SIZE - 1);
                    int y = Random.get().nextInt(0, MAP_SIZE - 1);
                    Position candidate = new Position(x, y);

                    if (inBounds(x, y) && isLandTile(gameState.getMap().getTileMap()[x][y]) && isPositionFarFromFeatures(gameState, candidate, minDistanceFromAllFeatures)) {
                        gameState.getMap().setEnvironment(x, y, Tiles.DUNGEON);
                        generateDungeon(gameState, x, y);
                        Feature dungeon = new Feature("wilddungeon", candidate);
                        features.add(dungeon);
                        placed = true;
                        System.out.println("Placed a wilderness dungeon!");
                        break;
                    }
                }

                if (!placed) {
                    System.out.println("Warning: Could not place a wilderness dungeon!");
                }
            }
        }).build().register();
        // Wave Function collapse keeping houses and path placements
        MapGenerationEffect wfc = MapGenerationEffect.builder().name("waveFunctionCollapse").dependsOn(new String[]{wildDungeons.getName()}).applyEffect(gameState -> {
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

            Tile[][] tileMap = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap, false);
            gameState.getMap().setTileMap(tileMap);
        }).build().register();
        // Wave Function collapse paths to smooth tiles
        MapGenerationEffect wfcPaths = MapGenerationEffect.builder().name("waveFunctionCollapsePaths").dependsOn(new String[]{wfc.getName()}).applyEffect(gameState -> {
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

            Tile[][] tileMap = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap, true);
            gameState.getMap().setTileMap(tileMap);
        }).build().register();
        // Create Trees
        trees = MapGenerationEffect.builder().name("trees").dependsOn(new String[]{wfcPaths.getName()}).applyEffect(gameState -> {
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
            Tile[][] environment = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap, true);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (environment[x][y] == Tiles.NULL)
                        environment[x][y] = null;
                    if (gameState.getMap().getEnvironment()[x][y] != null)
                        environment[x][y] = gameState.getMap().getEnvironment()[x][y];
                }
            }

            gameState.getMap().setEnvironment(environment);
        }).build().register();
        // Add Entities
        MapGenerationEffect entities = MapGenerationEffect.builder().name("entities").dependsOn(new String[]{trees.getName()}).applyEffect(gameState -> {
            Feature playerTown = kingdoms.getFirst().connections.stream().toList().getFirst();
            Integer playerId = overworldEntity(gameState, playerTown.location.getX(),
                playerTown.location.getY(), ResourceManager.get(LoadTextures.Sprites)[1][1], new MCTSAI());
            EntityEdit player = gameState.getWorld().edit(playerId);
            player.create(PlayerComponent.class).playerId(gameState.getId());
            player.create(NamedComponent.class).name("Player");
            InventoryComponent.get(gameState.getWorld(), playerId)
                .add(Items.healthPotion, 3)
                .add(Items.ironIngot, 5)
                .add(Items.chestPlate);
            GameState.global().getPlayableEntities().add(playerId);

            Feature opponentTown = kingdoms.getLast().connections.stream().toList().getLast();
            overworldEntity(gameState, opponentTown.location.getX(), opponentTown.location.getY(),
                ResourceManager.get(LoadTextures.Sprites)[1][1], new MCTSAI());
        }).build().register();
    }

    private static void buildRoad(GameState gameState, Tile connector, Position location1, Position location2) {
        Cell path = new RoadPathing().getPath(gameState.getMap(), location1.getX(),
            location1.getY(), location2.getX(),
            location2.getY());
        while (path != null) {
            gameState.getMap().setTile(path.x, path.y, connector);
            path = path.parent;
        }
    }

    private static Position findValidLandTile(int startX, int startY, Tile[][] map) {
        int maxRadius = 10;

        for (int r = 0; r <= maxRadius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dy = -r; dy <= r; dy++) {
                    if (Math.abs(dx) != r && Math.abs(dy) != r) continue; // only outer edge
                    int x = startX + dx;
                    int y = startY + dy;
                    if (inBounds(x, y) && isLandTile(map[x][y])) {
                        return new Position(x, y);
                    }
                }
            }
        }

        return new Position(startX, startY); // fallback, might not be land
    }

    private static boolean isLandTile(Tile tile) {
        return tile == Tiles.PLAINS || tile == Tiles.HILLS;
    }

    private static boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < MAP_SIZE && y < MAP_SIZE;
    }

    private static Position getNearbyValidTile(Tile[][] map, Position center, int minDist, int maxDist) {
        for (int i = 0; i < 100; i++) {
            int dx = Random.get().nextInt(-maxDist, maxDist);
            int dy = Random.get().nextInt(-maxDist, maxDist);
            if (Math.abs(dx) + Math.abs(dy) < minDist) continue;

            int x = center.getX() + dx;
            int y = center.getY() + dy;
            if (inBounds(x, y) && isLandTile(map[x][y])) {
                return new Position(x, y);
            }
        }
        return center; // fallback
    }

    private static boolean isPositionFarFromFeatures(GameState gameState, Position pos, int minDistance) {
        for (int dx = -minDistance; dx <= minDistance; dx++) {
            for (int dy = -minDistance; dy <= minDistance; dy++) {
                int x = pos.getX() + dx;
                int y = pos.getY() + dy;

                if (!inBounds(x, y)) continue;

                if (gameState.getMap().getEnvironment(x, y) != null) {
                    if (pos.distanceTo(new Position(x, y)) < minDistance) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}

