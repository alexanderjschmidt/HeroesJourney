package heroes.journey.initializers.base;

import static heroes.journey.initializers.base.factories.EntityFactory.addOverworldComponents;
import static heroes.journey.initializers.base.factories.EntityFactory.generateDungeon;
import static heroes.journey.initializers.base.factories.EntityFactory.generateTown;
import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.baseTiles;
import static heroes.journey.utils.worldgen.WaveFunctionCollapse.possibleTiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;
import heroes.journey.utils.Random;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.RoadPathing;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.RandomWorldGenerator;
import heroes.journey.utils.worldgen.WaveFunctionCollapse;
import heroes.journey.utils.worldgen.WeightedRandomPicker;

@SuppressWarnings("unchecked")
public class Map implements InitializerInterface {

    public static int MAP_SIZE = 64;
    public static int NUM_KINGDOMS = 3;
    public static MapGenerationEffect trees;

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
        MapGenerationEffect kingdomsGen = MapGenerationEffect.builder()
            .name("kingdoms")
            .dependsOn(new String[] {noise.getName()})
            .applyEffect(gameState -> {
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
                    int x = centerX + (int)(Math.cos(angleRad) * radius);
                    int y = centerY + (int)(Math.sin(angleRad) * radius);

                    // Snap to nearest valid land tile
                    Position capital = findValidLandTile(x, y, gameState.getMap().getTileMap());

                    gameState.getMap().setEnvironment(capital.getX(), capital.getY(), Tiles.CAPITAL);
                    UUID kingdomId = generateTown(gameState, capital.getX(), capital.getY(), true);
                    Feature kingdom = new Feature(kingdomId, FeatureType.KINGDOM, capital);
                    //System.out.println("Capital: " + capital.getX() + ", " + capital.getY());
                }
            })
            .build()
            .register();
        // Add Towns
        MapGenerationEffect townsGen = MapGenerationEffect.builder()
            .name("towns")
            .dependsOn(new String[] {kingdomsGen.getName()})
            .applyEffect(gameState -> {
                int minDistanceBetweenTowns = 6;
                int maxAttempts = 100;

                for (Feature kingdom : FeatureManager.get(FeatureType.KINGDOM)) {
                    int numTowns = Random.get().nextInt(3, 6);
                    Set<Position> placed = new HashSet<>();
                    placed.add(kingdom.location); // avoid placing towns right next to the capital

                    for (int i = 0; i < numTowns; i++) {
                        boolean placedTown = false;

                        for (int attempt = 0; attempt < maxAttempts; attempt++) {
                            Position candidate = getNearbyValidTile(gameState.getMap().getTileMap(),
                                kingdom.location, 4, 8);

                            // Check spacing with existing towns in this kingdom
                            boolean tooClose = false;
                            for (Position existing : placed) {
                                if (candidate.distanceTo(existing) < minDistanceBetweenTowns) {
                                    tooClose = true;
                                    break;
                                }
                            }

                            if (!tooClose &&
                                surroundedBySame(gameState.getMap().getTileMap(), candidate.getX(),
                                    candidate.getY())) {
                                gameState.getMap()
                                    .setEnvironment(candidate.getX(), candidate.getY(), Tiles.TOWN);
                                UUID townId = generateTown(gameState, candidate.getX(), candidate.getY(),
                                    false);
                                Feature town = new Feature(townId, FeatureType.TOWN, candidate);
                                kingdom.add(town);
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
            })
            .build()
            .register();
        // Add Paths
        MapGenerationEffect paths = MapGenerationEffect.builder()
            .name("paths")
            .dependsOn(new String[] {townsGen.getName()})
            .applyEffect(gameState -> {
                // Capitals to towns
                List<Feature> kingdoms = FeatureManager.get(FeatureType.KINGDOM);
                for (Feature kingdom : kingdoms) {
                    for (Feature town : kingdom.connections) {
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
        MapGenerationEffect monsters = MapGenerationEffect.builder()
            .name("monsters")
            .dependsOn(new String[] {paths.getName()})
            .applyEffect(gameState -> {
                MonsterFactory.goblin(gameState.getWorld());
                MonsterFactory.hobGoblin(gameState.getWorld());
            })
            .build()
            .register();
        // Add Dungeons
        MapGenerationEffect dungeonsGen = MapGenerationEffect.builder()
            .name("dungeons")
            .dependsOn(new String[] {monsters.getName()})
            .applyEffect(gameState -> {
                int minDistanceFromAnyFeature = 5;
                int minDistanceFromSettlement = 3;
                int maxDistanceFromSettlement = 10;
                int dungeonsPerSettlementMin = 2;
                int dungeonsPerSettlementMax = 4;
                int maxAttempts = 50;

                List<Feature> settlements = new ArrayList<>();
                for (Feature kingdom : FeatureManager.get(FeatureType.KINGDOM)) {
                    settlements.add(kingdom);
                    settlements.addAll(kingdom.connections);
                }

                for (Feature settlement : settlements) {
                    int numDungeons = Random.get()
                        .nextInt(dungeonsPerSettlementMin, dungeonsPerSettlementMax);

                    for (int i = 0; i < numDungeons; i++) {
                        boolean placed = false;

                        for (int attempt = 0; attempt < maxAttempts; attempt++) {
                            Position candidate = getNearbyValidTile(gameState.getMap().getTileMap(),
                                settlement.location, minDistanceFromSettlement, maxDistanceFromSettlement);

                            if (isPositionFarFromFeatures(gameState, candidate, minDistanceFromAnyFeature) &&
                                surroundedBySame(gameState.getMap().getTileMap(), candidate.getX(),
                                    candidate.getY())) {
                                gameState.getMap()
                                    .setEnvironment(candidate.getX(), candidate.getY(), Tiles.DUNGEON);
                                UUID dungeonId = generateDungeon(gameState, candidate.getX(),
                                    candidate.getY());
                                Feature dungeon = new Feature(dungeonId, FeatureType.DUNGEON, candidate);
                                placed = true;
                                break;
                            }
                        }

                        if (!placed) {
                            System.out.println("Warning: Could not place a dungeon near " + settlement);
                        }
                    }
                }
            })
            .build()
            .register();
        MapGenerationEffect wildDungeons = MapGenerationEffect.builder()
            .name("wildDungeons")
            .dependsOn(new String[] {dungeonsGen.getName()})
            .applyEffect(gameState -> {
                int numWildernessDungeons = Random.get().nextInt(8, 16); // Adjust how many you want
                int minDistanceFromAllFeatures = 5;
                int maxAttempts = 250;

                for (int i = 0; i < numWildernessDungeons; i++) {
                    boolean placed = false;

                    for (int attempt = 0; attempt < maxAttempts; attempt++) {
                        int x = Random.get().nextInt(0, MAP_SIZE - 1);
                        int y = Random.get().nextInt(0, MAP_SIZE - 1);
                        Position candidate = new Position(x, y);

                        if (inBounds(x, y) && isLandTile(gameState.getMap().getTileMap()[x][y]) &&
                            isPositionFarFromFeatures(gameState, candidate, minDistanceFromAllFeatures) &&
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
        MapGenerationEffect wfc = MapGenerationEffect.builder()
            .name("waveFunctionCollapse")
            .dependsOn(new String[] {wildDungeons.getName()})
            .applyEffect(gameState -> {
                int width = gameState.getWidth();

                WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < width; y++) {
                        if (gameState.getMap().getEnvironment()[x][y] != null ||
                            gameState.getMap().getTileMap()[x][y] == Tiles.pathTiles.getFirst()) {
                            possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                            possibleTilesMap[x][y].addItem(gameState.getMap().getTileMap()[x][y], 1);
                        } else if (surroundedBySame(gameState.getMap().getTileMap(), x, y)) {
                            possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                            possibleTilesMap[x][y].addItem(gameState.getMap().getTileMap()[x][y], 1);
                        } else {
                            possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                            for (Tile t : possibleTiles) {
                                //long weight = computeTileWeight(t, gameState.getMap().getTileMap(), x, y);
                                possibleTilesMap[x][y].addItem(t, t.getWeight());
                            }
                        }
                    }
                }

                Tile[][] tileMap = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap);
                gameState.getMap().setTileMap(tileMap);
            })
            .build()
            .register();
        // Wave Function collapse paths to smooth tiles
        MapGenerationEffect wfcPaths = MapGenerationEffect.builder()
            .name("waveFunctionCollapsePaths")
            .dependsOn(new String[] {wfc.getName()})
            .applyEffect(gameState -> {
                int width = gameState.getWidth();

                WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < width; y++) {
                        if (gameState.getMap().getTileMap()[x][y] != Tiles.pathTiles.getFirst()) {
                            possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                            possibleTilesMap[x][y].addItem(gameState.getMap().getTileMap()[x][y], 1);
                        } else {
                            possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                            for (Tile t : Tiles.pathTiles) {
                                possibleTilesMap[x][y].addItem(t, t.getWeight());
                            }
                            possibleTilesMap[x][y].remove(Tiles.pathTiles.getFirst());
                        }
                    }
                }

                Tile[][] tileMap = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap);
                gameState.getMap().setTileMap(tileMap);
            })
            .build()
            .register();
        // Create Trees
        trees = MapGenerationEffect.builder()
            .name("trees")
            .dependsOn(new String[] {wfcPaths.getName()})
            .applyEffect(gameState -> {
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
            })
            .build()
            .register();
        // Add Entities
        MapGenerationEffect entities = MapGenerationEffect.builder()
            .name("entities")
            .dependsOn(new String[] {trees.getName()})
            .applyEffect(gameState -> {
                List<Feature> kingdoms = FeatureManager.get(FeatureType.KINGDOM);
                Feature playerTown = kingdoms.getFirst().connections.stream().toList().getFirst();
                EntityEdit player = gameState.getWorld().createEntity().edit();
                UUID playerId = addOverworldComponents(gameState.getWorld(), player,
                    playerTown.location.getX(), playerTown.location.getY(),
                    ResourceManager.get(LoadTextures.Sprites)[1][1], new MCTSAI());
                player.create(PlayerComponent.class).playerId(PlayerInfo.get().getUuid());
                player.create(NamedComponent.class).name("Player");
                InventoryComponent.get(gameState.getWorld(), playerId)
                    .add(Items.healthPotion, 3)
                    .add(Items.ironIngot, 5)
                    .add(Items.chestPlate);
                PlayerInfo.get().setPlayerId(playerId);

                Feature opponentTown = kingdoms.getLast().connections.stream().toList().getLast();
                EntityEdit opponent = gameState.getWorld().createEntity().edit();
                addOverworldComponents(gameState.getWorld(), opponent, opponentTown.location.getX(),
                    opponentTown.location.getY(), ResourceManager.get(LoadTextures.Sprites)[1][1],
                    new MCTSAI());
            })
            .build()
            .register();
    }

    private static boolean surroundedBySame(Tile[][] tileMap, int x, int y) {
        Tile t = tileMap[x][y];
        return (!inBounds(x - 1, y + 1) || t == tileMap[x - 1][y + 1]) &&
            (!inBounds(x - 1, y) || t == tileMap[x - 1][y]) &&
            (!inBounds(x - 1, y - 1) || t == tileMap[x - 1][y - 1]) &&
            (!inBounds(x, y - 1) || t == tileMap[x][y - 1]) &&
            (!inBounds(x, y + 1) || t == tileMap[x][y + 1]) &&
            (!inBounds(x + 1, y - 1) || t == tileMap[x + 1][y - 1]) &&
            (!inBounds(x + 1, y) || t == tileMap[x + 1][y]) &&
            (!inBounds(x + 1, y + 1) || t == tileMap[x + 1][y + 1]);
    }

    private static void buildRoad(
        GameState gameState,
        Tile connector,
        Position location1,
        Position location2) {
        Cell path = new RoadPathing().getPath(gameState.getMap(), location1.getX(), location1.getY(),
            location2.getX(), location2.getY());
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
                    if (Math.abs(dx) != r && Math.abs(dy) != r)
                        continue; // only outer edge
                    int x = startX + dx;
                    int y = startY + dy;
                    if (inBounds(x, y) && isLandTile(map[x][y]) && surroundedBySame(map, x, y)) {
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

    public static boolean inBounds(int x, int y) {
        return inBounds(x, y, MAP_SIZE, MAP_SIZE);
    }

    public static boolean inBounds(int x, int y, int width, int height) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public static boolean inBounds(int x, int y, Object[][] array) {
        return x >= 0 && y >= 0 && x < array.length && y < array[x].length;
    }

    private static Position getNearbyValidTile(Tile[][] map, Position center, int minDist, int maxDist) {
        for (int i = 0; i < 100; i++) {
            int dx = Random.get().nextInt(-maxDist, maxDist);
            int dy = Random.get().nextInt(-maxDist, maxDist);
            if (Math.abs(dx) + Math.abs(dy) < minDist)
                continue;

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

                if (!inBounds(x, y))
                    continue;

                if (gameState.getMap().getEnvironment(x, y) != null) {
                    if (pos.distanceTo(new Position(x, y)) < minDistance) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static long computeTileWeight(Tile candidate, Tile[][] noiseMap, int x, int y) {
        // Step 1: Similarity to noise-defined terrain
        double matchScore = candidate.similarityTo(noiseMap[x][y]) *
            100; // You define this: 1.0 = identical, 0.0 = very different

        // Step 2: Neighbor alignment bias
        int aligned = 1;
        for (Direction dir : Direction.values()) {
            if (dir == Direction.NODIRECTION)
                continue;
            int nx = (int)(x + dir.getDirVector().x);
            int ny = (int)(y + dir.getDirVector().y);
            if (inBounds(nx, ny, noiseMap) && noiseMap[nx][ny] != null) {
                aligned += candidate.alignment(dir, noiseMap[nx][ny]);
            }
        }
        double alignmentScore = aligned / 33f * 100;

        double finalScore = Math.pow(matchScore, 4) * Math.pow(alignmentScore, 2);

        long result = (long)(candidate.getWeight() * finalScore);

        if (result == 0 || alignmentScore == 0 || matchScore == 0 || finalScore == 0 ||
            candidate.getWeight() == 0) {
            throw new RuntimeException(
                candidate + " (" + candidate.getWeight() + "): " + "Scores: " + alignmentScore + " " +
                    matchScore + " " + finalScore + "=" + Math.pow(matchScore, 3) + "*" +
                    Math.pow(alignmentScore, 3) + " " + result);
        }

        // Final weight combines base tile weight with noise and alignment preferences
        return result;
    }

}

