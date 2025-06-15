package heroes.journey.components.character;

import java.util.UUID;

import com.artemis.annotations.Transient;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.ai.pathfinding.Cell;

@Transient
public class MovementComponent extends PooledClonableComponent<MovementComponent> {

    private Cell path;
    private boolean startedMoving;

    public MovementComponent() {
        this.startedMoving = false;
    }

    public void progressPath() {
        path = path.parent;
        startedMoving = true;
    }

    public boolean hasBegunMoving() {
        return this.startedMoving;
    }

    public boolean hasPath() {
        return path != null;
    }

    public static MovementComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(MovementComponent.class, entityId);
    }

    @Override
    protected void reset() {
        path = null;
        this.startedMoving = false;
    }

    @Override
    public void copy(MovementComponent from) {
        path = Cell.clone(from.path);
    }

    public Cell path() {
        return this.path;
    }

    public MovementComponent path(Cell path) {
        this.path = path;
        return this;
    }
}
