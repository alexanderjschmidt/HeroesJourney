package heroes.journey.components.character;

import heroes.journey.GameState;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.ai.AI;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.constantsystems.AISystem;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.Future;

public class AITurnComponent extends PooledClonableComponent<AITurnComponent> {

    @Getter
    private transient Future<QueuedAction> futureResult = null;
    private final transient AI ai;

    public AITurnComponent() {
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

    public static AITurnComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(AITurnComponent.class, entityId);
    }

    @Override
    protected void reset() {
        futureResult.cancel(true);
        futureResult = null;
    }

    @Override
    public void copy(AITurnComponent from) {
    }
}
