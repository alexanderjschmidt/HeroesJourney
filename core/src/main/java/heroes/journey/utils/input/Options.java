package heroes.journey.utils.input;

import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;

import java.util.ArrayList;
import java.util.List;

public class Options {

    public static List<Action> optionsList = new ArrayList<>(2);

    static {
        new Action.Builder().name("Options")
            .teamAction(true)
            .terminalAction(false)
            .onSelect((gs, e) -> {
                List<ScrollPaneEntry<Action>> options = optionsList.stream().map(key -> new ScrollPaneEntry<>(key, true)).toList();
                HUD.get().setState(new ActionSelectState(options));
                return null;
            });
    }
}
