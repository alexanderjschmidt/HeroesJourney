package heroes.journey.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

import java.util.Map;

public class InfoUI extends UI {

    private final Label titleLabel;
    private final Label bodyLabel;
    private final Table contentTable;
    private final Image separatorLine;

    public InfoUI() {
        super();
        this.titleLabel = new Label("", ResourceManager.get().skin, "bold18"); // Use 18pt bold style
        this.titleLabel.setWrap(true);
        this.bodyLabel = new Label("", ResourceManager.get().skin);
        this.bodyLabel.setWrap(true);
        this.bodyLabel.setAlignment(Align.top | Align.left);
        this.contentTable = new Table();
        // Create a white line separator
        Drawable whiteDrawable = ResourceManager.get().skin.newDrawable("white");
        this.separatorLine = new Image(whiteDrawable);
        separatorLine.setColor(1, 1, 1, 1);
        separatorLine.setHeight(2f);
        separatorLine.setFillParent(false);
        // Layout: title, line, content, body
        mainTable.add(titleLabel).expandX().fillX().row();
        mainTable.add(separatorLine).expandX().fillX().height(2f).padBottom(6f).row();
        mainTable.add(contentTable).row();
        mainTable.add(bodyLabel).top().expand().row();
        setVisible(false);
        pack();
    }

    public void clearContent() {
        contentTable.clearChildren();
        titleLabel.setText("");
        bodyLabel.setText("");
    }

    public void showInfo(InfoProvider provider, Map<String, String> input) {
        clearContent();
        titleLabel.setText(provider.getTitle(new ActionContext(GameState.global(), null, false, input)));
        bodyLabel.setText(provider.getDescription(input));
        provider.fillCustomContent(contentTable, ResourceManager.get().skin, input);
        setVisible(true);
        pack();
    }
}
