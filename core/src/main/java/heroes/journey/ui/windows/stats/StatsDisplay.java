package heroes.journey.ui.windows.stats;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import heroes.journey.GameCamera;
import heroes.journey.components.RenderComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;

public class StatsDisplay extends Widget {

    private Entity entity;

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void draw(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(entity);
        batch.draw(renderComponent.getSprite(), getX() + HUD.FONT_SIZE, getY() + HUD.FONT_SIZE,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);

        StatsComponent statsComponent = StatsComponent.get(entity);
        UI.drawText(this, batch, "===== Stats =====", 0, 0);
        UI.drawText(this, batch, "Health: " + statsComponent.getHealth() + "/" + StatsComponent.MAX_HEALTH, 0,
            1);
        UI.drawText(this, batch, "Mana: " + statsComponent.getMana() + "/" + StatsComponent.MAX_MANA, 0, 2);
        UI.drawText(this, batch, "Move: " + statsComponent.getMoveDistance(), 0, 3);
        UI.drawText(this, batch, "Body: " + statsComponent.getBody(), 0, 4);
        UI.drawText(this, batch, "Mind: " + statsComponent.getMind(), 0, 5);
        UI.drawText(this, batch, "Fame: " + statsComponent.getFame(), 0, 6);
    }
}
