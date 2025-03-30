package heroes.journey.components.quests;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import heroes.journey.GameState;
import heroes.journey.systems.GameEngine;

public class QuestProcessor {

    public static void processQuests(GameState gameState) {
        Family family = Family.all(QuestsComponent.class, ActionObjectiveComponent.class).get();
        ImmutableArray<Entity> entities = GameEngine.get().getEntitiesFor(family);
        for (Entity entity : entities) {
            QuestsComponent quests = QuestsComponent.get(entity);
            
        }
    }

}
