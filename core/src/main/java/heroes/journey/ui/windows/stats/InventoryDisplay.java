package heroes.journey.ui.windows.stats;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.components.InventoryComponent;
import heroes.journey.entities.items.ItemInterface;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.UI;

public class InventoryDisplay extends ScrollPane<ItemInterface> {

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
}
