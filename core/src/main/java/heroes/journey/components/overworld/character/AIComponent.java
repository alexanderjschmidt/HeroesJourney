package heroes.journey.components.overworld.character;

import java.util.concurrent.Future;

import com.artemis.World;

import heroes.journey.GameState;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.ai.AI;
import heroes.journey.entities.ai.MCTSAI;
import heroes.journey.systems.constantsystems.AISystem;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class AIComponent extends PooledClonableComponent<AIComponent> {

    @Getter private transient Future<QueuedAction> futureResult = null;
    @Accessors(fluent = true, chain = true) @Setter private transient AI ai;

    public AIComponent() {
        this.ai = new MCTSAI();
    }

    public void startProcessingNextMove(GameState gameState, Integer playingEntity) {
        if (futureResult == null) {
            futureResult = AISystem.executor.submit(() -> ai.getMove(gameState, playingEntity));
        }
    }

    public void idle() {
        futureResult = null;
    }

    public static AIComponent get(World world, int entityId) {
        return world.getMapper(AIComponent.class).get(entityId);
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
