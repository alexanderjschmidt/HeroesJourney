package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.initializers.base.BaseActions;

import java.util.ArrayList;

public class ActionComponent extends ArrayList<Action> implements ClonableComponent<ActionComponent> {

    private Action action;
    private int targetX, targetY;

    public ActionComponent() {
        add(BaseActions.wait);
    }

    public void act(Action action, int targetX, int targetY) {
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
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

    public ActionComponent addAction(CooldownAction action, Entity entity) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        if (cooldownComponent == null)
            entity.add(new CooldownComponent());
        add(action);
        return this;
    }

    public ActionComponent addAction(Action action) {
        add(action);
        return this;
    }

    public ActionComponent clone() {
        ActionComponent clone = new ActionComponent();
        clone.action = action;
        clone.targetX = targetX;
        clone.targetY = targetY;
        clone.addAll(this);
        return clone;
    }

    private static final ComponentMapper<ActionComponent> mapper = ComponentMapper.getFor(
        ActionComponent.class);

    public static ActionComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
