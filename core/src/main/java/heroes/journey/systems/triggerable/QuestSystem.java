package heroes.journey.systems.triggerable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.artemis.annotations.All;

import heroes.journey.components.QuestsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.ActionInput;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;

@All({QuestsComponent.class, IdComponent.class})
public class QuestSystem extends TriggerableSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        QuestsComponent quests = QuestsComponent.get(world, id);
        List<Quest> completedQuests = new ArrayList<>();
        ActionInput input = new ActionInput(world.getGameState(), id);
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
