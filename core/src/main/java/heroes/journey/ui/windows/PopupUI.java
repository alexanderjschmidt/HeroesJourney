package heroes.journey.ui.windows;

import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.ui.UI;

public class PopupUI extends UI {

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void drawAndUpdate(Batch batch, float parentAlpha) {
        this.drawText(batch, text, 0, 0);
    }
}
