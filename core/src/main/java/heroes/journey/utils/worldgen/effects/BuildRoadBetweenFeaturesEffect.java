package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.buildRoad;

import java.util.List;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.initializers.base.Tiles;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class BuildRoadBetweenFeaturesEffect extends MapGenerationEffect {
    @NonNull private final FeatureType featureType;
    @NonNull private final FeatureType featureTypeToConnect;

    public BuildRoadBetweenFeaturesEffect(
        String id,
        FeatureType featureType,
        FeatureType featureTypeToConnect) {
        super(id);
        this.featureType = featureType;
        this.featureTypeToConnect = featureTypeToConnect;
    }

    public void apply(GameState gameState) {
        List<Feature> kingdoms = FeatureManager.get(featureType);
        for (Feature featureBase : kingdoms) {
            for (UUID connectionId : featureBase.connections) {
                Feature featureToConnect = FeatureManager.get().get(connectionId);
                if (featureToConnect.getType().equals(featureTypeToConnect))
                    buildRoad(gameState, Tiles.pathDot, featureBase.location, featureToConnect.location);
            }
        }
    }
}
