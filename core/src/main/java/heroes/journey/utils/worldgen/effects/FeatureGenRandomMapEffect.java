package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isFarFromFeatures;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandTile;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.surroundedBySame;

import heroes.journey.GameState;
import heroes.journey.modlib.Position;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.NonNull;

public class FeatureGenRandomMapEffect extends MapGenerationEffect {
    private final int minFeature;
    private final int maxFeature;
    private final int generationAttempts;
    private final int minDistanceFromAllFeatures;
    @NonNull private final FeatureType featureType;

    public FeatureGenRandomMapEffect(
        String id,
        int minFeature,
        int maxFeature,
        int generationAttempts,
        int minDistanceFromAllFeatures,
        FeatureType featureType) {
        super(id);
        this.minFeature = minFeature;
        this.maxFeature = maxFeature;
        this.generationAttempts = generationAttempts;
        this.minDistanceFromAllFeatures = minDistanceFromAllFeatures;
        this.featureType = featureType;
    }

    @Override
    public void apply(GameState gs) {
        int width = gs.getWidth();
        int height = gs.getHeight();
        int numWildernessDungeons = Random.get().nextInt(minFeature, maxFeature);

        for (int i = 0; i < numWildernessDungeons; i++) {
            boolean placed = false;

            for (int attempt = 0; attempt < generationAttempts; attempt++) {
                int x = Random.get().nextInt(0, width - 1);
                int y = Random.get().nextInt(0, height - 1);
                Position candidate = new Position(x, y);

                if (inBounds(x, y) && isLandTile(gs.getMap().getTileMap()[x][y]) &&
                    isFarFromFeatures(gs, minDistanceFromAllFeatures).test(candidate) &&
                    surroundedBySame(gs.getMap().getTileMap(), x, y)) {
                    featureType.generateFeature(gs, new Position(x, y));
                    placed = true;
                    System.out.println("Placed a random feature!");
                    break;
                }
            }

            if (!placed) {
                System.out.println("Warning: Could not place a random feature!");
            }
        }
    }
}
