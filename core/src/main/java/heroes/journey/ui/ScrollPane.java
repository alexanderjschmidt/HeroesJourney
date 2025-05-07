package heroes.journey.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.input.KeyManager;

public abstract class ScrollPane<T> extends Table {

    private List<ScrollPaneEntry<T>> options;
    public int selected = 0;
    private int MAX_HEIGHT = 14;
    public Image selector;
    public static int columns = 2;

    public ScrollPane() {
        super();
        defaults().left().pad(2.5f).fill();
        selector = new Image(new TextureRegionDrawable(ResourceManager.get().select));
        options = new ArrayList<>();
    }

    public void selectWrapper() {
        if (options.get(selected).isSelectable())
            select();
    }

    public abstract void select();

    public abstract void onHover();

    public void open(List<ScrollPaneEntry<T>> options) {
        selected = 0;
        updateList(options);
    }

    public void updateList(List<ScrollPaneEntry<T>> options) {
        this.options = options;
        this.clear();
        for (int i = 0; i < options.size(); i++) {
            if (i == selected) {
                this.add(selector).expandX().fill();
            } else {
                this.add().expandX().fill();
            }
            // TODO add extra column for count
            Label text = new Label(getText(options.get(i).entry()), ResourceManager.get().skin);
            text.setColor(options.get(i).isSelectable() ? Color.WHITE : Color.GRAY);
            this.add(text).expandX().fill().row();
        }
        this.add().expandY();
        onHover();
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(KeyManager.UP)) {
            decrement();
        } else if (Gdx.input.isKeyJustPressed(KeyManager.DOWN)) {
            increment();
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            selectWrapper();
        }
    }

    public String getText(T option) {
        return option.toString();
    }

    public ScrollPaneEntry<T> getSelected() {
        return options.get(selected);
    }

    private void increment() {
        if (selected < options.size() - 1) {
            selected++;
        } else {
            selected = 0;
        }
        moveSelectorToRow();
        onHover();
    }

    private void decrement() {
        if (selected > 0) {
            selected--;
        } else {
            selected = options.size() - 1;
        }
        moveSelectorToRow();
        onHover();
    }

    private void moveSelectorToRow() {
        // First, remove selector from wherever it is
        selector.remove();

        // Find the cell at column 0, row `rowIndex`
        int cellIndex = selected * columns; // Each row has 2 cells: [selector, label]
        Cell<?> targetCell = getCells().get(cellIndex);

        // Add the selector to that cell
        targetCell.setActor(selector).expandX().fill();
    }

}

