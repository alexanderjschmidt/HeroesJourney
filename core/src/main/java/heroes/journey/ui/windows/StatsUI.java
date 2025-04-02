package heroes.journey.ui.windows;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.ui.BasicBackground;
import heroes.journey.ui.UI;
import heroes.journey.ui.windows.stats.EquipmentDisplay;
import heroes.journey.ui.windows.stats.InventoryDisplay;
import heroes.journey.ui.windows.stats.QuestsDisplay;
import heroes.journey.ui.windows.stats.StatsDisplay;

public class StatsUI extends Stack {

    private final InventoryDisplay inventoryDisplay;
    private final EquipmentDisplay equipmentDisplay;
    private final QuestsDisplay questDisplay;
    private final StatsDisplay statsDisplay;

    public StatsUI() {
        super();
        UI background = new BasicBackground();
        this.add(background);

        inventoryDisplay = new InventoryDisplay();
        equipmentDisplay = new EquipmentDisplay();
        questDisplay = new QuestsDisplay();
        statsDisplay = new StatsDisplay();

        Table quadrants = new Table();
        quadrants.defaults().expand().fill();
        quadrants.add(statsDisplay);
        // TODO add title to this, maybe make its own display that is a table with the title as the top row
        quadrants.add(inventoryDisplay);
        quadrants.row();
        quadrants.add(questDisplay);
        quadrants.add(equipmentDisplay);

        this.add(quadrants);
        this.setVisible(false);
    }

    public void setEntity(Entity entity) {
        inventoryDisplay.setEntity(entity);
        equipmentDisplay.setEntity(entity);
        questDisplay.setEntity(entity);
        statsDisplay.setEntity(entity);
    }

    public void handleInputs() {
        inventoryDisplay.handleInput();
    }

}
