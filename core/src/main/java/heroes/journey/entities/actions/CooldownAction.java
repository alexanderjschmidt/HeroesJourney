package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.CooldownComponent;

public abstract class CooldownAction extends Action {

    private final int turnCooldown;

    public CooldownAction(String name, int turnCooldown) {
        super(name);
        this.turnCooldown = turnCooldown;
    }

    @Override
    public boolean requirementsMet(GameState gameState, Entity entity) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        return !cooldownComponent.containsKey(this) && requirementsMetHelper(gameState, entity);
    }

    @Override
    public String onSelect(GameState gameState, Entity entity) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        cooldownComponent.put(this, turnCooldown);
        return onSelectHelper(gameState, entity);
    }

    public abstract boolean requirementsMetHelper(GameState gameState, Entity selected);

    public abstract String onSelectHelper(GameState gameState, Entity selected);

    public int getTurnCooldown() {
        return turnCooldown;
    }
}
