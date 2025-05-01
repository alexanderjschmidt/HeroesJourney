package heroes.journey.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.utils.art.ResourceManager;

public class EffectManager extends Stage {

    private static EffectManager effectManager;

    private EffectManager() {
        super(Application.get().getViewport());
    }

    public static EffectManager get() {
        if (effectManager == null)
            effectManager = new EffectManager();
        return effectManager;
    }

    // TODO allow chaining of effects? or some randomness to the positioning
    public static void addTextEffect(String text, Color color, int x, int y, int xoffset, int yoffset) {
        Label stamina = new Label(text, ResourceManager.get().skin);
        stamina.setPosition(x * GameCamera.get().getSize() + xoffset,
            y * GameCamera.get().getSize() + yoffset);
        stamina.setColor(color);
        stamina.addAction(
            Actions.sequence(Actions.parallel(Actions.moveBy(0, 30f, 1.0f), // move up over 1 second
                    Actions.fadeOut(1.0f)         // fade out over 1 second
                ), Actions.removeActor()             // remove from stage after animation
            ));
        get().addActor(stamina);
    }

    public void update(float delta) {
        act();
        draw();
    }
}
