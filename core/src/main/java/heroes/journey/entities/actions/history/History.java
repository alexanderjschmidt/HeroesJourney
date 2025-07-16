package heroes.journey.entities.actions.history;

import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import heroes.journey.entities.actions.ActionEntry;

public class History extends Stack<Record> implements Cloneable {

    public void add(ActionEntry action, UUID currentEntity) {
        this.add(new ActionRecord(currentEntity, action.getActionId(), action.getInput()));
    }

    public void add(String actionId, Map<String,String> input, UUID currentEntity) {
        this.add(new ActionRecord(currentEntity, actionId, input));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public History clone() {
        return (History)super.clone();
    }
}

