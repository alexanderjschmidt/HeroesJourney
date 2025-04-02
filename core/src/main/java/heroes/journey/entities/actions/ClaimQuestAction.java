package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.quests.Quest;

public class ClaimQuestAction extends Action {

    private final Quest quest;

    public ClaimQuestAction(String name, Quest quest) {
        super(name);
        this.quest = quest;
    }

    @Override
    public boolean requirementsMet(GameState gameState, Entity entity) {
        return true;
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
}
