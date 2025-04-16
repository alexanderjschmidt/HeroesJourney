package heroes.journey.ui.windows;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.components.StatsComponent;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class EntityUI extends UI {

    public static final int HEALTH_WIDTH = HUD.FONT_SIZE * 8;
    public static final int HEALTH_HEIGHT = HUD.FONT_SIZE;

    public boolean hover = true;

    public EntityUI() {
        super();
    }

    public void watchSelected() {
        hover = false;
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        Entity entity;
        if (hover && HUD.get().getCursor().getHover() != HUD.get().getCursor().getSelected()) {
            entity = HUD.get().getCursor().getHover();
        } else if (!hover) {
            entity = HUD.get().getCursor().getSelected();
        } else {
            entity = null;
        }

        if (entity == null)
            return;
        StatsComponent statsComponent = StatsComponent.get(entity);

        if (statsComponent != null) {
            String health = statsComponent.getHealth() + "/" + ((int)(StatsComponent.MAX_HEALTH));
            String mana = statsComponent.getMana() + "/" + ((int)StatsComponent.MAX_MANA);
            // replace with labels

            batch.draw(ResourceManager.get(LoadTextures.UI)[3][0], getX() + HUD.FONT_SIZE,
                12 + getY() + ((2) * HUD.FONT_SIZE),
                HEALTH_WIDTH * ((float)statsComponent.getHealth() / StatsComponent.MAX_HEALTH),
                HEALTH_HEIGHT);
            batch.draw(ResourceManager.get(LoadTextures.UI)[2][1], getX() + HUD.FONT_SIZE,
                12 + getY() + ((1) * HUD.FONT_SIZE),
                HEALTH_WIDTH * ((float)statsComponent.getMana() / StatsComponent.MAX_MANA), HEALTH_HEIGHT);

            drawText(batch, "Health: " + health, 0, 1);
            drawText(batch, "Mana: " + mana, 0, 2);
        }
    }

}
