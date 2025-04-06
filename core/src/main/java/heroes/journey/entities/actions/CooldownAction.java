package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.CooldownComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.effects.ConsumerChain;
import heroes.journey.entities.effects.FunctionChain;
import heroes.journey.entities.effects.PredicateChain;

public class CooldownAction extends Action {

    private final int turnCooldown;
    private final boolean factionCooldown;

    private CooldownAction(Builder builder) {
        super(builder);
        this.onHover = builder.onHover.build();
        this.onSelect = builder.onSelect.build();
        this.requirementsMet = builder.requirementsMet.build();
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
        return !cooldownComponent.containsKey(this) && requirementsMet.isTrue(gameState, entity);
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

    public static class Builder extends Action.ActionBuilder<Builder,CooldownAction> {

        private int turnCooldown;
        private boolean factionCooldown = false;
        private final ConsumerChain.Builder<Builder> onHover = new ConsumerChain.Builder<>(this);
        private final FunctionChain.Builder<Builder,String> onSelect = new FunctionChain.Builder<>(this);
        private final PredicateChain.Builder<Builder> requirementsMet = new PredicateChain.Builder<>(this);

        public Builder turnCooldown(int turnCooldown) {
            this.turnCooldown = turnCooldown;
            return this;
        }

        public Builder factionCooldown(boolean factionCooldown) {
            this.factionCooldown = factionCooldown;
            return this;
        }

        public ConsumerChain.Builder<Builder> onHover() {
            return onHover;
        }

        public FunctionChain.Builder<Builder,String> onSelect() {
            return onSelect;
        }

        public PredicateChain.Builder<Builder> requirementsMet() {
            return requirementsMet;
        }

        public CooldownAction build() {
            return new CooldownAction(this);
        }
    }
}
