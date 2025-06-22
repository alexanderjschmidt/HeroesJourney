package heroes.journey.ui.windows;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public interface InfoProvider {
    String getTitle(Map<String,String> input);

    String getDescription(Map<String,String> input);

    void fillCustomContent(Table table, Skin skin, Map<String,String> input);
}

