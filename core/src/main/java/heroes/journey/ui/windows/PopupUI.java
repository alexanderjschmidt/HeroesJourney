package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.ui.UI;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PopupUI extends UI {

    private String text;

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        this.drawText(batch, text, 0, 0);
    }
}
