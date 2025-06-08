package heroes.journey.utils.worldgen.effects;

import java.util.List;
import java.util.function.BiPredicate;

import heroes.journey.GameState;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FeatureConnectionsEffect extends MapGenerationEffect {

    @NonNull private final FeatureType featureType;

    /**
     * first Feature is feature your looking at, second is the one to connect to
     */
    @NonNull private final BiPredicate<Feature,Feature> featurePredicate;

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
