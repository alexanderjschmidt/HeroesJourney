package heroes.journey.utils.worldgen.effects;

import java.util.UUID;
import java.util.function.BiPredicate;

import heroes.journey.GameState;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class FeatureConnectionsEffect extends MapGenerationEffect {
    @NonNull private final FeatureType featureType;
    @NonNull private final BiPredicate<UUID,UUID> featurePredicate;

    public FeatureConnectionsEffect(
        String id,
        FeatureType featureType,
        BiPredicate<UUID,UUID> featurePredicate) {
        super(id);
        this.featureType = featureType;
        this.featurePredicate = featurePredicate;
    }

    public void apply(GameState gameState) {
        /*List<Feature> features = FeatureManager.get(featureType);
        for (Feature feature : features) {
            for (Feature featureToConnect : FeatureManager.get().values()) {
                if (featurePredicate.test(feature, featureToConnect))
                    feature.add(featureToConnect);
            }
        }*/
    }
}
