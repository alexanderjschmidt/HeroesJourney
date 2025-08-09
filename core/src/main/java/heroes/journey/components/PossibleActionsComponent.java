package heroes.journey.components;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.modlib.actions.Action;
import heroes.journey.modlib.actions.ActionEntry;
import heroes.journey.systems.GameWorld;

import java.util.*;

import static heroes.journey.mods.Registries.ActionManager;

public class PossibleActionsComponent extends PooledClonableComponent<PossibleActionsComponent> {

    private final Set<String> possibleActions;
    private final Map<String, Integer> cooldowns;

    public PossibleActionsComponent() {
        possibleActions = new HashSet<>();
        cooldowns = new HashMap<>();
    }

    public List<ActionEntry> getPossibleActions(UUID entityId) {
        return ActionManager.get(possibleActions.stream().toList()).stream().map(action -> {
            Map<String, String> inputs = new HashMap<>(1);
            inputs.put("owner", entityId + "");
            return new ActionEntry(action.getId(), inputs);
        }).toList();
    }

    public List<Action> getPossibleActions() {
        return ActionManager.get(possibleActions.stream().toList());
    }

    public PossibleActionsComponent addAction(String actionId) {
        possibleActions.add(actionId);
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

    public Map<String, Integer> getCooldowns() {
        return this.cooldowns;
    }
}
