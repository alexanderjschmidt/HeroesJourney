package heroes.journey.utils.worldgen;

import static heroes.journey.registries.Registries.FeatureTypeManager;

import lombok.Getter;

@Getter
public class FeatureType {

    private final String name;

    public FeatureType(String name) {
        this.name = name;
        FeatureTypeManager.register(this);
    }

    public String toString() {
        return name;
    }

}
