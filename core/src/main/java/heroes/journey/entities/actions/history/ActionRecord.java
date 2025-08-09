package heroes.journey.entities.actions.history;

import heroes.journey.modlib.actions.Action;

import java.util.Map;
import java.util.UUID;

import static heroes.journey.mods.Registries.ActionManager;

public class ActionRecord extends Record {

    private final String action;
    private final Map<String, String> input;

    public ActionRecord(UUID entity, String action, Map<String, String> input) {
        super(entity);
        this.action = action;
        this.input = input;
    }

    public Action getAction() {
        return ActionManager.get(action);
    }

    public Map<String, String> getInput() {
        return input;
    }

}
