package heroes.journey.components.character;

import com.artemis.World;
import com.artemis.annotations.Transient;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionManager;
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

    // Split this into Possible Actions component and actively doing action component
    private String action;
    private int targetX, targetY;

    public Action getAction() {
        System.out.println(action);
        return ActionManager.get().get(action);
    }

    public ActionComponent action(Action action) {
        this.action = action.toString();
        return this;
    }

    public static ActionComponent get(World world, int entityId) {
        return world.getMapper(ActionComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
        action = null;
        targetX = -1;
        targetY = -1;
    }

    @Override
    public void copy(ActionComponent from) {
        action = from.action;
        targetX = from.targetX;
        targetY = from.targetY;
    }

}
