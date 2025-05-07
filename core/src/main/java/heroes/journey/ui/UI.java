package heroes.journey.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.utils.art.ResourceManager;

public abstract class UI extends Table {

    public Table mainTable = new Table();

    public UI() {
        super();
        this.addAction(Actions.fadeIn(.5f, Interpolation.pow5In));

        mainTable.defaults().left().pad(2.5f).fill();
        this.add(mainTable).pad(HUD.FONT_SIZE).center().fill().expand().row();
    }

    public void update() {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update();
        ResourceManager.get().menu.draw(batch, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    @Override
    public void layout() {
        super.layout(); // this triggers layout on children
        setSize(getWidth(), getHeight()); // Ensure it respects its allocated space
    }

}
