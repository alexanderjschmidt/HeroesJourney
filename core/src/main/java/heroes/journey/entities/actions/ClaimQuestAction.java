package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.quests.Quest;

public class ClaimQuestAction extends Action {

    private final Quest quest;

    public ClaimQuestAction(Builder builder) {
        super(builder);
        this.quest = builder.quest;
    }

    @Override
    public String onSelect(GameState gameState, Entity entity) {
        Entity town = Utils.getLocationsFaction(gameState, entity);
        QuestsComponent factionsQuestsComponent = QuestsComponent.get(town);
        QuestsComponent questsComponent = QuestsComponent.get(town);

        if (factionsQuestsComponent != null && questsComponent != null) {
            factionsQuestsComponent.remove(quest);
            QuestsComponent.get(entity).addQuest(quest);
        }
        return null;
    }

    public static class Builder extends Action.ActionBuilder<Builder, ClaimQuestAction> {

        private Quest quest;

        public Builder() {
            super.terminalAction(true);
        }

        public Builder quest(Quest quest) {
            this.quest = quest;
            return this;
        }

        public Builder terminalAction(boolean terminalAction) {
            return this;
        }

        public ClaimQuestAction build() {
            return new ClaimQuestAction(this);
        }
    }
}
