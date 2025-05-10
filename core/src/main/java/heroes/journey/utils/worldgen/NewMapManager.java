package heroes.journey.utils.worldgen;

import heroes.journey.GameState;
import heroes.journey.models.MapData;
import heroes.journey.tilemap.features.FeatureManager;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class NewMapManager {

    private static NewMapManager newMapManager;
    Map<String, MapGenerationEffect> mapGenerationEffects;

    private NewMapManager() {
        mapGenerationEffects = new HashMap<>();
    }

    public static NewMapManager get() {
        if (newMapManager == null)
            newMapManager = new NewMapManager();
        return newMapManager;
    }

    public static void timeout(Supplier<Callable<Void>> taskSupplier, int retryCount, int timeout) {
        int i = 0; // create once outside the loop
        while (i < retryCount) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            i++;
            Future<Void> future = executorService.submit(taskSupplier.get());
            try {
                // Wait for the task to complete or timeout
                future.get(timeout, TimeUnit.MILLISECONDS);
                break;  // Break if the task completes successfully
            } catch (TimeoutException e) {
                // Handle timeout case (task did not finish in time)
                System.out.println("Timed out");
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                future.cancel(true); // ensure cancellation if needed
                executorService.shutdownNow();
            }
        }
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
                    if (!loading || phase.isRunOnLoad()) {
                        System.out.println("Running phase: " + phase.getName() + " " + phase.isRunOnLoad());
                        phase.apply(gameState);
                    }
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

    public void initMapGenerationTimeout(GameState gameState, MapData mapData) {
        timeout(() -> (Callable<Void>) () -> {
            gameState.init(mapData);
            FeatureManager.get().clear();
            List<MapGenerationEffect> sorted = topologicalSort(mapGenerationEffects);
            for (MapGenerationEffect phase : sorted) {
                System.out.println("Running phase: " + phase.getName());
                phase.apply(gameState);
                gameState.getWorld().basicProcess();
            }
            return null;
        }, 10, 1000);
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
