package heroes.journey.utils.worldgen;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.*;
import java.util.function.Consumer;

@Getter
@Builder
public class MapGenerationEffect {

    @NonNull
    private final String name;
    @Builder.Default
    private final int timeout = 0, retryCount = 3;
    @Builder.Default
    public final String[] dependsOn = new String[0];

    @NonNull
    private final Consumer<GameState> applyEffect;

    public MapGenerationEffect register() {
        NewMapManager.get().addMapGenerationEffect(name, this);
        return this;
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

}
