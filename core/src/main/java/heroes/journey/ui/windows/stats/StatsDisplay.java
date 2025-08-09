package heroes.journey.ui.windows.stats;

import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.attributes.Attributes;
import heroes.journey.modlib.misc.Buff;
import heroes.journey.mods.Registries;
import heroes.journey.ui.HUD;
import heroes.journey.utils.art.ResourceManager;

public class StatsDisplay extends Table {

    private UUID entityId;
    private final Label body, mind, magic, charisma, fame;
    private final Image character;
    private final Table leftColumn;
    private final Table buffsColumn;
    private final Table buffsList;

    public StatsDisplay() {
        Label title = new Label("===== Stats =====", ResourceManager.get().skin);
        body = new Label("", ResourceManager.get().skin);
        mind = new Label("", ResourceManager.get().skin);
        magic = new Label("", ResourceManager.get().skin);
        charisma = new Label("", ResourceManager.get().skin);
        fame = new Label("", ResourceManager.get().skin);
        character = new Image();

        // Left column: stats and portrait
        leftColumn = new Table();
        leftColumn.defaults().fill().expandX().left().pad(2.5f);
        leftColumn.add(title).row();
        leftColumn.add(body).row();
        leftColumn.add(mind).row();
        leftColumn.add(magic).row();
        leftColumn.add(charisma).row();
        leftColumn.add(fame).row();
        leftColumn.add().expandY().row();
        leftColumn.add(character).row();

        // Right column: buffs list
        buffsColumn = new Table();
        buffsColumn.defaults().fillX().left().pad(2.5f);
        Label buffsTitle = new Label("===== Buffs =====", ResourceManager.get().skin);
        buffsList = new Table();
        buffsList.defaults().left().pad(2f);
        buffsColumn.add(buffsTitle).row();
        buffsColumn.add(buffsList).growX().row();

        // Root layout: two columns
        this.defaults().top().pad(2.5f);
        this.add(leftColumn).expand().left().top();
        this.add(buffsColumn).width(250f).top().right();
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

        populateBuffs();
    }

    private void populateBuffs() {
        buffsList.clearChildren();
        BuffsComponent buffsComponent = BuffsComponent.get(GameState.global().getWorld(), entityId);
        if (buffsComponent == null || buffsComponent.getTimeLeft().isEmpty()) {
            buffsList.add(new Label("None", ResourceManager.get().skin)).left().row();
            return;
        }
        for (Map.Entry<String, Integer> entry : buffsComponent.getTimeLeft().entrySet()) {
            String buffId = entry.getKey();
            int turnsLeft = entry.getValue();
            Buff buff = Registries.BuffManager.get(buffId);
            String name = buff != null ? buff.getName() : buffId;
            buffsList.add(new Label(name + " (" + turnsLeft + ")", ResourceManager.get().skin)).left().row();
        }
    }

    // TODO this should be a widget
    public void draw(Batch batch, float parentAlpha) {
        RenderComponent renderComponent = RenderComponent.get(GameState.global().getWorld(), entityId);
        batch.draw(renderComponent.sprite().getRender(), getX() + HUD.FONT_SIZE, getY() + HUD.FONT_SIZE,
            GameCamera.get().getSize() * 2, GameCamera.get().getSize() * 2);
        super.draw(batch, parentAlpha);
    }
}
