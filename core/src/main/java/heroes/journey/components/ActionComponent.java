package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.actions.Action;

public class ActionComponent implements ClonableComponent<ActionComponent> {

    // Split this into Possible Actions component and actively doing action component
    private final Action action;
    private final int targetX, targetY;

    public ActionComponent(Action action, int targetX, int targetY) {
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public ActionComponent(Action action) {
        this(action, -1, -1);
    }

    public Action getAction() {
        return action;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public ActionComponent clone() {
        return new ActionComponent(action, targetX, targetY);
    }

    public String toString() {
        return action + " (" + targetX + ", " + targetY + ")";
    }

    private static final ComponentMapper<ActionComponent> mapper = ComponentMapper.getFor(
        ActionComponent.class);

    public static ActionComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
