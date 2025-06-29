package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.tilemap.FeatureType;
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
       /* List<Feature> kingdoms = FeatureManager.get(featureType);
        for (Feature featureBase : kingdoms) {
            for (UUID connectionId : featureBase.connections) {
                Feature featureToConnect = FeatureManager.get().get(connectionId);
                if (featureToConnect.getType().equals(featureTypeToConnect))
                    buildRoad(gameState, Tiles.pathDot, featureBase.location, featureToConnect.location);
            }
        }*/
    }
}
