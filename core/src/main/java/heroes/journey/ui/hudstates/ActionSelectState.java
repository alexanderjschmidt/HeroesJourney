package heroes.journey.ui.hudstates;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.utils.input.KeyManager;

import java.util.List;

public class ActionSelectState extends HUDState {

    private final List<ScrollPaneEntry<Action>> options;

    public ActionSelectState(List<ScrollPaneEntry<Action>> options) {
        super();
        this.options = options;
    }

    public List<ScrollPaneEntry<Action>> getOptions() {
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
        HUD.get().getActionMenu().handleInputs();
        if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            Action selectedAction = HUD.get().getActionMenu().getSelected();
            // TODO add back TargetAction logic
            if (selectedAction.isTerminal()) {
                Entity selectedEntity = HUD.get().getCursor().getSelected();
                selectedEntity.add(new ActionComponent(selectedAction));
                HUD.get().revertToInitialState();
            } else {
                HUD.get().getActionMenu().select();
            }
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
