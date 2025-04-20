package heroes.journey.components.overworld.character;

import com.artemis.PooledComponent;
import com.artemis.World;

import lombok.Getter;

@Getter
public class PositionComponent extends PooledComponent {

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
}
