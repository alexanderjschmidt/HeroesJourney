package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.CooldownComponent;
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
    public ShowAction requirementsMet(GameState gameState, Entity entity) {
        CooldownComponent cooldownComponent;
        if (factionCooldown) {
            Entity faction = Utils.getLocationsFaction(gameState, entity);
            cooldownComponent = CooldownComponent.get(faction);
        } else {
            cooldownComponent = CooldownComponent.get(entity);
        }
        if (cooldownComponent.containsKey(this))
            return ShowAction.NO;
        return requirementsMet.apply(gameState, entity);
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
}
