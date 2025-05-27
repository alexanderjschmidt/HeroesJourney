package heroes.journey.entities.actions.history;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.UUID;

import heroes.journey.entities.actions.Action;

public class ActionRecord extends Record {

    private final String action;

    public ActionRecord(UUID entity, String action) {
        super(entity);
        this.action = action;
    }

    public Action getAction() {
        return ActionManager.get(action);
    }

}
