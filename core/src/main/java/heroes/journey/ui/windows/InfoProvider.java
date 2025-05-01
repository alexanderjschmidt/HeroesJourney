package heroes.journey.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public interface InfoProvider {
    String getTitle();

    String getDescription();

    void fillCustomContent(Table table, Skin skin);
}

