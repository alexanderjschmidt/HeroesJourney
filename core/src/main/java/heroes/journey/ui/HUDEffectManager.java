package heroes.journey.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import heroes.journey.utils.art.ResourceManager;

public class HUDEffectManager extends Stage {

    private static HUDEffectManager hudEffectManager;

    private HUDEffectManager(Viewport viewport) {
        super(viewport);
    }

    public static HUDEffectManager get() {
        if (hudEffectManager == null)
            hudEffectManager = new HUDEffectManager(HUD.get().getViewport());
        return hudEffectManager;
    }

    // TODO allow chaining of effects? or some randomness to the positioning
    public static void addTextEffect(String text, Color color, int x, int y) {
        Label stamina = new Label(text, ResourceManager.get().skin);
        stamina.setPosition(x, y);
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
