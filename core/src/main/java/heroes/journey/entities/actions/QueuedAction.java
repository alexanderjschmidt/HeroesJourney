package heroes.journey.entities.actions;

import java.util.UUID;

import heroes.journey.modlib.actions.ActionEntry;
import lombok.Getter;

@Getter
public class QueuedAction {

    private final ActionEntry action;
    private final UUID entityId;

    public QueuedAction(ActionEntry action, UUID entityId) {
        this.action = action;
        this.entityId = entityId;
    }

    public String toString() {
        return action.getActionId() + " " + entityId;
    }

}
