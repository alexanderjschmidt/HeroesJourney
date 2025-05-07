package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.utils.art.ResourceManager;

public class EquipmentDisplay extends Table {

    private final Label head, chest, legs, boots;
    private final Label handOne, handTwo, accessoryOne, accessoryTwo;

    public EquipmentDisplay() {
        Label title = new Label("===== Equipment =====", ResourceManager.get().skin);
        head = new Label("", ResourceManager.get().skin);
        chest = new Label("", ResourceManager.get().skin);
        legs = new Label("", ResourceManager.get().skin);
        boots = new Label("", ResourceManager.get().skin);
        handOne = new Label("", ResourceManager.get().skin);
        handTwo = new Label("", ResourceManager.get().skin);
        accessoryOne = new Label("", ResourceManager.get().skin);
        accessoryTwo = new Label("", ResourceManager.get().skin);

        this.defaults().fill().expandX().left().pad(2.5f);

        this.add(title).row();

        Table gear = new Table();
        gear.defaults().fill().expandX().left().pad(2.5f);
        gear.add(head);
        gear.add(handOne).row();
        gear.add(chest);
        gear.add(handTwo).row();
        gear.add(legs);
        gear.add(accessoryOne).row();
        gear.add(boots);
        gear.add(accessoryTwo).row();

        this.add(gear).row();
        this.add().expandY().row();
    }

    public void setEntity(UUID entityId) {
        EquipmentComponent equipmentComponent = EquipmentComponent.get(GameState.global().getWorld(),
            entityId);

        head.setText("Head: " + str(equipmentComponent.head()));
        chest.setText("Chest: " + str(equipmentComponent.chest()));
        legs.setText("Legs: " + str(equipmentComponent.legs()));
        boots.setText("Boots: " + str(equipmentComponent.boots()));

        handOne.setText("Hand One: " + str(equipmentComponent.handOne()));
        handTwo.setText("Hand Two: " + str(equipmentComponent.handTwo()));
        accessoryOne.setText("Acc One: " + str(equipmentComponent.accessoryOne()));
        accessoryTwo.setText("Acc Two: " + str(equipmentComponent.accessoryTwo()));
    }

    private String str(Object o) {
        return o == null ? "---" : o.toString();
    }
}
