package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.ui.HUD;
import heroes.journey.utils.Random;
import heroes.journey.utils.input.KeyManager;
import heroes.journey.utils.input.Options;
import heroes.journey.utils.worldgen.NewMapManager;

class GlobalState extends HUDState {
    @Override
    public void update(HUD hud) {
        HUD.get().getCursor().update();
        if (Gdx.input.isKeyJustPressed(KeyManager.DEVMODE)) {
            Options.DEBUG = !Options.DEBUG;
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.RE_GEN_MAP)) {
            Random.get().setSeed((int)(Math.random() * 10000000));
            NewMapManager.get().initMapGeneration(GameState.global());
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_JOB_INFO)) {
            HUD.get().getEntityDetailedUI().update();
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.ZOOM_IN)) {
            GameCamera.get().zoomIn();
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.ZOOM_OUT)) {
            GameCamera.get().zoomOut();
        }
    }
}
