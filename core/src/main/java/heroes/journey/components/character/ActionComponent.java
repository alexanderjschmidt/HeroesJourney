package heroes.journey.components.character;

import com.artemis.annotations.Transient;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionManager;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@ToString
@Transient
@Accessors(fluent = true, chain = true)
@Setter
@Getter
public class ActionComponent extends PooledClonableComponent<ActionComponent> {

    private String action;

    public Action getAction() {
        return ActionManager.get().get(action);
    }

    public ActionComponent action(Action action) {
        this.action = action.getName();
        return this;
    }

    public static ActionComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(ActionComponent.class, entityId);
    }

    @Override
    protected void reset() {
        action = null;
    }

    @Override
    public void copy(ActionComponent from) {
        action = from.action;
    }

}
