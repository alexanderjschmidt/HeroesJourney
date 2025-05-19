package heroes.journey.utils.worldgen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import heroes.journey.GameState;
import heroes.journey.models.MapData;
import heroes.journey.tilemap.features.FeatureManager;
import lombok.Getter;

@Getter
public class NewMapManager {

    private static NewMapManager newMapManager;
    Map<String,MapGenerationEffect> mapGenerationEffects;

    private NewMapManager() {
        mapGenerationEffects = new HashMap<>();
    }

    public static NewMapManager get() {
        if (newMapManager == null)
            newMapManager = new NewMapManager();
        return newMapManager;
    }

    public void addMapGenerationEffect(String name, MapGenerationEffect effect) {
        mapGenerationEffects.put(name, effect);
    }

    public void initMapGeneration(GameState gameState, MapData mapData, boolean loading) {
        List<MapGenerationEffect> sorted = topologicalSort(mapGenerationEffects);
        while (true) {
            try {
                if (!loading) {
                    gameState.init(mapData);
                    FeatureManager.get().clear();
                }
                for (MapGenerationEffect phase : sorted) {
                    System.out.println("Running phase: " + phase.getName());
                    phase.apply(gameState);
                    gameState.getWorld().basicProcess();
                }
            } catch (MapGenerationException e) {
                continue;
            }
            break;
        }
        if (!loading)
            gameState.nextMove();
    }

    private List<MapGenerationEffect> topologicalSort(Map<String,MapGenerationEffect> phases) {
        Map<String,Integer> inDegree = new HashMap<>();
        Map<String,List<String>> graph = new HashMap<>();

        for (String name : phases.keySet()) {
            inDegree.put(name, 0);
            graph.put(name, new ArrayList<>());
        }

        for (MapGenerationEffect phase : phases.values()) {
            for (String dep : phase.dependsOn) {
                graph.get(dep).add(phase.getName());
                inDegree.put(phase.getName(), inDegree.get(phase.getName()) + 1);
            }
        }

        Queue<String> queue = new ArrayDeque<>();
        for (String name : inDegree.keySet()) {
            if (inDegree.get(name) == 0) {
                queue.add(name);
            }
        }

        List<MapGenerationEffect> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            String name = queue.poll();
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
