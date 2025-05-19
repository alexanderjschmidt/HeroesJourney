package heroes.journey.utils.worldgen;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class MapGenerationEffect {

    @NonNull private final String name;
    @Builder.Default public final List<String> dependsOn = new ArrayList<>();

    public MapGenerationEffect register(MapGenerationEffect... dependencies) {
        NewMapManager.get().addMapGenerationEffect(name, this);
        for (MapGenerationEffect dependency : dependencies) {
            dependsOn.add(dependency.name);
        }
        return this;
    }

    public abstract void apply(GameState gameState);

    @Override
    public String toString() {
        return name;
    }

}
