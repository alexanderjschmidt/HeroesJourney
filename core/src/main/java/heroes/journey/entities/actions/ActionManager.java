package heroes.journey.entities.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import heroes.journey.GameState;

public class ActionManager extends HashMap<String,Action> {

    private static final long serialVersionUID = 1L;

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

    public static List<Action> getTeamActions(GameState gameState) {
        ArrayList<Action> options = new ArrayList<Action>(get().teamActions.size());
        for (Action action : get().teamActions) {
            if (action.requirementsMet(gameState, null))
                options.add(action);
        }
        return options;
    }

    public static Action getAction(String actionName) {
        Action action = get().get(actionName);
        if (action == null)
            System.out.println("SKILL NOT FOUND");
        return action;
    }

    public static void addTeamAction(Action action) {
        get().teamActions.addFirst(action);
    }

}
