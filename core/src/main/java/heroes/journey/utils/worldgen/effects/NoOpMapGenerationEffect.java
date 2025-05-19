package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NoOpMapGenerationEffect extends MapGenerationEffect {

    public void apply(GameState gameState) {
    }

}
