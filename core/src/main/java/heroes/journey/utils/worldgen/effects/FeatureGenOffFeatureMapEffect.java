package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.findTileNear;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isFarFromFeatures;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandSurrounded;

import java.util.function.Consumer;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.registries.FeatureManager;
import heroes.journey.registries.FeatureType;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FeatureGenOffFeatureMapEffect extends MapGenerationEffect {

    private final int minPerFeature, maxPerFeature, minDistanceFromFeature, maxDistanceFromFeature;
    @NonNull private final FeatureType offFeature;

    @NonNull private final Consumer<GenerateFeatureOffInput> generateFeature;

    public void apply(GameState gameState) {
        for (Feature settlement : FeatureManager.get(offFeature)) {
            int numDungeons = Random.get().nextInt(minPerFeature, maxPerFeature);

            for (int i = 0; i < numDungeons; i++) {
                Position candidate = findTileNear(settlement.location, minDistanceFromFeature,
                    maxDistanceFromFeature, isFarFromFeatures(gameState, minDistanceFromFeature).and(
                        isLandSurrounded(gameState.getMap().getTileMap())));

                generateFeature.accept(new GenerateFeatureOffInput(gameState, candidate, settlement));
            }
        }
    }

    @Getter
    public static class GenerateFeatureOffInput {
        GameState gameState;
        Position position;
        Feature offFeature;

        public GenerateFeatureOffInput(GameState gameState, Position position, Feature offFeature) {
            this.gameState = gameState;
            this.position = position;
            this.offFeature = offFeature;
        }
    }

}
