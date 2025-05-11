package heroes.journey.utils.input;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.options.OptionAction;
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
        Action optionsAction = Action.builder().name("Options").onSelect((input) -> {
            HUD.get().setState(new ActionSelectState(optionsList));
            return null;
        }).build().register();
        TeamActions.addTeamAction(optionsAction);
    }
}
