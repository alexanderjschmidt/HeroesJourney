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
import heroes.journey.entities.actions.ActionEntry;
import heroes.journey.systems.GameWorld;

public class PossibleActionsComponent extends PooledClonableComponent<PossibleActionsComponent> {

    private final Set<String> possibleActions;
    private final Map<String,Integer> cooldowns;

    public PossibleActionsComponent() {
        possibleActions = new HashSet<>();
        cooldowns = new HashMap<>();
    }

    public List<ActionEntry> getPossibleActions(UUID entityId) {
        return ActionManager.get(possibleActions.stream().toList())
            .stream()
            .map(action -> new ActionEntry(action.getId(), entityId + ""))
            .toList();
    }

    public PossibleActionsComponent addAction(Action action) {
        possibleActions.add(action.getId());
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

    public Map<String,Integer> getCooldowns() {
        return this.cooldowns;
    }
}
