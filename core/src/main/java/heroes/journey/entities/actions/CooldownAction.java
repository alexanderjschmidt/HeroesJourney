package heroes.journey.entities.actions;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.GameState;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.initializers.utils.Utils;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

import static heroes.journey.registries.Registries.ActionManager;

public abstract class CooldownAction extends Action {
    @NonNull
    @Getter
    private final Integer turnCooldown;
    private final boolean factionCooldown;

    public CooldownAction(String name, String displayName, String description, boolean returnsActionList, Cost cost,
                          Integer turnCooldown, boolean factionCooldown) {
        super(name, displayName, description, returnsActionList, cost);
        this.turnCooldown = turnCooldown;
        this.factionCooldown = factionCooldown;
    }

    public CooldownAction(String name, Integer turnCooldown) {
        this(name, null, "", false, null, turnCooldown, false);
    }

    @Override
    public ShowAction requirementsMet(ActionInput input) {
        PossibleActionsComponent cooldownComponent = getCooldownComponent(input);
        if (cooldownComponent.getCooldowns().containsKey(name))
            return ShowAction.GRAYED;
        return super.internalRequirementsMet(input);
    }

    @Override
    public ActionResult onSelect(ActionInput input, boolean ai) {
        PossibleActionsComponent cooldownComponent = getCooldownComponent(input);
        cooldownComponent.getCooldowns().put(name, turnCooldown);
        return super.onSelect(input, ai);
    }

    private PossibleActionsComponent getCooldownComponent(ActionInput input) {
        PossibleActionsComponent cooldownComponent;
        if (factionCooldown) {
            UUID faction = Utils.getLocation(input);
            cooldownComponent = PossibleActionsComponent.get(input.getGameState().getWorld(), faction);
        } else {
            cooldownComponent = PossibleActionsComponent.get(input.getGameState().getWorld(),
                input.getEntityId());
        }
        return cooldownComponent;
    }

    private Label cooldown;

    @Override
    public void fillCustomContent(Table table, Skin skin) {
        if (cooldown == null) {
            cooldown = new Label("", skin);
        }
        PossibleActionsComponent cooldownComponent = getCooldownComponent(
            new ActionInput(GameState.global(), GameState.global().getCurrentEntity()));
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
