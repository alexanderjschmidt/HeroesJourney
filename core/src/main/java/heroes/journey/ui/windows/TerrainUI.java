package heroes.journey.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.Ids;
import heroes.journey.registries.Registries;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.ui.Cursor;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

import java.util.UUID;

public class TerrainUI extends UI {

    private final Label terrain;

    public TerrainUI() {
        super();
        this.terrain = new Label("", ResourceManager.get().skin);
        this.terrain.setWrap(true);
        this.mainTable.add(terrain).expand().row();

        pack();
    }

    @Override
    public void update() {
        Cursor cursor = HUD.get().getCursor();
        Terrain tile = GameState.global().getMap().get(cursor.x, cursor.y);
        Terrain environment = GameState.global().getMap().getEnvironment(cursor.x, cursor.y);

        String locationCoords = ((heroes.journey.entities.actions.options.BooleanOptionAction)Registries.ActionManager.get(
            Ids.DEBUG)).isTrue() ? " (" + cursor.x + ", " + cursor.y + ")" : "";
        String name = (tile == null ? "---" : (tile + (environment == null ? "" : " and " + environment)));
        if (environment != null) {
            UUID locationId = GameState.global().getEntities().getLocation(cursor.x, cursor.y);
            if (locationId != null) {
                name = NamedComponent.get(GameState.global().getWorld(), locationId, "Unnamed Location");
            }
        }

        terrain.setText(name + locationCoords);
    }

}
