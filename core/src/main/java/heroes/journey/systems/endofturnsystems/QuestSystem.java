package heroes.journey.systems.endofturnsystems;

import java.util.ArrayList;
import java.util.List;

import com.artemis.World;
import com.artemis.annotations.All;

import heroes.journey.GameState;
import heroes.journey.components.QuestsComponent;
import heroes.journey.entities.quests.Quest;
import heroes.journey.systems.EndOfTurnSystem;

@All({QuestsComponent.class})
public class QuestSystem extends EndOfTurnSystem {

    private final GameState gameState;

    public QuestSystem(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        QuestsComponent quests = QuestsComponent.get(world, entityId);
        List<Quest> completedQuests = new ArrayList<>();
        for (Quest quest : quests.getQuests()) {
            if (quest.isComplete(gameState, entityId)) {
                quest.onComplete(gameState, entityId);
                completedQuests.add(quest);
            }
        }
        quests.getQuests().removeAll(completedQuests);
    }
}
