package heroes.journey.systems.constantsystems;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.artemis.EntityEdit;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

import heroes.journey.components.character.AIComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.systems.GameWorld;

@All({AIComponent.class, IdComponent.class})
public class AISystem extends IteratingSystem {

    public static final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        AIComponent ai = AIComponent.get(world, id);
        if (ai.getFutureResult() != null && ai.getFutureResult().isDone()) {
            try {
                QueuedAction nextMove = ai.getFutureResult().get();
                System.out.println("Selected (AI) " + nextMove.getAction() + " " + id);

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
