package heroes.journey.entities.actions.history;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.Map;
import java.util.UUID;

import heroes.journey.entities.actions.Action;

public class ActionRecord extends Record {

    private final String action;
    private final Map<String,String> input;

    public ActionRecord(UUID entity, String action, Map<String,String> input) {
        super(entity);
        this.action = action;
        this.input = input;
    }

    public Action getAction() {
        return ActionManager.get(action);
    }

    public Map<String,String> getInput() {
        return input;
    }

}
