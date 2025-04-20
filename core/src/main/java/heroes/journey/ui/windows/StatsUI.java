package heroes.journey.ui.windows;

import static heroes.journey.ui.windows.Display.INVENTORY;
import static heroes.journey.ui.windows.Display.STATS;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.ui.BasicBackground;
import heroes.journey.ui.UI;
import heroes.journey.ui.windows.stats.EquipmentDisplay;
import heroes.journey.ui.windows.stats.InventoryDisplay;
import heroes.journey.ui.windows.stats.QuestsDisplay;
import heroes.journey.ui.windows.stats.StatsDisplay;
import heroes.journey.utils.input.KeyManager;

public class StatsUI extends Stack {

    private final InventoryDisplay inventoryDisplay = new InventoryDisplay();
    private final EquipmentDisplay equipmentDisplay = new EquipmentDisplay();
    private final QuestsDisplay questDisplay = new QuestsDisplay();
    private final StatsDisplay statsDisplay = new StatsDisplay();

    private final List<Actor> allDisplays = List.of(statsDisplay, questDisplay, inventoryDisplay,
        equipmentDisplay);

    private Display display = STATS;

    public StatsUI() {
        super();
        UI background = new BasicBackground();
        this.add(background);

        Table inventory = new Table();
        inventory.defaults().expand().fill();
        // TODO add title to this, maybe make its own display that is a table with the title as the top row
        inventory.add(inventoryDisplay);
        inventory.add(equipmentDisplay);

        this.add(statsDisplay);
        this.add(questDisplay);
        this.add(inventory);
        setVisibility();
        this.setVisible(false);
    }

    public void updatePanel(Display display) {
        this.display = display;
        setVisibility();
    }

    public void togglePanel() {
        Display[] values = Display.values();
        display = values[(display.ordinal() + 1) % values.length];
        setVisibility();
    }

    private void setVisibility() {
        allDisplays.forEach(actor -> actor.setVisible(false));
        switch (display) {
            case STATS -> statsDisplay.setVisible(true);
            case QUESTS -> questDisplay.setVisible(true);
            case INVENTORY -> {
                inventoryDisplay.setVisible(true);
                equipmentDisplay.setVisible(true);
            }
        }
    }

    public void setEntity(Integer entityId) {
        inventoryDisplay.setEntity(entityId);
        equipmentDisplay.setEntity(entityId);
        questDisplay.setEntity(entityId);
        statsDisplay.setEntity(entityId);
    }

    public void handleInputs() {
        if (display == INVENTORY) {
            inventoryDisplay.handleInput();
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.NEXT_CHARACTER)) {
            togglePanel();
        }
    }

    public Display display() {
        return display;
    }
}

