package heroes.journey.ui.windows.stats;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import heroes.journey.GameState;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.entities.quests.Quest;
import heroes.journey.ui.UI;

public class QuestsDisplay extends Widget {

    private Integer entityId;

    public void setEntity(Integer entityId) {
        this.entityId = entityId;
    }

    public void draw(Batch batch, float parentAlpha) {
        QuestsComponent questsComponent = QuestsComponent.get(GameState.global().getWorld(), entityId);

        UI.drawText(this, batch, "===== Quests =====", 0, 0);
        for (int i = 0; i < questsComponent.getQuests().size(); i++) {
            Quest quest = questsComponent.getQuests().get(i);
            UI.drawText(this, batch, quest.toString(), 0, i + 1);
        }
    }
}
