package heroes.journey.entities.actions;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.GameState;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
public class CooldownAction extends Action {

    // TODO This could get folded into Action with a default of 1 and false
    @NonNull
    @Getter
    private final Integer turnCooldown;
    @Builder.Default
    private final boolean factionCooldown = false;

    @Override
    public ShowAction requirementsMet(GameState gameState, UUID entityId) {
        PossibleActionsComponent cooldownComponent = getCooldownComponent(gameState, entityId);
        if (cooldownComponent.getCooldowns().containsKey(name))
            return ShowAction.GRAYED;
        return requirementsMet.apply(gameState, entityId);
    }

    @Override
    public ActionResult onSelect(GameState gameState, UUID entityId) {
        PossibleActionsComponent cooldownComponent = getCooldownComponent(gameState, entityId);
        cooldownComponent.getCooldowns().put(name, turnCooldown);
        return onSelect.apply(gameState, entityId);
    }

    private PossibleActionsComponent getCooldownComponent(GameState gameState, UUID entityId) {
        PossibleActionsComponent cooldownComponent;
        if (factionCooldown) {
            UUID faction = Utils.getLocation(gameState, entityId);
            cooldownComponent = PossibleActionsComponent.get(gameState.getWorld(), faction);
        } else {
            cooldownComponent = PossibleActionsComponent.get(gameState.getWorld(), entityId);
        }
        return cooldownComponent;
    }

    private Label cooldown;

    @Override
    public void fillCustomContent(Table table, Skin skin) {
        if (cooldown == null) {
            cooldown = new Label("", skin);
        }
        PossibleActionsComponent cooldownComponent = getCooldownComponent(GameState.global(),
            GameState.global().getCurrentEntity());
        Integer cooldownVal = cooldownComponent.getCooldowns().get(this.name);
        cooldownVal = cooldownVal == null ? turnCooldown : (turnCooldown - cooldownVal - 1);
        cooldown.setText(cooldownVal + "/" + turnCooldown);
        table.add(cooldown).fill().row();
        super.fillCustomContent(table, skin);
    }

    public CooldownAction register() {
        return (CooldownAction) ActionManager.register(this);
    }
}
