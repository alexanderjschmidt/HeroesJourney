package heroes.journey.utils.worldgen.effects;

import static heroes.journey.registries.TileManager.baseTiles;
import static heroes.journey.utils.worldgen.CellularAutomata.convertToTileMap;
import static heroes.journey.utils.worldgen.CellularAutomata.smooth;

import heroes.journey.GameState;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.utils.RandomWorldGenerator;

public class NoiseMapEffect extends MapGenerationEffect {
    private final float amplitude;
    private final float roughness;
    private final int octaves;
    private final int smooths;
    private final boolean island;

    public NoiseMapEffect(
        String id,
        float amplitude,
        float roughness,
        int octaves,
        int smooths,
        boolean island) {
        super(id);
        this.amplitude = amplitude;
        this.roughness = roughness;
        this.octaves = octaves;
        this.smooths = smooths;
        this.island = island;
    }

    public NoiseMapEffect(String id, float amplitude, float roughness, int octaves, int smooths) {
        this(id, amplitude, roughness, octaves, smooths, true);
    }

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
