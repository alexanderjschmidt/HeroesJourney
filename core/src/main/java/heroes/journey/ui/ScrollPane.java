package heroes.journey.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.input.KeyManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ScrollPane<T> extends Table {

    private final List<Label> optionLabels = new ArrayList<>();
    private List<ScrollPaneEntry<T>> options;
    public int selected = 0;
    private int MAX_HEIGHT = 14;

    public ScrollPane() {
        super();
        defaults().left().pad(2.5f).fillX();

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
        clearChildren(); // clear only the inner list, not whole layout
        optionLabels.clear();

        for (int i = 0; i < options.size(); i++) {
            row();

            Label text = new Label(getText(options.get(i).entry()), ResourceManager.get().skin);
            text.setWrap(true);
            text.setColor(options.get(i).isSelectable() ? Color.WHITE : Color.DARK_GRAY);
            add(text).expandX().fillX().left().pad(2.5f);

            optionLabels.add(text);
        }
        moveSelectorToRow();
        onHover();
        this.pack();
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

    public abstract String getText(T option);

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
        for (int i = 0; i < optionLabels.size(); i++) {
            Label label = optionLabels.get(i);
            label.clearActions();
            if (i == selected) {
                label.setStyle(ResourceManager.get().skin.get("bold18", Label.LabelStyle.class));
                label.setColor(options.get(i).isSelectable() ? Color.YELLOW : Color.TAN); // Optional: highlight color
            } else {
                label.setStyle(ResourceManager.get().skin.get("default", Label.LabelStyle.class));
                label.setColor(options.get(i).isSelectable() ? Color.WHITE : Color.DARK_GRAY);
            }
        }
    }

}

