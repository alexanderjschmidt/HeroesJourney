package heroes.journey.ui.hudstates;

import java.util.AbstractMap;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.Gdx;

import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.utils.input.KeyManager;

public class ActionSelectState extends HUDState {

    private final List<ScrollPaneEntry<Action>> options;

    public ActionSelectState(List<Action> options) {
        super();
        UUID selectedEntity = GameState.global().getCurrentEntity();
        this.options = filter(options, selectedEntity);
    }

    private List<ScrollPaneEntry<Action>> filter(List<Action> input, UUID entityId) {
        ActionInput actionInput = new ActionInput(GameState.global(), entityId);
        return input.stream()
            .map(action -> {
                ShowAction result = action.requirementsMet(actionInput);
                return new AbstractMap.SimpleEntry<>(action, result);
            })
            .filter(entry -> entry.getValue() != ShowAction.NO)
            .map(entry -> new ScrollPaneEntry<>(entry.getKey(), entry.getValue() != ShowAction.GRAYED))
            .toList();
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
        if (Gdx.input.isKeyJustPressed(KeyManager.ESCAPE) || Gdx.input.isKeyJustPressed(KeyManager.BACK)) {
            HUD.get().revertToPreviousState();
            if (!(HUD.get().getState() instanceof ActionSelectState))
                hud.getCursor().revertSelectedPosition();
        }
    }

    @Override
    public void exit(HUD hud) {
        hud.getActionMenu().setVisible(false);
        hud.getActionDetailedUI().setVisible(false);
    }

}
