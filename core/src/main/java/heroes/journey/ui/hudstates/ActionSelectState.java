package heroes.journey.ui.hudstates;

import java.util.List;

import com.badlogic.gdx.Gdx;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionQueue;
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
            // TODO this seems to trigger twice when its a nested action menu causing two turns to pass
            // Need to redo the ActionQueue and selecting options to only end once.
            // its getting to turn 2 before it resolves the select quest
            if (!(HUD.get().getActionMenu().getSelected() instanceof TargetAction) &&
                HUD.get().getActionMenu().getSelected().isTerminal()) {
                ActionQueue.get().sendAction(HUD.get().getActionMenu().getSelected(), pathHolder);
                pathHolder = null;
            } else if (HUD.get().getActionMenu().getSelected() instanceof TargetAction targetAction) {
                HUD.get().getCursor().setActiveSkill(targetAction);
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

}
