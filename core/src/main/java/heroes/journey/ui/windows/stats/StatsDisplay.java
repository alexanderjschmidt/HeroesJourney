package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.ui.HUD;
import heroes.journey.utils.art.ResourceManager;

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
        PlayerComponent playerComponent = PlayerComponent.get(GameState.global().getWorld(), entityId);

        assert statsComponent != null;
        health.setText(
            "Health: " + statsComponent.get(Stats.HEALTH) + "/" + StatsUtils.getMaxHealth(statsComponent));
        mana.setText("Mana: " + statsComponent.get(Stats.MANA) + "/" + StatsUtils.getMaxMana(statsComponent));
        stamina.setText(
            "Stamina: " + statsComponent.get(Stats.STAMINA) + "/" + StatsUtils.getMaxStamina(statsComponent));
        body.setText("Body: " + statsComponent.get(Stats.BODY));
        mind.setText("Mind: " + statsComponent.get(Stats.MIND));
        fame.setText("Fame: " + playerComponent.fame());
    }

    public void draw(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(GameState.global().getWorld(), entityId);
        batch.draw(renderComponent.sprite(), getX() + HUD.FONT_SIZE, getY() + HUD.FONT_SIZE,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);
        super.draw(batch, parentAlpha);
    }
}
