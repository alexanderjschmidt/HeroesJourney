package heroes.journey.utils.worldgen.effects;

import static heroes.journey.utils.worldgen.utils.WaveFunctionCollapse.possibleTiles;

import java.util.function.BiFunction;

import heroes.journey.GameState;
import heroes.journey.entities.Position;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.worldgen.MapGenerationEffect;
import heroes.journey.utils.worldgen.utils.WaveFunctionCollapse;
import heroes.journey.utils.worldgen.utils.WeightedRandomPicker;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@SuppressWarnings("unchecked")
public class WaveFunctionCollapseMapEffect extends MapGenerationEffect {

    @Builder.Default private final boolean environment = false;

    @NonNull private final BiFunction<GameState,Position,WeightedRandomPicker<Tile>> applyTile;

    @Override
    public void apply(GameState gs) {
        int width = gs.getWidth();
        int height = gs.getHeight();

        WeightedRandomPicker<Tile>[][] possibleTilesMap = new WeightedRandomPicker[width][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                possibleTilesMap[x][y] = applyTile.apply(gs, new Position(x, y));
                if (possibleTilesMap[x][y] == null) {
                    possibleTilesMap[x][y] = new WeightedRandomPicker<>();
                    for (Tile t : possibleTiles) {
                        possibleTilesMap[x][y].addItem(t, t.getWeight());
                    }
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
