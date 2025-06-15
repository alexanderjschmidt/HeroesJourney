package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.findTileNear;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isFarFromFeatures;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandSurrounded;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class FeatureGenOffFeatureMapEffect extends MapGenerationEffect {
    private final int minPerFeature;
    private final int maxPerFeature;
    private final int minDistanceFromFeature;
    private final int maxDistanceFromFeature;
    @NonNull private final FeatureType offFeature;
    @NonNull private final FeatureType featureType;

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
        for (Feature settlement : FeatureManager.get(offFeature)) {
            int numDungeons = Random.get().nextInt(minPerFeature, maxPerFeature);

            for (int i = 0; i < numDungeons; i++) {
                Position candidate = findTileNear(settlement.location, minDistanceFromFeature,
                    maxDistanceFromFeature, isFarFromFeatures(gameState, minDistanceFromFeature).and(
                        isLandSurrounded(gameState.getMap().getTileMap())));
                System.out.println("Gen feature off another feature");
                featureType.generateFeature(gameState, candidate, settlement);
            }
        }
    }
}
