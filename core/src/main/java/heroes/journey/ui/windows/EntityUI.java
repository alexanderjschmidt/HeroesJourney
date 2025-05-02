package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class EntityUI extends UI {

    public static final int BAR_WIDTH = HUD.FONT_SIZE * 8;
    public static final int BAR_HEIGHT = HUD.FONT_SIZE;

    public EntityUI() {
        super();
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        Integer entityId = HUD.get().getCursor().getHover();
        if (entityId == null)
            return;
        StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
        String name = NamedComponent.get(GameState.global().getWorld(), entityId, "---");

        if (statsComponent != null) {
            String health = statsComponent.getHealth() + "/" + ((int)(StatsComponent.MAX_HEALTH));
            String mana = statsComponent.getMana() + "/" + ((int)StatsComponent.MAX_MANA);
            String stamina = statsComponent.getStamina() + "/" + ((int)StatsComponent.MAX_STAMINA);
            // replace with labels

            batch.draw(ResourceManager.get(LoadTextures.UI)[3][0], getX() + HUD.FONT_SIZE,
                12 + getY() + ((2) * HUD.FONT_SIZE),
                BAR_WIDTH * ((float)statsComponent.getHealth() / StatsComponent.MAX_HEALTH), BAR_HEIGHT);
            batch.draw(ResourceManager.get(LoadTextures.UI)[2][1], getX() + HUD.FONT_SIZE,
                12 + getY() + ((1) * HUD.FONT_SIZE),
                BAR_WIDTH * ((float)statsComponent.getMana() / StatsComponent.MAX_MANA), BAR_HEIGHT);
            batch.draw(ResourceManager.get(LoadTextures.UI)[3][1], getX() + HUD.FONT_SIZE,
                12 + getY() + ((0) * HUD.FONT_SIZE),
                BAR_WIDTH * ((float)statsComponent.getStamina() / StatsComponent.MAX_STAMINA), BAR_HEIGHT);

            drawText(batch, name, 0, 0);
            drawText(batch, "H: " + health, 0, 1);
            drawText(batch, "M: " + mana, 0, 2);
            drawText(batch, "S: " + stamina, 0, 3);
        }
    }

}
