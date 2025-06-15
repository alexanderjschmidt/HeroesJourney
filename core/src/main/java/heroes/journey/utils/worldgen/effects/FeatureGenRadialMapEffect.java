package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.NonNull;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.findTileNear;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandSurrounded;

public class FeatureGenRadialMapEffect extends MapGenerationEffect {
    private final int distToCenter;
    private final int numOfFeature;
    @NonNull
    private final FeatureType featureType;

    public FeatureGenRadialMapEffect(String name, int distToCenter,
                                     int numOfFeature, FeatureType featureType) {
        super(name);
        this.distToCenter = distToCenter;
        this.numOfFeature = numOfFeature;
        this.featureType = featureType;
    }

    @Override
    public void apply(GameState gs) {
        int centerX = gs.getWidth() / 2;
        int centerY = gs.getHeight() / 2;
        int radius = Math.min(gs.getWidth(), gs.getHeight()) / distToCenter;

        double randomOffsetDeg = Random.get().nextDouble() * 360.0;
        for (int i = 0; i < numOfFeature; i++) {
            double angleDeg = i * (360.0 / numOfFeature) + randomOffsetDeg;
            double angleRad = Math.toRadians(angleDeg);

            // Polar to Cartesian
            int x = centerX + (int) (Math.cos(angleRad) * radius);
            int y = centerY + (int) (Math.sin(angleRad) * radius);

            // Snap to nearest valid land tile
            Position pos = findTileNear(new Position(x, y), 0, 10,
                isLandSurrounded(gs.getMap().getTileMap()));

            featureType.generateFeature(gs, pos);
        }
    }
}
