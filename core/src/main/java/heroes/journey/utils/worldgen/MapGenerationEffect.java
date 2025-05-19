package heroes.journey.utils.worldgen;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class MapGenerationEffect {

    @NonNull private final String name;
    @Builder.Default public final String[] dependsOn = new String[0];

    public MapGenerationEffect register() {
        NewMapManager.get().addMapGenerationEffect(name, this);
        return this;
    }

    public abstract void apply(GameState gameState);

    @Override
    public String toString() {
        return name;
    }

}
