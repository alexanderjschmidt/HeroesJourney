package heroes.journey.entities.actions.history;

import heroes.journey.entities.actions.Action;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ActionRecord extends Record {

    private final Action action;

    public ActionRecord(UUID entity, Action action) {
        super(entity);
        this.action = action;
    }

}
