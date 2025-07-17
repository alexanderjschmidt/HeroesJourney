package heroes.journey.ui.windows.stats;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import heroes.journey.GameState;
import heroes.journey.components.QuestsComponent;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.ActionContext;
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
        if (questsComponent != null) {
            for (Quest quest : questsComponent.getQuests()) {
                // Create quest display with costs and rewards
                String questText = formatQuestDisplay(quest, entityId);
                quests.add(new Label(questText, ResourceManager.get().skin)).row();
            }
        }
    }

    private String formatQuestDisplay(Quest quest, UUID entityId) {
        StringBuilder sb = new StringBuilder();
        sb.append(quest.getName()).append("\n");

        // Show cost if any
        if (!quest.getCost().isEmpty()) {
            sb.append("  Cost: ");
            quest.getCost()
                .forEach((stat, amount) -> sb.append(amount)
                    .append(" ")
                    .append(stat.getId().toUpperCase())
                    .append(" "));
            sb.append("\n");
        }

        // Show rewards if any
        if (!quest.getRewards().isEmpty()) {
            sb.append("  Reward: ");
            quest.getRewards()
                .forEach((stat, amount) -> sb.append(amount)
                    .append(" ")
                    .append(stat.getId().toUpperCase())
                    .append(" "));
            sb.append("\n");
        }

        // Show fame reward if any
        if (quest.getFameReward() > 0) {
            sb.append("  Fame: +").append(quest.getFameReward()).append("\n");
        }

        // Show affordability status
        ActionContext input = new ActionContext(GameState.global(), entityId);
        if (quest.canAfford(input)) {
            sb.append("  [CAN AFFORD]");
        } else {
            sb.append("  [CANNOT AFFORD]");
        }

        return sb.toString();
    }
}
