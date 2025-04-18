package heroes.journey.components.overworld.character;

import java.util.concurrent.Future;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.ai.AI;
import heroes.journey.systems.constantsystems.AISystem;
import lombok.Getter;

public class AIComponent implements ClonableComponent<AIComponent> {

    @Getter private Future<QueuedAction> futureResult = null;

    private final AI ai;

    public AIComponent(AI ai) {
        this.ai = ai;
    }

    public void startProcessingNextMove(GameState gameState, Entity playingEntity) {
        if (futureResult == null) {
            futureResult = AISystem.executor.submit(() -> ai.getMove(gameState, playingEntity));
        }
    }

    public void idle() {
        futureResult = null;
    }

    public AIComponent clone() {
        return new AIComponent(ai);
    }

    private static final ComponentMapper<AIComponent> mapper = ComponentMapper.getFor(AIComponent.class);

    public static AIComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
