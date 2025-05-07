package heroes.journey.ui.windows;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ResourceBar;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class EntityUI extends UI {

    Label entity;
    ResourceBar health, mana, stamina;

    public EntityUI() {
        super();
        this.entity = new Label("", ResourceManager.get().skin);
        this.entity.setWrap(true);
        this.health = new ResourceBar(ResourceManager.get(LoadTextures.UI)[3][0], null);
        this.mana = new ResourceBar(ResourceManager.get(LoadTextures.UI)[2][1], null);
        this.stamina = new ResourceBar(ResourceManager.get(LoadTextures.UI)[3][1], null);
        this.mainTable.add(entity).expandX().row();
        this.mainTable.add(health).expandX().row();
        this.mainTable.add(mana).expandX().row();
        this.mainTable.add(stamina).expandX().row();

        pack();
    }

    @Override
    public void update() {
        UUID entityId = HUD.get().getCursor().getHover();
        if (entityId == null)
            return;
        StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
        String name = NamedComponent.get(GameState.global().getWorld(), entityId, "---");

        entity.setText(name);

        if (statsComponent != null) {
            health.update(statsComponent.getHealth(), (int)(StatsComponent.MAX_HEALTH));
            mana.update(statsComponent.getMana(), (int)(StatsComponent.MAX_HEALTH));
            stamina.update(statsComponent.getStamina(), (int)(StatsComponent.MAX_HEALTH));
        }
    }

}
