package heroes.journey.utils.worldgen.effects;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.registries.FeatureManager;
import heroes.journey.registries.RegionManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerationException;
import heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static heroes.journey.initializers.base.Tiles.water;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;
import static heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator.buildRegionsFromMap;

@Getter
public class VoronoiRegionEffect extends MapGenerationEffect {

    private final int numRegions;
    private final List<FeatureType> featuresToMakeRegionsAround;

    public VoronoiRegionEffect(String id, int numRegions, FeatureType... featuresToMakeRegionsAround) {
        super(id);
        this.numRegions = numRegions;
        this.featuresToMakeRegionsAround = List.of(featuresToMakeRegionsAround);
    }

    public void apply(GameState gs) {
        List<Position> centerPoints = new ArrayList<>();
        for (Feature feature : FeatureManager.get().values()) {
            if (featuresToMakeRegionsAround.contains(feature.getType()))
                centerPoints.add(feature.location);
        }
        boolean[][] isLand = new boolean[gs.getWidth()][gs.getHeight()];

        for (int x = 0; x < gs.getWidth(); x++) {
            for (int y = 0; y < gs.getHeight(); y++) {
                isLand[x][y] = inBounds(x, y) && gs.getMap().getTileMap()[x][y].getTerrain() != water;
            }
        }

        int[][] result = VoronoiRegionGenerator.generateRegionMap(isLand, centerPoints, numRegions);
        gs.getMap().setRegionMap(result);
        buildRegionsFromMap(result);
        if (RegionManager.get().size() != numRegions)
            throw new MapGenerationException("Could not produce enough regions");
    }
}
