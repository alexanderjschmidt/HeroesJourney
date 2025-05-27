package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameState;
import heroes.journey.components.QuestsComponent;
import heroes.journey.entities.Quest;
import heroes.journey.utils.art.ResourceManager;

public class QuestsDisplay extends Table {

    private final Table quests;

    public QuestsDisplay() {
        Label title = new Label("===== Quests =====", ResourceManager.get().skin);
        quests = new Table();
        quests.defaults().fill().left().pad(2.5f);

        this.defaults().fill().left().expandX().pad(2.5f);
        this.add(title).row();
        this.add(quests).row();
        this.add().expandY().row();
    }

    public void setEntity(UUID entityId) {
        quests.clear();
        QuestsComponent questsComponent = QuestsComponent.get(GameState.global().getWorld(), entityId);
        for (Quest quest : questsComponent.getQuests()) {
            quests.add(new Label(quest.toString(), ResourceManager.get().skin));
        }
    }
}
