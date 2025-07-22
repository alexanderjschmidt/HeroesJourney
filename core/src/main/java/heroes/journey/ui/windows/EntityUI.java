package heroes.journey.ui.windows;

import static heroes.journey.modlib.Ids.LIGHT_BLUE;
import static heroes.journey.modlib.Ids.PURPLE;
import static heroes.journey.modlib.Ids.RED;
import static heroes.journey.modlib.Ids.YELLOW;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.modlib.Ids;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ResourceBar;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class EntityUI extends UI {

    private final Label entity;
    private final ResourceBar stamina, focus, mana, moxie;

    public EntityUI() {
        super();
        this.entity = new Label("", ResourceManager.get().skin);
        this.entity.setWrap(true);
        this.mainTable.add(entity).expandX().row();

        this.stamina = new ResourceBar(RED, null);
        this.focus = new ResourceBar(LIGHT_BLUE, null);
        this.mana = new ResourceBar(PURPLE, null);
        this.moxie = new ResourceBar(YELLOW, null);

        this.mainTable.add(stamina).expandX().row();
        this.mainTable.add(focus).expandX().row();
        this.mainTable.add(mana).expandX().row();
        this.mainTable.add(moxie).expandX().row();

        pack();
    }

    @Override
    public void update() {
        UUID entityId = HUD.get().getCursor().getHover();
        if (entityId == null)
            return;
        Attributes statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
        String name = NamedComponent.get(GameState.global().getWorld(), entityId, "---");

        entity.setText(name);

        if (statsComponent != null) {
            stamina.update(statsComponent, Ids.STAT_STAMINA);
            focus.update(statsComponent, Ids.STAT_FOCUS);
            mana.update(statsComponent, Ids.STAT_MANA);
            moxie.update(statsComponent, Ids.STAT_MOXIE);
        }
    }
}
