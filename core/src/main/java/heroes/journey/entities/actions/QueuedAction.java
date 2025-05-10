package heroes.journey.entities.actions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class QueuedAction {

    private Action action;
    private UUID entityId;

    public QueuedAction(Action action, UUID entityId) {
        this.action = action;
        this.entityId = entityId;
    }

    public String toString() {
        return action + " " + entityId;
    }

}
