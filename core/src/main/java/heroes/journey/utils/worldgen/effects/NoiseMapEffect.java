package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;
import static heroes.journey.utils.worldgen.utils.WaveFunctionCollapse.baseTiles;

import heroes.journey.GameState;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.utils.RandomWorldGenerator;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class NoiseMapEffect extends MapGenerationEffect {

    private final float amplitude, roughness;
    private final int octaves, smooths;
    @Builder.Default private final boolean island = true;

    @Override
    public void apply(GameState gs) {
        int width = gs.getWidth();

        RandomWorldGenerator noiseGen = new RandomWorldGenerator(amplitude, octaves, roughness, island);
        int[][] noiseMap = noiseGen.generateMap(width);
        Tile[][] tileMap = convertToTileMap(noiseMap);
        for (int i = 0; i < smooths; i++) {
            smooth(tileMap, baseTiles);
        }

        gs.getMap().setTileMap(tileMap);
    }
}
