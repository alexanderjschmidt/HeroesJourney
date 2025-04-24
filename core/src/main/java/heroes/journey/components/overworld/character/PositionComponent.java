package heroes.journey.components.overworld.character;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Getter;

@Getter
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

    public static PositionComponent get(World world, int entityId) {
        return world.getMapper(PositionComponent.class).get(entityId);
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
}
