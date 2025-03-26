package heroes.journey.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.components.StatsComponent;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMaps;

public class EntityUI extends UI {

    public static final int HEALTH_WIDTH = HUD.FONT_SIZE * 8;
    public static final int HEALTH_HEIGHT = HUD.FONT_SIZE;

    public boolean hover = true;

    private Entity entity;

    public EntityUI() {
        super(0, 3, 8, 4);
    }

    @Override
    public void update() {
        if (hover)
            entity = HUD.get().getCursor().getHover();
        else
            entity = HUD.get().getCursor().getSelected();
    }

    public void watchSelected() {
        hover = false;
    }

    @Override
    public void drawUI(Batch batch, float parentAlpha) {
        if (entity == null)
            return;
        StatsComponent statsComponent = StatsComponent.get(entity);

        if (statsComponent != null) {
            String health = statsComponent.getHealth() + "/" + ((int) (StatsComponent.MAX_HEALTH));
            String mana = statsComponent.getMana() + "/" + ((int) StatsComponent.MAX_MANA);
            // replace with labels

            batch.draw(ResourceManager.get(TextureMaps.UI)[3][0], getX() + HUD.FONT_SIZE,
                12 + getY() + ((2) * HUD.FONT_SIZE),
                HEALTH_WIDTH * (statsComponent.getHealth() / StatsComponent.MAX_HEALTH), HEALTH_HEIGHT);
            batch.draw(ResourceManager.get(TextureMaps.UI)[2][1], getX() + HUD.FONT_SIZE,
                12 + getY() + ((1) * HUD.FONT_SIZE),
                HEALTH_WIDTH * (statsComponent.getMana() / StatsComponent.MAX_MANA), HEALTH_HEIGHT);

            drawText(batch, "Health: " + health, 0, 1);
            drawText(batch, "Mana: " + mana, 0, 2);
        }
    }

}
