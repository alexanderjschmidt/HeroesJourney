package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.ActionInput;
import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.utils.art.ResourceManager;

public class InventoryDisplay extends Table {

    private UUID entityId;
    private EquipmentDisplay equipmentDisplay;
    private InventoryComponent inventoryComponent;
    private final InventoryScroll inventoryScroll;
    private final Label gold, weight;

    public InventoryDisplay(EquipmentDisplay equipmentDisplay) {
        super();
        this.equipmentDisplay = equipmentDisplay;
        Label title = new Label("===== Inventory =====", ResourceManager.get().skin);
        inventoryScroll = new InventoryScroll();

        this.defaults().fill().expandX().left().pad(2.5f);

        Table stats = new Table();
        stats.defaults().fill().expandX().left().pad(2.5f);
        gold = new Label("", ResourceManager.get().skin);
        gold.setAlignment(Align.right);
        weight = new Label("", ResourceManager.get().skin);
        weight.setAlignment(Align.right);
        stats.add(new Label("Gold: ", ResourceManager.get().skin)).left();
        stats.add(gold).right().row();
        stats.add(new Label("Weight: ", ResourceManager.get().skin)).left();
        stats.add(weight).right().row();

        this.add(title).row();
        this.add(inventoryScroll).row();
        this.add().expandY().row();
        this.add(stats).row();
    }

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
        refreshEntity();
    }

    private void refreshEntity() {
        inventoryComponent = InventoryComponent.get(GameState.global().getWorld(), entityId);
        inventoryScroll.open(inventoryComponent.getInventory()
            .keySet()
            .stream()
            .map(key -> new ScrollPaneEntry<>(key, true))
            .toList());
        gold.setText(inventoryComponent.getGold());
        Attributes statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
        weight.setText(inventoryComponent.getWeight() + "/" + StatsUtils.getCarryCapacity(statsComponent));
    }

    public void handleInput() {
        inventoryScroll.handleInput();
    }

    private class InventoryScroll extends ScrollPane<Item> {

        @Override
        public void select() {
            Item selectedItem = getSelected().entry();
            System.out.println(selectedItem);
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
                    c.consume(new ActionInput(GameState.global(), entityId));
                    break;
            }
            equipmentDisplay.refreshEntity();
            refreshEntity();
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
