package heroes.journey.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.components.CooldownComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;

public class ActionDetailUI extends UI {

    private Action action;
    private CooldownComponent cooldownComponent;

    public ActionDetailUI() {
        super(0, 0, 8, 13, false, true);
        this.setVisible(false);
    }

    @Override
    public void update() {
        this.setVisible(action != null);
    }

    @Override
    public void drawUI(Batch batch, float parentAlpha) {
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
        Entity selected = HUD.get().getCursor().getSelected();
        if (selected != null)
            cooldownComponent = CooldownComponent.get(selected);
    }
}
