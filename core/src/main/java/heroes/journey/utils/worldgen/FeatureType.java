package heroes.journey.utils.worldgen;

import static heroes.journey.registries.Registries.FeatureTypeManager;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.tilemap.Feature;
import lombok.Getter;

@Getter
public abstract class FeatureType {

    private final String name;

    public FeatureType(String name) {
        this.name = name;
        FeatureTypeManager.register(this);
    }

    public abstract Feature generateFeature(GameState gs, Position pos, Feature... connections);

    public String toString() {
        return name;
    }

}
