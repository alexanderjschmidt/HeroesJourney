package heroes.journey.utils.worldgen.effects;

import java.util.function.BiFunction;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.utils.WaveFunctionCollapse;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;
import lombok.Getter;
import lombok.NonNull;

@Getter
@SuppressWarnings("unchecked")
public class WaveFunctionCollapseMapEffect extends MapGenerationEffect {
    private final boolean environment;
    @NonNull private final BiFunction<GameState,Position,WeightedRandomPicker<Tile>> applyTile;

    public WaveFunctionCollapseMapEffect(
        String id,
        boolean environment,
        BiFunction<GameState,Position,WeightedRandomPicker<Tile>> applyTile) {
        super(id);
        this.environment = environment;
        this.applyTile = applyTile;
    }

    public WaveFunctionCollapseMapEffect(
        String id,
        BiFunction<GameState,Position,WeightedRandomPicker<Tile>> applyTile) {
        this(id, false, applyTile);
    }

    @Override
    public void apply(GameState gs) {
        int width = gs.getWidth();
        int height = gs.getHeight();

        WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                possibleTilesMap[x][y] = applyTile.apply(gs, new Position(x, y));
                if (possibleTilesMap[x][y] == null) {
                    throw new RuntimeException("No Tile should have a null WeightedRandomPicker");
                }
            }
        }

        Tile[][] map = WaveFunctionCollapse.applyWaveFunctionCollapse(possibleTilesMap);
        if (environment)
            gs.getMap().setEnvironment(map);
        else
            gs.getMap().setTileMap(map);
    }
}
