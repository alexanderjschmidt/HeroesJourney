package heroes.journey.entities.actions.history;

import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;

public class ActionRecord extends Record {

    private final Action action;
    private final Position position;

    public ActionRecord(Integer entity, Action action, Position position) {
        super(entity);
        this.action = action;
        this.position = position;
    }

    public Action getAction() {
        return action;
    }

    public Position getPosition() {
        return position;
    }

}
