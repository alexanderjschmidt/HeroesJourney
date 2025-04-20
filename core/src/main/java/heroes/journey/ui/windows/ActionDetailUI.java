package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.CooldownComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;

public class ActionDetailUI extends UI {

    private Action action;
    private CooldownComponent cooldownComponent;

    public ActionDetailUI() {
        super();
        this.setVisible(false);
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        String name = action + "";
        drawText(batch, name, 0, 0);
        if (action instanceof CooldownAction cooldownAction) {
            drawCooldownAction(batch, cooldownAction);
        }
    }

    public void drawCooldownAction(Batch batch, CooldownAction action) {
        String cooldown = "None";
        cooldown = action.getTurnCooldown() + " Turns";
        drawText(batch, "Cooldown: " + cooldown, 0, 1);
    }

    public void setAction(Action action) {
        this.action = action;
        Integer selected = HUD.get().getCursor().getSelected();
        if (selected != null)
            cooldownComponent = CooldownComponent.get(GameState.global().getWorld(), selected);
    }
}
