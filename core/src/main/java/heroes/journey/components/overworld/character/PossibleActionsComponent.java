package heroes.journey.components.overworld.character;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.initializers.base.BaseActions;

import java.util.ArrayList;

public class PossibleActionsComponent extends ArrayList<Action> implements ClonableComponent<PossibleActionsComponent> {

    public PossibleActionsComponent() {
        add(BaseActions.wait);
    }

    public PossibleActionsComponent addAction(CooldownAction action, Entity entity) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        if (cooldownComponent == null) {
            entity.add(new CooldownComponent());
        }
        add(action);
        return this;
    }

    public PossibleActionsComponent addAction(Action action) {
        add(action);
        return this;
    }

    public PossibleActionsComponent clone() {
        PossibleActionsComponent clone = new PossibleActionsComponent();
        clone.addAll(this);
        return clone;
    }

    private static final ComponentMapper<PossibleActionsComponent> mapper = ComponentMapper.getFor(
        PossibleActionsComponent.class);

    public static PossibleActionsComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
