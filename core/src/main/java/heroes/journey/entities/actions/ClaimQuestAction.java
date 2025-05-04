package heroes.journey.entities.actions;

import heroes.journey.GameState;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.quests.Quest;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
public class ClaimQuestAction extends Action {

    private final Quest quest;

    @Override
    public ActionResult onSelect(GameState gameState, UUID entityId) {
        UUID town = Utils.getLocation(gameState, entityId);
        QuestsComponent factionsQuestsComponent = QuestsComponent.get(gameState.getWorld(), town);

        if (factionsQuestsComponent != null) {
            factionsQuestsComponent.getQuests().remove(quest);
            QuestsComponent.get(gameState.getWorld(), entityId).addQuest(quest);
        }
        return null;
    }
}
