package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.entities.actions.ActionUtils;
import heroes.journey.modlib.Ids;
import heroes.journey.ui.HUD;
import heroes.journey.ui.windows.Display;
import heroes.journey.utils.input.KeyManager;

import static heroes.journey.mods.Registries.ActionManager;
import static heroes.journey.ui.hudstates.States.STATS;

class GlobalState extends HUDState {
    @Override
    public void update(HUD hud) {
        HUD.get().getCursor().update();
        if (Gdx.input.isKeyJustPressed(KeyManager.DEV_MODE)) {
            ActionUtils.onSelect(
                ActionManager.get(Ids.DEBUG),
                new ActionContext(
                    GameState.global(), false
                )
            );
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_QUESTS) && HUD.get().getState() != STATS &&
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
