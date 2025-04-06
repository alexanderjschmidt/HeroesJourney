package heroes.journey.utils.input;

import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
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
                HUD.get().setState(new ActionSelectState(optionsList));
                return null;
            });
    }
}
