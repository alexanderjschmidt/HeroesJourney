package heroes.journey.ui.windows.stats;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;

import java.util.UUID;

public class StatsDisplay extends Widget {

    private UUID entityId;

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
    }

    public void draw(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(GameState.global().getWorld(), entityId);
        batch.draw(renderComponent.sprite(), getX() + HUD.FONT_SIZE, getY() + HUD.FONT_SIZE,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);

        StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
        UI.drawText(this, batch, "===== Stats =====", 0, 0);
        UI.drawText(this, batch, "Health: " + statsComponent.getHealth() + "/" + StatsComponent.MAX_HEALTH, 0,
            1);
        UI.drawText(this, batch, "Mana: " + statsComponent.getMana() + "/" + StatsComponent.MAX_MANA, 0, 2);
        UI.drawText(this, batch, "Move: " + statsComponent.getMoveDistance(), 0, 3);
        UI.drawText(this, batch, "Body: " + statsComponent.getBody(), 0, 4);
        UI.drawText(this, batch, "Mind: " + statsComponent.getMind(), 0, 5);
    }
}
