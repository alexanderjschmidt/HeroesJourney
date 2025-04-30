package heroes.journey.utils.worldgen;

import static heroes.journey.utils.worldgen.NewMapManager.timeout;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class MapGenerationEffect {

    @NonNull private final String name;
    @Builder.Default private final int timeout = 0, retryCount = 3;
    @Builder.Default public final String[] dependsOn = new String[0];

    @NonNull private final Consumer<GameState> applyEffect;

    public MapGenerationEffect register() {
        NewMapManager.get().addMapGenerationEffect(name, this);
        return this;
    }

    public void apply(GameState gameState) {
        if (timeout > 0) {
            timeout(() -> (Callable<Void>)() -> {
                applyEffect.accept(gameState);
                return null;
            }, retryCount, timeout);
        } else {
            applyEffect.accept(gameState);
        }
    }

}
