package heroes.journey.utils.input;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.options.OptionAction;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;

public class Options {

    private static List<Action> optionsList = new ArrayList<>(2);

    public static void addOption(OptionAction option) {
        option.setDisplay("");
        optionsList.add(option);
    }

    static {
        Action optionsAction = Action.builder().name("Options").terminal(false).onSelect((gs, e) -> {
            List<ScrollPaneEntry<Action>> options = optionsList.stream()
                .map(key -> new ScrollPaneEntry<>(key, true))
                .toList();
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).build().register();
        TeamActions.addTeamAction(optionsAction);
    }
}
