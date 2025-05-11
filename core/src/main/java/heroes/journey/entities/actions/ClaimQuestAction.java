package heroes.journey.entities.actions;

import heroes.journey.components.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.quests.Quest;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
public class ClaimQuestAction extends Action {

    private final Quest quest;

    @Override
    public ActionResult onSelect(ActionInput input) {
        UUID town = Utils.getLocation(input);
        QuestsComponent factionsQuestsComponent = QuestsComponent.get(input.getGameState().getWorld(), town);

        if (factionsQuestsComponent != null) {
            factionsQuestsComponent.getQuests().remove(quest);
            QuestsComponent.get(input.getGameState().getWorld(), input.getEntityId()).addQuest(quest);
        }
        return new EndTurnResult();
    }
}
