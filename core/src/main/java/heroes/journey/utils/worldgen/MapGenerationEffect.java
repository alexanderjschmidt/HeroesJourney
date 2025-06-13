package heroes.journey.utils.worldgen;

import heroes.journey.GameState;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static heroes.journey.registries.Registries.MapGenerationManager;

@Getter
public abstract class MapGenerationEffect {
    @NonNull
    private final String name;
    public final List<String> dependsOn;

    public MapGenerationEffect(String name) {
        this.name = name;
        this.dependsOn = new ArrayList<>();
    }

    public MapGenerationEffect register(MapGenerationEffect... dependencies) {
        MapGenerationManager.register(this);
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
