package heroes.journey.utils.worldgen;

import static heroes.journey.registries.Registries.MapGenerationManager;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.registries.Registrable;
import lombok.Getter;

@Getter
public abstract class MapGenerationEffect extends Registrable {

    public final List<String> dependsOn;

    public MapGenerationEffect(String id) {
        super(id);
        this.dependsOn = new ArrayList<>();
    }

    public MapGenerationEffect register(MapGenerationEffect... dependencies) {
        MapGenerationManager.register(this);
        for (MapGenerationEffect dependency : dependencies) {
            dependsOn.add(dependency.getName());
        }
        return this;
    }

    public abstract void apply(GameState gameState);

}
