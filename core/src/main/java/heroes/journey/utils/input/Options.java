package heroes.journey.utils.input;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.entities.actions.options.OptionAction;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;

public class Options {

    private static List<Action> optionsList = new ArrayList<>(2);

    public static void addOption(OptionAction option) {
        option.setDisplay("");
        optionsList.add(option);
    }

    public static boolean isTrue(String optionAction) {
        Action action = ActionManager.get(optionAction);
        if (action instanceof BooleanOptionAction booleanOptionAction) {
            return booleanOptionAction.isTrue();
        }
        return false;
    }

    public static void toggle(String optionAction) {
        Action action = ActionManager.get(optionAction);
        if (action instanceof BooleanOptionAction booleanOptionAction) {
            booleanOptionAction.onSelect(null, false);
        }
    }

    static {
        Action optionsAction = new Action("options", "Options", "", true, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                HUD.get().setState(new ActionSelectState(optionsList));
                return null;
            }
        };
        TeamActions.addTeamAction(optionsAction);
    }
}
