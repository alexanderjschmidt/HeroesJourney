package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.utils.input.KeyManager;

public class InventoryDisplay extends ScrollPane<Item> {

    private UUID entityId;
    private InventoryComponent inventoryComponent;

    public InventoryDisplay() {
        super();
        this.offsetY = 1;
    }

    @Override
    public void select() {
        Item selectedItem = getSelected().entry();
        switch (selectedItem.getType()) {
            case Weapon:
            case Armor:
                EquipmentComponent equipment = EquipmentComponent.get(GameState.global().getWorld(),
                    entityId);
                equipment.equip((Item)selectedItem);
                break;
            case Consumable:
                removeItem((Item)selectedItem);
                ConsumableItem c = (ConsumableItem)selectedItem;
                c.consume(GameState.global(), entityId);
                break;
        }
    }

    @Override
    public void onHover() {

    }

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
        inventoryComponent = InventoryComponent.get(GameState.global().getWorld(), entityId);
        open(inventoryComponent.getInventory()
            .keySet()
            .stream()
            .map(key -> new ScrollPaneEntry<>(key, true))
            .toList());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //UI.drawText(this, batch, "===== Inventory =====", 0, 0);
        super.draw(batch, parentAlpha);
    }

    @Override
    public String getText(Item item) {
        return inventoryComponent.toString(item);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            selectWrapper();
        }
    }

    private void removeItem(Item item) {
        inventoryComponent.remove(item, 1);
        if (!inventoryComponent.getInventory().containsKey(item)) {
            if (selected > 0) {
                selected--;
            }
            updateList(inventoryComponent.getInventory()
                .keySet()
                .stream()
                .map(key -> new ScrollPaneEntry<>(key, true))
                .toList());
        }
    }
}
