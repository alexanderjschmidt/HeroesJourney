package heroes.journey.utils.worldgen;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import heroes.journey.GameState;

public class MapGenerationEffect {

    private final MapGenerationPhase phase;
    private final int timeout, retryCount;

    private final Consumer<GameState> applyEffect;

    public MapGenerationEffect(MapGenerationPhase phase, Consumer<GameState> applyEffect) {
        this(phase, 0, applyEffect);
    }

    public MapGenerationEffect(MapGenerationPhase phase, int timeout, Consumer<GameState> applyEffect) {
        this.phase = phase;
        this.applyEffect = applyEffect;
        this.timeout = timeout;
        this.retryCount = 5;
        NewMapManager.get().addMapGenerationEffect(this);
    }

    public void apply(GameState gameState) {
        if (timeout > 0) {
            Callable<Void> task = () -> {
                applyEffect.accept(gameState);
                return null;
            };
            timeout(task);
        } else {
            applyEffect.accept(gameState);
        }
    }

    private void timeout(Callable<Void> task) {
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

    public MapGenerationPhase getPhase() {
        return phase;
    }
}
