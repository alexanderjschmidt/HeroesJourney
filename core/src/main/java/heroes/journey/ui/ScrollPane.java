package heroes.journey.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.registries.Registrable;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.input.KeyManager;

public abstract class ScrollPane<T extends Registrable> extends Table {

    private final List<Label> optionLabels = new ArrayList<>();
    private List<ScrollPaneEntry<T>> options;
    public int selected = 0;
    private int MAX_HEIGHT = 14;

    public ScrollPane() {
        super();
        defaults().left().pad(2.5f).fill();

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
            text.setColor(options.get(i).isSelectable() ? Color.WHITE : Color.DARK_GRAY);
            add(text).expandX().left().pad(2.5f);

            optionLabels.add(text);
        }

        pack();
        moveSelectorToRow();
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
        return option.getName();
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
        for (Label label : optionLabels) {
            label.clearActions();
            label.addAction(Actions.moveTo(0, label.getY(), 0.15f, Interpolation.fade));
        }

        Label selectedLabel = optionLabels.get(selected);
        selectedLabel.clearActions();
        selectedLabel.addAction(Actions.moveTo(10f, selectedLabel.getY(), 0.15f, Interpolation.fade));
    }

}

