package heroes.journey.components;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;

public class PositionComponent extends PooledClonableComponent<PositionComponent> {

    private int x, y, targetX, targetY;

    public PositionComponent() {
    }

    public void sync() {
        x = targetX;
        y = targetY;
    }

    public PositionComponent setPos(int x, int y) {
        targetX = x;
        targetY = y;
        return this;
    }

    public static PositionComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(PositionComponent.class, entityId);
    }

    @Override
    protected void reset() {
        x = -1;
        y = -1;
        targetX = -1;
        targetY = -1;
    }

    @Override
    public void copy(PositionComponent from) {
        x = from.x;
        y = from.y;
        targetX = from.targetX;
        targetY = from.targetY;
    }

    public boolean isNotSynced() {
        return targetX != x || targetY != y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getTargetX() {
        return this.targetX;
    }

    public int getTargetY() {
        return this.targetY;
    }
}
