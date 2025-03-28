package heroes.journey.ui.hudstates;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

public class ActionSelectState extends HUDState {

    private final List<Action> options;

    public ActionSelectState(List<Action> options) {
        super();
        this.options = options;
    }

    public List<Action> getOptions() {
        return options;
    }

    @Override
    public void enter(HUD hud) {
        hud.getActionMenu().setVisible(true);
        hud.getActionDetailedUI().setVisible(true);
        HUD.get().getActionMenu().open(options);
    }

    @Override
    public void update(HUD hud) {
        if (Gdx.input.isKeyJustPressed(KeyManager.UP)) {
            HUD.get().getActionMenu().decrement();
        } else if (Gdx.input.isKeyJustPressed(KeyManager.DOWN)) {
            HUD.get().getActionMenu().increment();
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            if (!(HUD.get().getActionMenu().getSelected() instanceof TargetAction)) {
                sendAction();
            } else {
                HUD.get().getCursor().setActiveSkill((TargetAction)HUD.get().getActionMenu().getSelected());
            }
            HUD.get().getActionMenu().select();
        } else if (Gdx.input.isKeyJustPressed(KeyManager.ESCAPE) ||
            Gdx.input.isKeyJustPressed(KeyManager.BACK)) {
            HUD.get().revertToPreviousState();
            hud.getCursor().revertSelectedPosition();
        }
    }

    @Override
    public void exit(HUD hud) {
        hud.getActionMenu().setVisible(false);
        hud.getActionDetailedUI().setVisible(false);
    }

    @Override
    public boolean onMessage(HUD entity, Telegram telegram) {
        return false;
    }
}
