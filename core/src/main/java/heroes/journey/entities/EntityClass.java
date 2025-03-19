package heroes.journey.entities;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.GameCamera;
import heroes.journey.entities.actions.Action;
import heroes.journey.initializers.base.BaseActions;

public class EntityClass {

    private ArrayList<Action> actions;

    private String name;
    private TextureRegion sprite;
    private String description;

    public EntityClass(String name, String description, TextureRegion sprite, Action... actions) {
        this.name = name;
        this.description = description;
        this.sprite = sprite;
        this.actions = new ArrayList<Action>();
        this.actions.add(BaseActions.wait);
        this.actions.addAll(Arrays.asList(actions));
        EntityClassManager.get().put(name, this);
    }

    public void render(Batch batch, float x, float y) {
        batch.draw(sprite, x, y, GameCamera.get().getSize(), GameCamera.get().getSize());
    }

    public void render(Batch batch, float x, float y, float width, float height) {
        batch.draw(sprite, x, y, width, height);
    }

    public String toString() {
        return name;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public String getDescription() {
        return description;
    }
}
