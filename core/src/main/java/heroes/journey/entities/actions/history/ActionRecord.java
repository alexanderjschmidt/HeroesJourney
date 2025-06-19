package heroes.journey.entities.actions.history;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.UUID;

import heroes.journey.entities.actions.Action;

public class ActionRecord extends Record {

    private final String action;
    private final String input;

    public ActionRecord(UUID entity, String action, String input) {
        super(entity);
        this.action = action;
        this.input = input;
    }

    public Action getAction() {
        return ActionManager.get(action);
    }

    public String getInput() {
        return input;
    }

}
