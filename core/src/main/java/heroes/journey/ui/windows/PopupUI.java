package heroes.journey.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;

@Getter
public class PopupUI extends UI {

    private final Label text;

    public PopupUI() {
        super();
        this.text = new Label("", ResourceManager.get().skin);
        this.text.setWrap(true);
        this.mainTable.add(text).expand();
    }

}
