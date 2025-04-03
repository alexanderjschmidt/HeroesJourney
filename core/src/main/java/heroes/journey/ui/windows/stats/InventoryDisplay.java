package heroes.journey.ui.windows.stats;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemInterface;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.UI;
import heroes.journey.utils.input.KeyManager;

public class InventoryDisplay extends ScrollPane<ItemInterface> {

    private Entity entity;
    private InventoryComponent inventoryComponent;

    public InventoryDisplay() {
        super();
        this.offsetY = 1;
    }

    @Override
    public void select() {

    }

    @Override
    public void onHover() {

    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        inventoryComponent = InventoryComponent.get(entity);
        open(inventoryComponent.keySet().stream().toList());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        UI.drawText(this, batch, "===== Inventory =====", 0, 0);
        super.draw(batch, parentAlpha);
    }

    @Override
    public String getText(ItemInterface item) {
        return inventoryComponent.toString(item);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            ItemInterface selectedItem = getSelected();
            switch (selectedItem.getType()) {
                case Weapon:
                case Armor:
                    EquipmentComponent equipment = EquipmentComponent.get(entity);
                    equipment.equip((Item) selectedItem);
                    break;
                case Consumable:
                    removeItem((Item) selectedItem);
                    ConsumableItem c = (ConsumableItem) selectedItem;
                    c.consume().apply(GameState.global(), entity);
                    break;
            }

        }
    }

    private void removeItem(Item item) {
        inventoryComponent.remove(item, 1);
        if (!inventoryComponent.containsKey(item)) {
            if (selected > 0) {
                selected--;
            }
            updateList(inventoryComponent.keySet().stream().toList());
        }
    }
}
