package heroes.journey.entities.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionManager extends HashMap<String,Action> {

    private static ActionManager actionManager;

    private ActionManager() {
    }

    public static ActionManager get() {
        if (actionManager == null)
            actionManager = new ActionManager();
        return actionManager;
    }

    public static List<Action> get(List<String> actionStrings) {
        return actionStrings.stream().map(actionManager::get) // get the Action for each string
            .filter(Objects::nonNull) // optionally skip nulls
            .collect(Collectors.toList());
    }

    public static Action register(Action action) {
        get().put(action.name, action);
        return action;
    }

}
