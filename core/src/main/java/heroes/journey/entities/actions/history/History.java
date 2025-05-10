package heroes.journey.entities.actions.history;

import heroes.journey.entities.actions.Action;

import java.util.Stack;
import java.util.UUID;

public class History extends Stack<Record> implements Cloneable {

    public void add(Action action, UUID currentEntity) {
        this.add(new ActionRecord(currentEntity, action));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public History clone() {
        return (History) super.clone();
    }
}

