package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Consumer;

@Getter
public class BasicMapGenerationEffect extends MapGenerationEffect {
    @NonNull
    private final Consumer<GameState> applyEffect;

    public BasicMapGenerationEffect(String name, Consumer<GameState> applyEffect) {
        super(name);
        this.applyEffect = applyEffect;
    }

    public void apply(GameState gameState) {
        applyEffect.accept(gameState);
    }
}
