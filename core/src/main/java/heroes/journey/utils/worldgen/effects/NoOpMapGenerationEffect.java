package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;

@Getter
public class NoOpMapGenerationEffect extends MapGenerationEffect {
    public NoOpMapGenerationEffect(String name) {
        super(name);
    }

    public void apply(GameState gameState) {
    }
}
