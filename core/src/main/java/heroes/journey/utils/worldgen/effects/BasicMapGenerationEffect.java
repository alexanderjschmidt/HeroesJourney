package heroes.journey.utils.worldgen.effects;

import java.util.function.Consumer;

import heroes.journey.GameState;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class BasicMapGenerationEffect extends MapGenerationEffect {
    @NonNull private final Consumer<GameState> applyEffect;

    public BasicMapGenerationEffect(String id, Consumer<GameState> applyEffect) {
        super(id);
        this.applyEffect = applyEffect;
    }

    public void apply(GameState gameState) {
        applyEffect.accept(gameState);
    }
}
