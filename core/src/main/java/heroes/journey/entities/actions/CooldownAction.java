package heroes.journey.entities.actions;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.CooldownComponent;
import heroes.journey.components.utils.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CooldownAction extends Action {

    // TODO This could get folded into Action with a default of 1 and false
    @NonNull @Getter private final Integer turnCooldown;
    @Builder.Default private final boolean factionCooldown = false;

    @Override
    public ShowAction requirementsMet(GameState gameState, Integer entityId) {
        CooldownComponent cooldownComponent;
        if (factionCooldown) {
            Integer faction = Utils.getLocationsFaction(gameState, entityId);
            cooldownComponent = CooldownComponent.get(gameState.getWorld(), faction);
        } else {
            cooldownComponent = CooldownComponent.get(gameState.getWorld(), entityId);
        }
        if (cooldownComponent.getCooldowns().containsKey(name))
            return ShowAction.GRAYED;
        return requirementsMet.apply(gameState, entityId);
    }

    @Override
    public String onSelect(GameState gameState, Integer entityId) {
        CooldownComponent cooldownComponent;
        if (factionCooldown) {
            Integer faction = Utils.getLocationsFaction(gameState, entityId);
            cooldownComponent = CooldownComponent.get(gameState.getWorld(), faction);
        } else {
            cooldownComponent = CooldownComponent.get(gameState.getWorld(), entityId);
        }
        cooldownComponent.getCooldowns().put(name, turnCooldown);
        return onSelect.apply(gameState, entityId);
    }

    public CooldownAction register() {
        return (CooldownAction)ActionManager.register(this);
    }
}
