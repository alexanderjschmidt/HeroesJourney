package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import heroes.journey.GameState;
import heroes.journey.ui.Cursor;
import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

public class StatsUIState extends HUDState {

    private boolean justOpened = false;

    public StatsUIState() {
        super();
    }

    @Override
    public void enter(HUD hud) {
        justOpened = true;
        hud.updateCenterPanel();
        hud.getStatsUI().setVisible(true);
        Cursor cursor = HUD.get().getCursor();
        hud.getStatsUI().setEntity(GameState.global().getEntities().get(cursor.x, cursor.y));
    }

    @Override
    public void update(HUD hud) {
        HUD.get().getStatsUI().handleInputs();
        if (Gdx.input.isKeyJustPressed(KeyManager.ESCAPE) || Gdx.input.isKeyJustPressed(KeyManager.BACK) ||
            (Gdx.input.isKeyJustPressed(KeyManager.SHOW_JOB_INFO) && !justOpened)) {
            HUD.get().revertToPreviousState();
        }
        justOpened = false;
    }

    @Override
    public void exit(HUD hud) {
        hud.getStatsUI().setVisible(false);
    }

}
