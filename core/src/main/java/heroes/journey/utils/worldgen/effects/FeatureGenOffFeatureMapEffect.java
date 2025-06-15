package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.*;

@Getter
public class FeatureGenOffFeatureMapEffect extends MapGenerationEffect {
    private final int minPerFeature;
    private final int maxPerFeature;
    private final int minDistanceFromFeature;
    private final int maxDistanceFromFeature;
    @NonNull
    private final FeatureType offFeature;
    @NonNull
    private final FeatureType featureType;

    public FeatureGenOffFeatureMapEffect(
        String id,
        int minPerFeature,
        int maxPerFeature,
        int minDistanceFromFeature,
        int maxDistanceFromFeature,
        FeatureType offFeature,
        FeatureType featureType) {
        super(id);
        this.minPerFeature = minPerFeature;
        this.maxPerFeature = maxPerFeature;
        this.minDistanceFromFeature = minDistanceFromFeature;
        this.maxDistanceFromFeature = maxDistanceFromFeature;
        this.offFeature = offFeature;
        this.featureType = featureType;
    }

    public void apply(GameState gameState) {
        System.out.println(FeatureManager.get());
        System.out.println(FeatureManager.get(offFeature));
        System.out.println(offFeature);
        for (Feature settlement : FeatureManager.get(offFeature)) {
            int numFeatures = Random.get().nextInt(minPerFeature, maxPerFeature);

            for (int i = 0; i < numFeatures; i++) {
                Position candidate = findTileNear(settlement.location, minDistanceFromFeature,
                    maxDistanceFromFeature, isFarFromFeatures(gameState, minDistanceFromFeature).and(
                        isLandSurrounded(gameState.getMap().getTileMap())));
                featureType.generateFeature(gameState, candidate, settlement);
            }
        }
    }
}
