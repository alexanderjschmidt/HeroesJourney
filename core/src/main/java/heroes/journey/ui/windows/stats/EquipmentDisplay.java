package heroes.journey.ui.windows.stats;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.ui.UI;

public class EquipmentDisplay extends Widget {

    private Entity entity;

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void draw(Batch batch, float parentAlpha) {
        EquipmentComponent equipmentComponent = EquipmentComponent.get(entity);

        UI.drawText(this, batch, "===== Equipment =====", 0, 0);
        
        UI.drawText(this, batch, "Head: " + str(equipmentComponent.getHead()), 0, 1);
        UI.drawText(this, batch, "Chest: " + str(equipmentComponent.getChest()), 0, 2);
        UI.drawText(this, batch, "Legs: " + str(equipmentComponent.getLegs()), 0, 3);
        UI.drawText(this, batch, "Boots: " + str(equipmentComponent.getBoots()), 0, 4);

        UI.drawText(this, batch, "Hand One: " + str(equipmentComponent.getHandOne()), 10, 1);
        UI.drawText(this, batch, "Hand Two: " + str(equipmentComponent.getHandTwo()), 10, 2);
        UI.drawText(this, batch, "Acc One: " + str(equipmentComponent.getAccessoryOne()), 10, 3);
        UI.drawText(this, batch, "Acc Two: " + str(equipmentComponent.getAccessoryTwo()), 10, 4);
    }

    private String str(Object o) {
        return o == null ? "---" : o.toString();
    }
}
