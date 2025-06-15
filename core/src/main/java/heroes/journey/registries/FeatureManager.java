package heroes.journey.registries;

import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FeatureType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FeatureManager extends HashMap<UUID, Feature> {

    private static FeatureManager featureManager;

    public static FeatureManager get() {
        if (featureManager == null)
            featureManager = new FeatureManager();
        return featureManager;
    }

    public static Feature getFeature(UUID id) {
        return get().get(id);
    }

    public static List<Feature> get(FeatureType type) {
        return get().values().stream().filter(f -> f.getType() == type).collect(Collectors.toList());
    }
}
