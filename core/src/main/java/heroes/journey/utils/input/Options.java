package heroes.journey.utils.input;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.inputs.ActionInput;
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

    static {
        Action optionsAction = new Action("options", "Options") {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                HUD.get().setState(new ActionSelectState(optionsList));
                return null;
            }
        };
        TeamActions.addTeamAction(optionsAction);
    }
}
