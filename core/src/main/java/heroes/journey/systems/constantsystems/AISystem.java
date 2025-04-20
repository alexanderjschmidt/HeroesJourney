package heroes.journey.systems.constantsystems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.artemis.EntityEdit;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

import heroes.journey.components.overworld.character.AIComponent;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.components.overworld.character.MovementComponent;
import heroes.journey.entities.actions.QueuedAction;

@All({AIComponent.class})
public class AISystem extends IteratingSystem {

    public static final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    protected void process(int entityId) {
        AIComponent ai = AIComponent.get(getWorld(), entityId);
        if (ai.getFutureResult() != null && ai.getFutureResult().isDone()) {
            try {
                QueuedAction nextMove = ai.getFutureResult().get();
                System.out.println(nextMove);

                EntityEdit entity = world.edit(entityId);
                entity.create(MovementComponent.class).path(nextMove.getPath());
                entity.create(ActionComponent.class)
                    .action(nextMove.getAction())
                    .targetX(nextMove.getTargetX())
                    .targetY(nextMove.getTargetY());

                ai.idle();
            } catch (Exception e) {
                e.printStackTrace();
                ai.idle();
            }
        }
    }
}
