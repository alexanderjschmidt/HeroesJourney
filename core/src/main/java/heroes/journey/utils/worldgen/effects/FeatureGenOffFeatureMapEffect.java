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
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FeatureGenOffFeatureMapEffect extends MapGenerationEffect {

    private final int minPerFeature, maxPerFeature, minDistanceFromFeature, maxDistanceFromFeature;
    @NonNull private final FeatureType offFeature, featureType;

    public void apply(GameState gameState) {
        for (Feature settlement : FeatureManager.get(offFeature)) {
            int numDungeons = Random.get().nextInt(minPerFeature, maxPerFeature);

            for (int i = 0; i < numDungeons; i++) {
                Position candidate = findTileNear(settlement.location, minDistanceFromFeature,
                    maxDistanceFromFeature, isFarFromFeatures(gameState, minDistanceFromFeature).and(
                        isLandSurrounded(gameState.getMap().getTileMap())));

                featureType.generateFeature(gameState, candidate, settlement);
            }
        }
    }

}
