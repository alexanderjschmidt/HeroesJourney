package heroes.journey.utils.worldgen;

import heroes.journey.GameState;

import java.util.concurrent.*;

public abstract class MapGenerationEffect {

    private final MapGenerationPhase phase;
    private final int timeout, retryCount;

    public MapGenerationEffect(MapGenerationPhase phase) {
        this(phase, 0);
    }

    public MapGenerationEffect(MapGenerationPhase phase, int timeout) {
        this.phase = phase;
        this.timeout = timeout;
        this.retryCount = 5;
        NewMapManager.get().addMapGenerationEffect(this);
    }

    public void apply(GameState gameState) {
        if (timeout > 0) {
            Callable<Void> task = () -> {
                applyEffect(gameState);
                return null;
            };
            timeout(task);
        } else {
            applyEffect(gameState);
        }
    }

    public abstract void applyEffect(GameState gameState);

    private void timeout(Callable<Void> task) {

        int i = 0;
        while (i < retryCount) {
            i++;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<Void> future = executorService.submit(task);
            try {
                // Wait for the task to complete or timeout
                future.get(timeout, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                // Handle timeout case (task did not finish in time)
                future.cancel(true);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                executorService.shutdown();
            }
        }
    }

    public MapGenerationPhase getPhase() {
        return phase;
    }
}
