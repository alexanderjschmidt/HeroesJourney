package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import heroes.journey.GameCamera;
import heroes.journey.initializers.base.actions.LoadOptions;
import heroes.journey.ui.HUD;
import heroes.journey.ui.windows.Display;
import heroes.journey.utils.input.KeyManager;

import static heroes.journey.ui.hudstates.States.STATS;

class GlobalState extends HUDState {
    @Override
    public void update(HUD hud) {
        HUD.get().getCursor().update();
        if (Gdx.input.isKeyJustPressed(KeyManager.DEV_MODE)) {
            LoadOptions.debugOption.onSelect(null, false);
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_INVENTORY) && HUD.get().getState() != STATS &&
            HUD.get().getCursor().getHover() != null) {
            HUD.get().setState(STATS);
            HUD.get().getStatsUI().updatePanel(Display.INVENTORY);
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_QUESTS) && HUD.get().getState() != STATS &&
            HUD.get().getCursor().getHover() != null) {
            HUD.get().setState(STATS);
            HUD.get().getStatsUI().updatePanel(Display.QUESTS);
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_STATS) && HUD.get().getState() != STATS &&
            HUD.get().getCursor().getHover() != null) {
            HUD.get().setState(STATS);
            HUD.get().getStatsUI().updatePanel(Display.STATS);
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.ZOOM_IN)) {
            GameCamera.get().zoomIn();
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.ZOOM_OUT)) {
            GameCamera.get().zoomOut();
        }
    }
}
