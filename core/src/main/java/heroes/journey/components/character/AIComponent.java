package heroes.journey.components.character;

import heroes.journey.GameState;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.ai.AI;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.constantsystems.AISystem;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;
import java.util.concurrent.Future;

public class AIComponent extends PooledClonableComponent<AIComponent> {

    @Getter
    private transient Future<QueuedAction> futureResult = null;
    @Accessors(fluent = true, chain = true)
    @Setter
    private transient AI ai;

    public AIComponent() {
        this.ai = new MCTSAI();
    }

    public void startProcessingNextMove(GameState gameState, UUID playingEntity) {
        if (futureResult == null) {
            futureResult = AISystem.executor.submit(() -> ai.getMove(gameState, playingEntity));
        }
    }

    public void idle() {
        futureResult = null;
    }

    public static AIComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(AIComponent.class, entityId);
    }

    @Override
    protected void reset() {
        ai = null;
        futureResult.cancel(true);
        futureResult = null;
    }

    @Override
    public void copy(AIComponent from) {
        ai = from.ai;
    }
}
