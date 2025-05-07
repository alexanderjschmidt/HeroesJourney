package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;

public class EquipmentDisplay extends Widget {

    private UUID entityId;

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
    }

    public void draw(Batch batch, float parentAlpha) {
        EquipmentComponent equipmentComponent = EquipmentComponent.get(GameState.global().getWorld(),
            entityId);
/*
        UI.drawText(this, batch, "===== Equipment =====", 0, 0);

        UI.drawText(this, batch, "Head: " + str(equipmentComponent.head()), 0, 1);
        UI.drawText(this, batch, "Chest: " + str(equipmentComponent.chest()), 0, 2);
        UI.drawText(this, batch, "Legs: " + str(equipmentComponent.legs()), 0, 3);
        UI.drawText(this, batch, "Boots: " + str(equipmentComponent.boots()), 0, 4);

        UI.drawText(this, batch, "Hand One: " + str(equipmentComponent.handOne()), 10, 1);
        UI.drawText(this, batch, "Hand Two: " + str(equipmentComponent.handTwo()), 10, 2);
        UI.drawText(this, batch, "Acc One: " + str(equipmentComponent.accessoryOne()), 10, 3);
        UI.drawText(this, batch, "Acc Two: " + str(equipmentComponent.accessoryTwo()), 10, 4);*/
    }

    private String str(Object o) {
        return o == null ? "---" : o.toString();
    }
}
