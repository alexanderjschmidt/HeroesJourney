package heroes.journey.utils.worldgen.effects;

import java.util.function.Consumer;

import heroes.journey.GameState;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BasicMapGenerationEffect extends MapGenerationEffect {

    @NonNull private final Consumer<GameState> applyEffect;

    public void apply(GameState gameState) {
        applyEffect.accept(gameState);
    }

}
