package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.CooldownComponent;

public abstract class CooldownAction extends Action {

    private final int turnCooldown;

    public CooldownAction(String name, int manaCost, boolean teamSkill, int turnCooldown) {
        super(name, manaCost, teamSkill);
        this.turnCooldown = turnCooldown;
    }

    public CooldownAction(String name, int manaCost, int turnCooldown) {
        super(name, manaCost);
        this.turnCooldown = turnCooldown;
    }

    public CooldownAction(String name, boolean teamSkill, int turnCooldown) {
        super(name, teamSkill);
        this.turnCooldown = turnCooldown;
    }

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
    public void onSelect(GameState gameState, Entity entity) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        cooldownComponent.put(this, turnCooldown);
        onSelectHelper(gameState, entity);
    }

    public abstract boolean requirementsMetHelper(GameState gameState, Entity selected);

    public abstract void onSelectHelper(GameState gameState, Entity selected);

    public int getTurnCooldown() {
        return turnCooldown;
    }
}
