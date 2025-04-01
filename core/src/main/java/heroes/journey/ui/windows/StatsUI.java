package heroes.journey.ui.windows;

import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.RenderComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.entities.items.ItemInterface;
import heroes.journey.entities.quests.Quest;
import heroes.journey.ui.Cursor;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;

public class StatsUI extends UI {

    private Entity entity;

    private boolean toggled;

    public StatsUI() {
        super();
        toggled = false;
        this.setVisible(false);
    }

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
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        if (entity == null)
            return;
        drawStats(batch, parentAlpha);
        drawQuests(batch, parentAlpha);
        drawInventory(batch, parentAlpha);
        drawEquipment(batch, parentAlpha);
    }

    private void drawStats(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(entity);
        batch.draw(renderComponent.getSprite(), getX() + 12, getY() + 24 + HUD.FONT_SIZE * 9,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);

        StatsComponent statsComponent = StatsComponent.get(entity);
        drawText(batch, "===== Stats =====", 0, 0);
        drawText(batch, "Health: " + statsComponent.getHealth() + "/" + StatsComponent.MAX_HEALTH, 0, 1);
        drawText(batch, "Mana: " + statsComponent.getMana() + "/" + StatsComponent.MAX_MANA, 0, 2);
        drawText(batch, "Move: " + statsComponent.getMoveDistance(), 0, 3);
        drawText(batch, "Body: " + statsComponent.getBody(), 0, 4);
        drawText(batch, "Mind: " + statsComponent.getMind(), 0, 5);
        drawText(batch, "Fame: " + statsComponent.getFame(), 0, 6);
    }

    private void drawQuests(Batch batch, float parentAlpha) {
        QuestsComponent questsComponent = QuestsComponent.get(entity);

        drawText(batch, "===== Quests =====", 0, 12);
        for (int i = 0; i < questsComponent.size(); i++) {
            Quest quest = questsComponent.get(i);
            drawText(batch, quest.toString(), 0, i + 1 + 12);
        }
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

    private void drawEquipment(Batch batch, float parentAlpha) {
        EquipmentComponent equipmentComponent = EquipmentComponent.get(entity);

        drawText(batch, "===== Equipment =====", 13, 12);
        drawText(batch, "Head: " + str(equipmentComponent.getHead()), 13, 13);
        drawText(batch, "Chest: " + str(equipmentComponent.getChest()), 13, 14);
        drawText(batch, "Legs: " + str(equipmentComponent.getLegs()), 13, 15);
        drawText(batch, "Boots: " + str(equipmentComponent.getBoots()), 13, 16);
    }

    private String str(Object o) {
        return o == null ? "---" : o.toString();
    }

}
