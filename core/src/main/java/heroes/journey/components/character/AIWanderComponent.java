package heroes.journey.components.character;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.components.utils.WanderType;
import heroes.journey.entities.Position;
import heroes.journey.systems.GameWorld;

public class AIWanderComponent extends PooledClonableComponent<AIWanderComponent> {

    private WanderType wanderType;
    private Position localPosition;

    public static AIWanderComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(AIWanderComponent.class, entityId);
    }

    @Override
    protected void reset() {
        wanderType = null;
        localPosition.reset();
    }

    @Override
    public void copy(AIWanderComponent from) {
        wanderType = from.wanderType;
    }

    public WanderType getWanderType() {
        return wanderType;
    }

    public Position getLocalPosition() {
        return localPosition;
    }

    public AIWanderComponent setWanderType(WanderType wanderType) {
        this.wanderType = wanderType;
        return this;
    }

    public AIWanderComponent setLocalPosition(Position position) {
        this.localPosition = position;
        return this;
    }
}
