package heroes.journey.systems.constantsystems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import heroes.journey.components.overworld.character.AIComponent;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.components.overworld.character.MovementComponent;
import heroes.journey.entities.actions.QueuedAction;

public class AISystem extends IteratingSystem {

    public static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public AISystem() {
        super(Family.all(AIComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AIComponent ai = AIComponent.get(entity);
        if (ai.getFutureResult() != null && ai.getFutureResult().isDone()) {
            try {
                QueuedAction nextMove = ai.getFutureResult().get();
                System.out.println(nextMove);

                entity.add(new MovementComponent(nextMove.getPath()));
                entity.add(
                    new ActionComponent(nextMove.getAction(), nextMove.getTargetX(), nextMove.getTargetY()));

                ai.idle();
            } catch (Exception e) {
                e.printStackTrace();
                ai.idle();
            }
        }
    }
}
