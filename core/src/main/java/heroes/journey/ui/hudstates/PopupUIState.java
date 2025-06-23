package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;

import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

public class PopupUIState extends HUDState {

    private boolean justOpened = false;
    private boolean resetOnClose = false;

    public PopupUIState() {
        super();
    }

    @Override
    public void enter(HUD hud) {
        System.out.println("open popup");
        justOpened = true;
        resetOnClose = false;
        hud.updateCenterPanel();
        hud.getPopupUI().setVisible(true);
    }

    @Override
    public void update(HUD hud) {
        if (Gdx.input.isKeyJustPressed(KeyManager.ANY) && !justOpened) {
            System.out.println("close popup");
            if (resetOnClose)
                HUD.get().revertToInitialState(true);
            else
                HUD.get().revertToPreviousState();
        }
        justOpened = false;
    }

    @Override
    public void exit(HUD hud) {
        resetOnClose = false;
        hud.getPopupUI().setVisible(false);
    }

    public void resetOnClose() {
        resetOnClose = true;
    }
}
