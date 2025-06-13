package heroes.journey.utils.worldgen;

import static heroes.journey.registries.Registries.FeatureTypeManager;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.registries.Registrable;
import heroes.journey.tilemap.Feature;
import lombok.Getter;

@Getter
public abstract class FeatureType extends Registrable {

    public FeatureType(String id, String name) {
        super(id, name);
        FeatureTypeManager.register(this);
    }

    public abstract Feature generateFeature(GameState gs, Position pos, Feature... connections);

}
