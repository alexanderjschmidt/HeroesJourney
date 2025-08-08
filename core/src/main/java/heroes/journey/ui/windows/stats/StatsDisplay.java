package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.attributes.Attributes;
import heroes.journey.ui.HUD;
import heroes.journey.utils.art.ResourceManager;

public class StatsDisplay extends Table {

    private UUID entityId;
    private final Label body, mind, magic, charisma, fame;
    private final Image character;

    public StatsDisplay() {
        Label title = new Label("===== Stats =====", ResourceManager.get().skin);
        body = new Label("", ResourceManager.get().skin);
        mind = new Label("", ResourceManager.get().skin);
        magic = new Label("", ResourceManager.get().skin);
        charisma = new Label("", ResourceManager.get().skin);
        fame = new Label("", ResourceManager.get().skin);
        character = new Image();

        this.defaults().fill().expandX().left().pad(2.5f);
        this.add(title).row();
        this.add(body).row();
        this.add(mind).row();
        this.add(magic).row();
        this.add(charisma).row();
        this.add(fame).row();
        this.add().expandY().row();
        this.add(character).row();
    }

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
        Attributes statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);

        assert statsComponent != null;
        body.setText("Body: " + statsComponent.get(Ids.STAT_BODY));
        mind.setText("Mind: " + statsComponent.get(Ids.STAT_MIND));
        magic.setText("Magic: " + statsComponent.get(Ids.STAT_MAGIC));
        charisma.setText("Charisma: " + statsComponent.get(Ids.STAT_CHARISMA));
        fame.setText("Fame: " + statsComponent.get(Ids.STAT_FAME));
    }

    // TODO this should be a widget
    public void draw(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(GameState.global().getWorld(), entityId);
        batch.draw(renderComponent.sprite().getRender(), getX() + HUD.FONT_SIZE, getY() + HUD.FONT_SIZE,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);
        super.draw(batch, parentAlpha);
    }
}
