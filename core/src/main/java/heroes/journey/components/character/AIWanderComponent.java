package heroes.journey.components.character;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.components.utils.WanderType;
import heroes.journey.modlib.utils.Position;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.Random;

public class AIWanderComponent extends PooledClonableComponent<AIWanderComponent> {

    private WanderType wanderType;
    private Position localPosition;
    private transient float timeSinceLastMove = 0f, targetWaitTime = 0f;

    public static AIWanderComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(AIWanderComponent.class, entityId);
    }

    @Override
    protected void reset() {
        wanderType = null;
        localPosition.reset();
        resetTime();
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

    public void resetTime() {
        this.timeSinceLastMove = 0f;
        targetWaitTime = 2f + Random.get().nextFloat() * 5f;
    }

    public boolean incrementTime(float delta) {
        this.timeSinceLastMove += delta;
        return timeSinceLastMove < targetWaitTime;
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
