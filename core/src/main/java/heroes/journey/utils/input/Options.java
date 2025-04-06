package heroes.journey.utils.input;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;

public class Options {

    public static List<Action> optionsList = new ArrayList<>(2);

    static {
        new Action.Builder().name("Options")
            .teamAction(true)
            .terminalAction(false)
            .onSelect()
            .add((gs, e) -> {
                HUD.get().setState(new ActionSelectState(optionsList));
                return null;
            });
    }
}
