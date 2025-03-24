package heroes.journey.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameState;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.tilemap.wavefunction.Terrain;

public class TerrainUI extends UI {

    private Terrain tile;
    private ActionTerrain environment;

    public TerrainUI() {
        super(0, 0, 8, 1);
    }

    @Override
    public void update() {
        Cursor cursor = HUD.get().getCursor();
        tile = GameState.global().getMap().get(cursor.x, cursor.y);
        environment = GameState.global().getMap().getEnvironment(cursor.x, cursor.y);
    }

    @Override
    public void drawUI(Batch batch, float parentAlpha) {
        Cursor cursor = HUD.get().getCursor();
        String name = (tile == null ? "---" : (tile + (environment == null ? "" : " and " + environment))) + (" (" + cursor.x + ", " + cursor.y + ")");
        drawText(batch, name, 0, 0);
    }

}
