package heroes.journey.components;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

public class PossibleActionsComponent extends PooledClonableComponent<PossibleActionsComponent> {

    private final Set<String> possibleActions;
    @Getter private final Map<String,Integer> cooldowns;

    public PossibleActionsComponent() {
        possibleActions = new HashSet<>();
        cooldowns = new HashMap<>();
        possibleActions.add(BaseActions.rest.toString());
    }

    public List<Action> getPossibleActions() {
        return ActionManager.get(possibleActions.stream().toList());
    }

    public PossibleActionsComponent addAction(Action action) {
        possibleActions.add(action.toString());
        return this;
    }

    public static PossibleActionsComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(PossibleActionsComponent.class, entityId);
    }

    public PossibleActionsComponent removeAction(Action action) {
        possibleActions.remove(action);
        return this;
    }

    @Override
    protected void reset() {
        possibleActions.clear();
        cooldowns.clear();
    }

    @Override
    public void copy(PossibleActionsComponent from) {
        possibleActions.addAll(from.possibleActions);
        cooldowns.putAll(from.cooldowns);
    }
}
