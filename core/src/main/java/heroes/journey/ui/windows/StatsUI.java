package heroes.journey.ui.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.ui.UI;
import heroes.journey.ui.windows.stats.EquipmentDisplay;
import heroes.journey.ui.windows.stats.InventoryDisplay;
import heroes.journey.ui.windows.stats.QuestsDisplay;
import heroes.journey.ui.windows.stats.StatsDisplay;
import heroes.journey.utils.input.KeyManager;

import java.util.List;
import java.util.UUID;

import static heroes.journey.ui.windows.Display.INVENTORY;
import static heroes.journey.ui.windows.Display.STATS;

public class StatsUI extends UI {

    private final EquipmentDisplay equipmentDisplay = new EquipmentDisplay();
    private final InventoryDisplay inventoryDisplay = new InventoryDisplay(equipmentDisplay);
    private final Table inventory = new Table();
    private final QuestsDisplay questDisplay = new QuestsDisplay();
    private final StatsDisplay statsDisplay = new StatsDisplay();

    private final List<Actor> allDisplays = List.of(statsDisplay, questDisplay, inventory);

    private Display display = STATS;

    public StatsUI() {
        super();
        inventory.defaults().expand().fill();
        // TODO add title to this, maybe make its own display that is a table with the title as the top row
        inventory.add(inventoryDisplay);
        inventory.add(equipmentDisplay);

        mainTable.defaults().expand().top().left();

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
        mainTable.clear();
        switch (display) {
            case STATS -> {
                mainTable.add(statsDisplay);
                statsDisplay.setVisible(true);
            }
            case QUESTS -> {
                mainTable.add(questDisplay);
                questDisplay.setVisible(true);
            }
            case INVENTORY -> {
                mainTable.add(inventory);
                inventory.setVisible(true);
            }
        }
    }

    public void setEntity(UUID entityId) {
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

