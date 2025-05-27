package heroes.journey.systems.triggerable;

import com.artemis.annotations.All;
import heroes.journey.GameState;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.Quest;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@All({QuestsComponent.class, IdComponent.class})
public class QuestSystem extends TriggerableSystem {

    private final GameState gameState;

    public QuestSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        QuestsComponent quests = QuestsComponent.get(world, id);
        List<Quest> completedQuests = new ArrayList<>();
        ActionInput input = new ActionInput(gameState, id);
        for (Quest quest : quests.getQuests()) {
            if (quest.isComplete(input)) {
                quest.onComplete(input);
                completedQuests.add(quest);
            }
        }
        quests.getQuests().removeAll(completedQuests);
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.MOVE;
    }
}
