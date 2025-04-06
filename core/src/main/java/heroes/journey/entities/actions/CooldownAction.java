package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.CooldownComponent;
import heroes.journey.components.utils.Utils;

public class CooldownAction extends Action {

    // TODO This could get folded into Action with a default of 1 and false
    private final int turnCooldown;
    private final boolean factionCooldown;

    private CooldownAction(Builder builder) {
        super(builder);
        this.turnCooldown = builder.turnCooldown;
        this.factionCooldown = builder.factionCooldown;
    }

    @Override
    public boolean requirementsMet(GameState gameState, Entity entity) {
        CooldownComponent cooldownComponent;
        if (factionCooldown) {
            Entity faction = Utils.getLocationsFaction(gameState, entity);
            cooldownComponent = CooldownComponent.get(faction);
        } else {
            cooldownComponent = CooldownComponent.get(entity);
        }
        return !cooldownComponent.containsKey(this) && requirementsMet.test(gameState, entity);
    }

    @Override
    public String onSelect(GameState gameState, Entity entity) {
        CooldownComponent cooldownComponent;
        if (factionCooldown) {
            Entity faction = Utils.getLocationsFaction(gameState, entity);
            cooldownComponent = CooldownComponent.get(faction);
        } else {
            cooldownComponent = CooldownComponent.get(entity);
        }
        cooldownComponent.put(this, turnCooldown);
        return onSelect.apply(gameState, entity);
    }

    public int getTurnCooldown() {
        return turnCooldown;
    }

    public static class Builder extends Action.ActionBuilder<Builder, CooldownAction> {

        private int turnCooldown;
        private boolean factionCooldown = false;

        public Builder turnCooldown(int turnCooldown) {
            this.turnCooldown = turnCooldown;
            return this;
        }

        public Builder factionCooldown(boolean factionCooldown) {
            this.factionCooldown = factionCooldown;
            return this;
        }

        public CooldownAction build() {
            return new CooldownAction(this);
        }
    }
}
