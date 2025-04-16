package heroes.journey.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;

import heroes.journey.utils.art.ResourceManager;

public abstract class UI extends Widget {

    public UI() {
        this.addAction(Actions.fadeIn(.5f, Interpolation.pow5In));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor().r, this.getColor().g, this.getColor().b, this.getColor().a);
        ResourceManager.get().font24.setColor(this.getColor().r, this.getColor().g, this.getColor().b,
            this.getColor().a);
        ResourceManager.get().menu.draw(batch, getX(), getY(), getWidth(), getHeight());

        drawAndUpdate(batch, parentAlpha);

        ResourceManager.get().font24.setColor(Color.WHITE);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void layout() {
        setSize(getWidth(), getHeight()); // Ensure it respects its allocated space
    }

    public abstract void drawAndUpdate(Batch batch, float parentAlpha);

    public void drawText(Batch batch, String text, int x, int y) {
        drawText(this, batch, text, x, y);
    }

    public static void drawText(Actor actor, Batch batch, String text, int x, int y) {
        ResourceManager.get().font24.draw(batch, text, actor.getX() + ((x + 1) * HUD.FONT_SIZE),
            actor.getY() + actor.getHeight() - ((y + 0.8f) * HUD.FONT_SIZE));
    }

    public static void drawTextWrap(Actor actor, Batch batch, String text, int x, int y) {
        ResourceManager.get().font12.draw(batch, text, actor.getX() + ((x + 1) * HUD.FONT_SIZE),
            actor.getY() + actor.getHeight() - ((y + 0.8f) * HUD.FONT_SIZE), actor.getWidth(), Align.center,
            true);
    }

}
