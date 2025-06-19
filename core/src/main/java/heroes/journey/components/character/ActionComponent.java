package heroes.journey.components.character;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.UUID;

import com.artemis.annotations.Transient;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionEntry;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Transient
@Accessors(fluent = true, chain = true)
@Setter
@Getter
public class ActionComponent extends PooledClonableComponent<ActionComponent> {

    private String action;
    private String input;

    public Action getAction() {
        return ActionManager.get(action);
    }

    public ActionComponent action(Action action) {
        this.action = action.getId();
        return this;
    }

    public ActionComponent action(ActionEntry actionEntry) {
        this.action = actionEntry.getId();
        this.input = actionEntry.getInput();
        return this;
    }

    public ActionComponent action(Action action, String input) {
        this.action = action.getId();
        this.input = input;
        return this;
    }

    public static ActionComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(ActionComponent.class, entityId);
    }

    @Override
    protected void reset() {
        action = null;
        input = null;
    }

    @Override
    public void copy(ActionComponent from) {
        action = from.action;
    }

}
