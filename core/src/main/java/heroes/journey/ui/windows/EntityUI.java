package heroes.journey.ui.windows;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ResourceBar;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;

public class EntityUI extends UI {

    private final Label entity;
    @Getter private final ResourceBar health, mana, stamina;

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
        Attributes statsComponent = StatsComponent.get(GameState.global().getWorld(), entityId);
        String name = NamedComponent.get(GameState.global().getWorld(), entityId, "---");

        entity.setText(name);

        if (statsComponent != null) {
            health.update(statsComponent.get(Stats.HEALTH), StatsUtils.getMaxHealth(statsComponent));
            mana.update(statsComponent.get(Stats.MANA), StatsUtils.getMaxMana(statsComponent));
            stamina.update(statsComponent.get(Stats.STAMINA), StatsUtils.getMaxStamina(statsComponent));
        }
    }
}
