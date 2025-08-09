package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.entities.actions.ActionUtils;
import heroes.journey.modlib.actions.ActionEntry;
import heroes.journey.modlib.actions.ShowAction;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.utils.input.KeyManager;

import java.util.AbstractMap;
import java.util.List;
import java.util.UUID;

import static heroes.journey.mods.Registries.ActionManager;

public class ActionSelectState extends HUDState {

    private final List<ScrollPaneEntry<ActionEntry>> options;

    public ActionSelectState(List<ActionEntry> options) {
        super();
        UUID selectedEntity = GameState.global().getCurrentEntity();
        this.options = filter(options, selectedEntity);
    }

    private List<ScrollPaneEntry<ActionEntry>> filter(List<ActionEntry> input, UUID entityId) {
        return input.stream()
            .map(action -> {
                ShowAction result = ActionUtils
                    .requirementsMet(ActionManager.get(action.getActionId()),
                        new ActionContext(GameState.global(), entityId, false, action.getInput()));
                return new AbstractMap.SimpleEntry<>(action, result);
            })
            .filter(entry -> entry.getValue() != ShowAction.NO)
            .map(entry -> new ScrollPaneEntry<>(entry.getKey(), entry.getValue() != ShowAction.GRAYED))
            .toList();
    }

    @Override
    public void enter(HUD hud) {
        if (HUD.isReverting) {
            return;
        }
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
