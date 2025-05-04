package heroes.journey.tilemap.features;

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

    public static List<Feature> get(FeatureType type) {
        return get().values().stream().filter(f -> f.getType() == type).collect(Collectors.toList());
    }
}
