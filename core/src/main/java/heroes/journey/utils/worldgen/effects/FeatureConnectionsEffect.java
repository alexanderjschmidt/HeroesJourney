package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.function.BiPredicate;

@Getter
public class FeatureConnectionsEffect extends MapGenerationEffect {
    @NonNull
    private final FeatureType featureType;
    @NonNull
    private final BiPredicate<Feature, Feature> featurePredicate;

    public FeatureConnectionsEffect(
        String id,
        FeatureType featureType,
        BiPredicate<Feature, Feature> featurePredicate) {
        super(id);
        this.featureType = featureType;
        this.featurePredicate = featurePredicate;
    }

    public void apply(GameState gameState) {
        List<Feature> features = FeatureManager.get(featureType);
        for (Feature feature : features) {
            for (Feature featureToConnect : FeatureManager.get().values()) {
                if (featurePredicate.test(feature, featureToConnect))
                    feature.add(featureToConnect);
            }
        }
    }
}
