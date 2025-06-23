package heroes.journey.ui.windows;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.ui.HUD;
import heroes.journey.ui.UI;
import heroes.journey.utils.art.ResourceManager;

public class EntityUI extends UI {

    private final Label entity;

    public EntityUI() {
        super();
        this.entity = new Label("", ResourceManager.get().skin);
        this.entity.setWrap(true);
        this.mainTable.add(entity).expandX().row();

        pack();
    }

    @Override
    public void update() {
        UUID entityId = HUD.get().getCursor().getHover();
        if (entityId == null)
            return;
        String name = NamedComponent.get(GameState.global().getWorld(), entityId, "---");

        entity.setText(name);
    }
}
