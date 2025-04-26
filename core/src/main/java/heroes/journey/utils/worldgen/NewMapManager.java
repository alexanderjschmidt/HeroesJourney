package heroes.journey.utils.worldgen;

import heroes.journey.GameState;

import java.util.*;
import java.util.concurrent.*;

public class NewMapManager {

    Map<String, MapGenerationEffect> mapGenerationEffects;

    private static NewMapManager newMapManager;

    public static NewMapManager get() {
        if (newMapManager == null)
            newMapManager = new NewMapManager();
        return newMapManager;
    }

    private NewMapManager() {
        mapGenerationEffects = new HashMap<>();
    }

    public void addMapGenerationEffect(String name, MapGenerationEffect effect) {
        mapGenerationEffects.put(name, effect);
    }

    public void initMapGeneration(GameState gameState) {
        Callable<Void> task = () -> {
            List<MapGenerationEffect> sorted = topologicalSort(mapGenerationEffects);
            for (MapGenerationEffect phase : sorted) {
                System.out.println("Running phase: " + phase.getName());
                phase.apply(gameState);
            }
            return null;
        };
        timeout(task, 5, 2000);
    }

    public static void timeout(Callable<Void> task, int retryCount, int timeout) {
        int i = 0;
        ExecutorService executorService = Executors.newSingleThreadExecutor(); // create once outside the loop
        try {
            while (i < retryCount) {
                i++;
                Future<Void> future = executorService.submit(task);
                try {
                    // Wait for the task to complete or timeout
                    future.get(timeout, TimeUnit.MILLISECONDS);
                    break;  // Break if the task completes successfully
                } catch (TimeoutException e) {
                    // Handle timeout case (task did not finish in time)
                    future.cancel(true);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    future.cancel(true); // ensure cancellation if needed
                }
            }
        } finally {
            executorService.shutdown();  // Shutdown executor once all retries are done
        }
    }

    private List<MapGenerationEffect> topologicalSort(Map<String, MapGenerationEffect> phases) {
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> graph = new HashMap<>();

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
