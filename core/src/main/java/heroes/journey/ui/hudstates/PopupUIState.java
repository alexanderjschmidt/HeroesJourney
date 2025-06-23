package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;

import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

public class PopupUIState extends HUDState {

    private boolean justOpened = false;

    public PopupUIState() {
        super();
    }

    @Override
    public void enter(HUD hud) {
        justOpened = true;
        hud.updateCenterPanel();
        hud.getPopupUI().setVisible(true);
    }

    @Override
    public void update(HUD hud) {
        if (Gdx.input.isKeyJustPressed(KeyManager.ANY) && !justOpened) {
            System.out.println("close popup");
            HUD.get().revertToPreviousState();
        }
        justOpened = false;
    }

    @Override
    public void exit(HUD hud) {
        hud.getPopupUI().setVisible(false);
    }
}
