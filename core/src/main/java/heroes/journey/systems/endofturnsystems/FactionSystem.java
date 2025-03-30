package heroes.journey.systems.endofturnsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import heroes.journey.GameState;
import heroes.journey.components.AIComponent;
import heroes.journey.components.FactionComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.systems.EndOfTurnSystem;
import heroes.journey.systems.GameEngine;

public class FactionSystem extends EndOfTurnSystem {

    public FactionSystem(GameEngine engine) {
        super(Family.all(FactionComponent.class, AIComponent.class).get(), engine);
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        AIComponent ai = AIComponent.get(entity);

        QueuedAction action = ai.getAI().getMove(GameState.global(), entity);
        if (action != null) {
            //ActionQueue.get().addAction(action);
        }
    }
}
