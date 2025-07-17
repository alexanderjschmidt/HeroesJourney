package heroes.journey.utils.worldgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heroes.journey.GameState;
import heroes.journey.models.MapData;
import heroes.journey.modlib.Registry;
import heroes.journey.utils.worldgen.effects.NoOpMapGenerationEffect;
import lombok.Getter;

@Getter
public class MapGenerator {

    public static MapGenerationEffect noisePhase, worldGenPhase, postWorldGenPhase, entityPhase;
    public static Registry<MapGenerationEffect> MapGenerationManager = new Registry<>();

    static {
        noisePhase = new NoOpMapGenerationEffect("NoisePhase").register();
        worldGenPhase = new NoOpMapGenerationEffect("WorldGenPhase").register(noisePhase);
        postWorldGenPhase = new NoOpMapGenerationEffect("PostWorldGenPhase").register(worldGenPhase);
        entityPhase = new NoOpMapGenerationEffect("EntityPhase").register(postWorldGenPhase);
    }

    public static void initMapGeneration(GameState gameState, MapData mapData, boolean loading) {
        List<MapGenerationEffect> sorted = topologicalSort(MapGenerationManager);
        while (true) {
            try {
                if (!loading) {
                    gameState.init(mapData);
                    for (MapGenerationEffect phase : sorted) {
                        if (phase instanceof NoOpMapGenerationEffect) {
                            System.out.println("Running phase: " + phase.getId());
                            continue;
                        }
                        System.out.println("Running effect: " + phase.getId());
                        phase.apply(gameState);
                        gameState.getWorld().basicProcess();
                    }
                }
            } catch (MapGenerationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        if (!loading)
            gameState.nextMove();
    }

    private static List<MapGenerationEffect> topologicalSort(Map<String,MapGenerationEffect> phases) {
        Map<String,Integer> inDegree = new HashMap<>();
        Map<String,List<String>> graph = new HashMap<>();

        for (String name : phases.keySet()) {
            inDegree.put(name, 0);
            graph.put(name, new ArrayList<>());
        }

        for (MapGenerationEffect phase : phases.values()) {
            for (String dep : phase.getDependsOn()) {
                graph.get(dep).add(phase.getId());
                inDegree.put(phase.getId(), inDegree.get(phase.getId()) + 1);
            }
        }

        List<String> queue = new ArrayList<>();
        for (String name : inDegree.keySet()) {
            if (inDegree.get(name) == 0) {
                queue.add(name);
            }
        }

        List<MapGenerationEffect> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            queue.sort((a, b) -> {
                boolean aIsNoOp = phases.get(a) instanceof NoOpMapGenerationEffect;
                boolean bIsNoOp = phases.get(b) instanceof NoOpMapGenerationEffect;
                return Boolean.compare(aIsNoOp, bIsNoOp); // false < true → NoOps last
            });

            String name = queue.removeFirst();
            MapGenerationEffect phase = phases.get(name);
            result.add(phase);

            for (String dependent : graph.get(name)) {
                inDegree.put(dependent, inDegree.get(dependent) - 1);
                if (inDegree.get(dependent) == 0) {
                    queue.add(dependent);
                }
            }
        }

        if (result.size() != phases.size()) {
            throw new IllegalStateException("Cyclic dependency detected in generation phases");
        }

        return result;
    }

}
