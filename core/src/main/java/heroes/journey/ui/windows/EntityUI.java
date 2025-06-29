package heroes.journey.ui.windows;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ResourceBar;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class EntityUI extends UI {

    private final Label entity;
    private final ResourceBar valor, insight, arcanum, inspiration;

    public EntityUI() {
        super();
        this.entity = new Label("", ResourceManager.get().skin);
        this.entity.setWrap(true);
        this.mainTable.add(entity).expandX().row();

        this.valor = new ResourceBar(ResourceManager.get(ResourceManager.UI)[2][0], null);
        this.insight = new ResourceBar(ResourceManager.get(ResourceManager.UI)[4][1], null);
        this.arcanum = new ResourceBar(ResourceManager.get(ResourceManager.UI)[4][0], null);
        this.inspiration = new ResourceBar(ResourceManager.get(ResourceManager.UI)[3][1], null);

        this.mainTable.add(valor).expandX().row();
        this.mainTable.add(insight).expandX().row();
        this.mainTable.add(arcanum).expandX().row();
        this.mainTable.add(inspiration).expandX().row();

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
            valor.update(statsComponent, "valor");
            insight.update(statsComponent, "insight");
            arcanum.update(statsComponent, "arcanum");
            inspiration.update(statsComponent, "inspiration");
        }
    }
}
