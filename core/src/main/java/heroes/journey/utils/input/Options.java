package heroes.journey.utils.input;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.options.OptionAction;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;

import java.util.ArrayList;
import java.util.List;

public class Options {

    private static List<Action> optionsList = new ArrayList<>(2);

    public static void addOption(OptionAction option) {
        option.setDisplay("");
        optionsList.add(option);
    }

    static {
        Action optionsAction = new Action("Options") {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                HUD.get().setState(new ActionSelectState(optionsList));
                return null;
            }
        };
        TeamActions.addTeamAction(optionsAction);
    }
}
