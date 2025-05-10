package heroes.journey.entities.actions.history;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionManager;

import java.util.UUID;

public class ActionRecord extends Record {

    private final String action;

    public ActionRecord(UUID entity, String action) {
        super(entity);
        this.action = action;
    }

    public Action getAction() {
        return ActionManager.get().get(action);
    }

}
