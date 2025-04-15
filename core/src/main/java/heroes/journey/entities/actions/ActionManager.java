package heroes.journey.entities.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.ui.ScrollPaneEntry;

public class ActionManager extends HashMap<String,Action> {

    private final List<Action> teamActions;

    private static ActionManager actionManager;

    public static ActionManager get() {
        if (actionManager == null)
            actionManager = new ActionManager();
        return actionManager;
    }

    private ActionManager() {
        teamActions = new ArrayList<Action>();
    }

    public static List<ScrollPaneEntry<Action>> getTeamActions(GameState gameState) {
        ArrayList<ScrollPaneEntry<Action>> options = new ArrayList<>(get().teamActions.size());
        for (Action action : get().teamActions) {
            if (action.requirementsMet(gameState, null) == ShowAction.YES)
                options.add(new ScrollPaneEntry<>(action, true));
        }
        return options;
    }

    public static void addTeamAction(Action action) {
        get().teamActions.addFirst(action);
    }

}
