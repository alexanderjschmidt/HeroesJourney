package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.utils.art.ResourceManager;

public class InventoryDisplay extends Table {

    private UUID entityId;
    private InventoryComponent inventoryComponent;
    private final InventoryScroll inventoryScroll;

    public InventoryDisplay() {
        super();
        Label title = new Label("===== Inventory =====", ResourceManager.get().skin);
        inventoryScroll = new InventoryScroll();

        this.defaults().fill().expandX().left().pad(2.5f);

        this.add(title).row();
        this.add(inventoryScroll);
        this.add().expandY().row();
    }

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
        inventoryComponent = InventoryComponent.get(GameState.global().getWorld(), entityId);
        inventoryScroll.open(inventoryComponent.getInventory()
            .keySet()
            .stream()
            .map(key -> new ScrollPaneEntry<>(key, true))
            .toList());
    }

    public void handleInput() {
        inventoryScroll.handleInput();
    }

    private class InventoryScroll extends ScrollPane<Item> {

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

        @Override
        public void onHover() {

        }

        @Override
        public String getText(Item item) {
            return inventoryComponent.toString(item);
        }
    }
}
