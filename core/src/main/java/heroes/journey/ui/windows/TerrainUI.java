package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.initializers.base.actions.LoadOptions;
import heroes.journey.tilemap.wavefunctiontiles.ActionTerrain;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
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

        String locationCoords = LoadOptions.debugOption.isTrue() ?
            " (" + cursor.x + ", " + cursor.y + ")" :
            "";
        String name = (tile == null ? "---" : (tile + (environment == null ? "" : " and " + environment)));
        if (environment != null) {
            Integer locationId = GameState.global().getEntities().getLocation(cursor.x, cursor.y);
            if (locationId != null) {
                name = NamedComponent.get(GameState.global().getWorld(), locationId, "Unnamed Location");
            }
        }
        drawText(batch, name + locationCoords, 0, 0);
    }

}
