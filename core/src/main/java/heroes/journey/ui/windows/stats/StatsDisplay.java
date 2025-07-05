package heroes.journey.ui.windows.stats;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.ui.HUD;
import heroes.journey.utils.art.ResourceManager;

import java.util.UUID;

public class StatsDisplay extends Table {

    private UUID entityId;
    private final Label health, mana, stamina;
    private final Label body, mind, fame;
    private final Image character;

    public StatsDisplay() {
        Label title = new Label("===== Stats =====", ResourceManager.get().skin);
        health = new Label("", ResourceManager.get().skin);
        mana = new Label("", ResourceManager.get().skin);
        stamina = new Label("", ResourceManager.get().skin);
        body = new Label("", ResourceManager.get().skin);
        mind = new Label("", ResourceManager.get().skin);
        fame = new Label("", ResourceManager.get().skin);
        character = new Image();

        this.defaults().fill().expandX().left().pad(2.5f);
        this.add(title).row();
        this.add(health).row();
        this.add(mana).row();
        this.add(stamina).row();
        this.add(body).row();
        this.add(mind).row();
        this.add(fame).row();
        this.add().expandY().row();
        this.add(character).row();
    }

    public void setEntity(UUID entityId) {
        this.entityId = entityId;
        Attributes statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);

        assert statsComponent != null;
        body.setText("Body: " + statsComponent.get(Stat.BODY));
        mind.setText("Mind: " + statsComponent.get(Stat.MIND));
        fame.setText("Fame: " + StatsComponent.getFame(GameState.global().getWorld(), entityId));
    }

    // TODO this should be a widget
    public void draw(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(GameState.global().getWorld(), entityId);
        batch.draw(renderComponent.sprite().getRender(), getX() + HUD.FONT_SIZE, getY() + HUD.FONT_SIZE,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);
        super.draw(batch, parentAlpha);
    }
}
