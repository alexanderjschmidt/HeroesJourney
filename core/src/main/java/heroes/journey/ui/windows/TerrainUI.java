package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.overworld.place.FactionComponent;
import heroes.journey.initializers.base.LoadOptions;
import heroes.journey.initializers.base.Tiles;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.tilemap.wavefunction.Terrain;
import heroes.journey.ui.Cursor;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;

public class TerrainUI extends UI {

    public TerrainUI() {
        super();
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        Cursor cursor = HUD.get().getCursor();
        Terrain tile = GameState.global().getMap().get(cursor.x, cursor.y);
        ActionTerrain environment = GameState.global().getMap().getEnvironment(cursor.x, cursor.y);

        String location = LoadOptions.debugOption.isTrue() ? " (" + cursor.x + ", " + cursor.y + ")" : "";
        String name =
            (tile == null ? "---" : (tile + (environment == null ? "" : " and " + environment))) + location;
        if (environment == Tiles.house || environment == Tiles.dungeon) {
            Integer faction = GameState.global().getEntities().getFaction(cursor.x, cursor.y);
            FactionComponent factionComponent = FactionComponent.get(GameState.global().getWorld(), faction);
            name = factionComponent.toString();
        }
        drawText(batch, name, 0, 0);
    }

}
