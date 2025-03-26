package heroes.journey.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.RenderComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.items.ItemInterface;

import java.util.List;

public class StatsUI extends UI {

    private Entity entity;

    private boolean toggled;

    public StatsUI() {
        super(11, 3, 27, 20);
        toggled = false;
        this.setVisible(false);
    }

    @Override
    public void update() {
        Cursor cursor = HUD.get().getCursor();
        this.entity = GameState.global().getEntities().get(cursor.x, cursor.y);
        if (entity == null) {
            toggled = false;
            this.setVisible(false);
            return;
        }
        toggled = !toggled;
        this.setVisible(toggled);
    }

    @Override
    public void drawUI(Batch batch, float parentAlpha) {
        if (entity == null)
            return;
        drawStats(batch, parentAlpha);
        drawInventory(batch, parentAlpha);
    }

    private void drawStats(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(entity);
        batch.draw(renderComponent.getSprite(), getX() + 12, getY() + 24 + HUD.FONT_SIZE * 9, GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);

        StatsComponent statsComponent = StatsComponent.get(entity);
        drawText(batch, "===== Stats =====", 0, 0);
        drawText(batch, "Health: " + statsComponent.getHealth() + "/" + StatsComponent.MAX_HEALTH, 0, 1);
        drawText(batch, "Mana: " + statsComponent.getMana() + "/" + StatsComponent.MAX_MANA, 0, 2);
        drawText(batch, "Move: " + statsComponent.getMoveDistance(), 0, 3);
        drawText(batch, "Body: " + statsComponent.getBody(), 0, 4);
        drawText(batch, "Mind: " + statsComponent.getMind(), 0, 5);
        drawText(batch, "Fame: " + statsComponent.getFame(), 0, 6);
    }

    private void drawInventory(Batch batch, float parentAlpha) {
        InventoryComponent inventoryComponent = InventoryComponent.get(entity);

        drawText(batch, "===== Inventory =====", 13, 0);
        List<ItemInterface> items = inventoryComponent.keySet().stream().toList();
        for (int i = 0; i < items.size(); i++) {
            ItemInterface item = items.get(i);
            drawText(batch, item.toString(), 13, i + 1);
            drawText(batch, "x" + inventoryComponent.get(item), 22, i + 1);
        }
    }

}
