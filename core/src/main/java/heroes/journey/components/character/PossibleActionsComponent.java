package heroes.journey.components.character;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionManager;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.initializers.base.BaseActions;

public class PossibleActionsComponent extends PooledClonableComponent<PossibleActionsComponent> {

    private final Set<String> possibleActions;

    public PossibleActionsComponent() {
        possibleActions = new HashSet<>();
        possibleActions.add(BaseActions.wait.toString());
    }

    public List<Action> getPossibleActions() {
        return ActionManager.get(possibleActions.stream().toList());
    }

    public PossibleActionsComponent addAction(CooldownAction action, World world, int entityId) {
        CooldownComponent cooldownComponent = CooldownComponent.get(world, entityId);
        if (cooldownComponent == null) {
            world.edit(entityId).create(CooldownComponent.class);
        }
        possibleActions.add(action.toString());
        return this;
    }

    public PossibleActionsComponent addAction(Action action) {
        possibleActions.add(action.toString());
        return this;
    }

    public static PossibleActionsComponent get(World world, int entityId) {
        return world.getMapper(PossibleActionsComponent.class).get(entityId);
    }

    public PossibleActionsComponent removeAction(Action action) {
        possibleActions.remove(action);
        return this;
    }

    @Override
    protected void reset() {
        possibleActions.clear();
    }

    @Override
    public void copy(PossibleActionsComponent from) {
        possibleActions.addAll(from.possibleActions);
    }
}
