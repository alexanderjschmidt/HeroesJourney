package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.findTileNear;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandSurrounded;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.utils.Random;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class FeatureGenRadialMapEffect extends MapGenerationEffect {

    private final int distToCenter, numOfFeature;
    @NonNull private FeatureType featureType;

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
            int x = centerX + (int)(Math.cos(angleRad) * radius);
            int y = centerY + (int)(Math.sin(angleRad) * radius);

            // Snap to nearest valid land tile
            Position pos = findTileNear(new Position(x, y), 0, 10,
                isLandSurrounded(gs.getMap().getTileMap()));

            featureType.generateFeature(gs, pos);
        }
    }

}
