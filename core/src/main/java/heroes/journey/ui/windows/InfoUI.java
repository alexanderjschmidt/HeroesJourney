package heroes.journey.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.ui.BasicBackground;
import heroes.journey.ui.HUD;
import heroes.journey.ui.InfoProvider;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class InfoUI extends Stack {

    private final Label titleLabel;
    private final Label bodyLabel;
    private final Table contentTable;

    public InfoUI() {
        super();
        UI background = new BasicBackground();
        this.add(background);
        this.titleLabel = new Label("", ResourceManager.get().skin);
        this.titleLabel.setWrap(true);
        this.bodyLabel = new Label("", ResourceManager.get().skin);
        this.bodyLabel.setWrap(true);
        this.contentTable = new Table();

        Table mainTable = new Table();

        mainTable.add(titleLabel).left().pad(2.5f).fill().row();
        mainTable.add(contentTable).left().pad(2.5f).fill().row();
        mainTable.add(bodyLabel).left().top().pad(2.5f).expand().fill().row();

        Container<Table> paddedContainer = new Container<>(mainTable);
        paddedContainer.pad(HUD.FONT_SIZE).center();
        paddedContainer.fill();
        this.add(paddedContainer);

        setVisible(false);
        pack();
    }

    public void clearContent() {
        contentTable.clearChildren();
        titleLabel.setText("");
        bodyLabel.setText("");
    }

    public void showInfo(InfoProvider provider) {
        clearContent();
        titleLabel.setText(provider.getTitle());
        bodyLabel.setText(provider.getDescription());
        provider.fillCustomContent(contentTable, ResourceManager.get().skin);
        setVisible(true);
        pack();
    }
}
