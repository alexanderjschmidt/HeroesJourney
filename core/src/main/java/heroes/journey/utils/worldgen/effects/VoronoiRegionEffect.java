package heroes.journey.utils.worldgen.effects;

import static heroes.journey.registries.Registries.TerrainManager;
import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;
import static heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator.buildRegionsFromMap;
import static heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator.generateInwardRegionRings;

import java.util.List;
import java.util.stream.Collectors;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.MapGenerationException;
import heroes.journey.utils.worldgen.utils.VoronoiRegionGenerator;
import lombok.Getter;
import heroes.journey.initializers.Ids;

@Getter
public class VoronoiRegionEffect extends MapGenerationEffect {

    private final List<Integer> regionsPerRing;
    private final List<Boolean> offsetRings;

    public VoronoiRegionEffect(String id, List<Integer> regionsPerRing, List<Boolean> offsetRings) {
        super(id);
        this.regionsPerRing = regionsPerRing;
        this.offsetRings = offsetRings;
    }

    public void apply(GameState gs) {
        int centerX = gs.getWidth() / 2;
        int centerY = gs.getHeight() / 2;
        Position center = new Position(centerX, centerY);
        List<List<Position>> centerPoints = generateInwardRegionRings(center,
            (float)centerX / regionsPerRing.size(), regionsPerRing, offsetRings, 0.05f);
        boolean[][] isLand = new boolean[gs.getWidth()][gs.getHeight()];

        for (int x = 0; x < gs.getWidth(); x++) {
            for (int y = 0; y < gs.getHeight(); y++) {
                isLand[x][y] = inBounds(x, y) &&
                    gs.getMap().getTileMap()[x][y].getTerrain() != TerrainManager.get(Ids.TERRAIN_WATER);
            }
        }

        List<Position> centerPointsFlat = centerPoints.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        int[][] result = VoronoiRegionGenerator.generateRegionMap(isLand, centerPointsFlat,
            centerPointsFlat.size());
        buildRegionsFromMap(gs, centerPoints, result);
        int createdRegions = gs.getWorld().getRegionSubscription().getEntities().size();
        if (createdRegions != centerPointsFlat.size())
            throw new MapGenerationException(
                "Could not produce enough regions, target: " + centerPointsFlat.size() + ", created: " +
                    createdRegions);
    }
}
