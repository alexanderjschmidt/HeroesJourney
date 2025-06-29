package heroes.journey.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import heroes.journey.entities.tagging.Attributes;
import heroes.journey.registries.Tags;
import heroes.journey.utils.art.ResourceManager;

public class ResourceBar extends Stack {

    private final TextureRegion front, background;
    private final Label resource;

    private int currentVal, maxVal;

    public ResourceBar(TextureRegion front, TextureRegion background) {
        super();
        this.front = front;
        this.background = background;
        resource = new Label("?/?", ResourceManager.get().skin);
        this.add(resource);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (background != null)
            batch.draw(background, getX(), getY(), getWidth(), getHeight());
        batch.draw(front, getX(), getY(), getWidth() * ((float)currentVal / maxVal), getHeight());
        super.draw(batch, parentAlpha);
    }

    public void update(int currentVal, int maxVal) {
        this.currentVal = currentVal;
        this.maxVal = maxVal;
        resource.setText(currentVal + "/" + maxVal);
    }

    public void update(Attributes stats, String tagId) {
        this.currentVal = stats.get(Tags.getTag(tagId));
        this.maxVal = Tags.getTag(tagId).getMax();
        resource.setText(currentVal + "/" + maxVal);
    }
}
