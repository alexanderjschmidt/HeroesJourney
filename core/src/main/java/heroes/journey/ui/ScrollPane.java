package heroes.journey.ui;

import static heroes.journey.ui.UI.drawText;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.input.KeyManager;

public abstract class ScrollPane<T> extends Widget {

    private List<T> options;
    private int selected = 0;
    private int MAX_HEIGHT = 14;

    public ScrollPane() {
        super();
    }

    public abstract void select();

    public abstract void onHover();

    public void open(List<T> options) {
        this.options = options;
        selected = 0;
        onHover();
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(KeyManager.UP)) {
            decrement();
        } else if (Gdx.input.isKeyJustPressed(KeyManager.DOWN)) {
            increment();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(ResourceManager.get().select, getX() + (0.5f * HUD.FONT_SIZE),
            getY() + getHeight() - ((selected + 1.5f) * HUD.FONT_SIZE));
        for (int i = 0; i < options.size(); i++) {
            drawText(this, batch, options.get(i).toString(), 1, i);
        }
    }

    public T getSelected() {
        return options.get(selected);
    }

    private void increment() {
        if (selected < options.size() - 1) {
            selected++;
        } else {
            selected = 0;
        }
        onHover();
    }

    private void decrement() {
        if (selected > 0) {
            selected--;
        } else {
            selected = options.size() - 1;
        }
        onHover();
    }

}
