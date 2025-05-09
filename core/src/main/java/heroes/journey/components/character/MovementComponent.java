package heroes.journey.components.character;

import com.artemis.annotations.Transient;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.ai.pathfinding.Cell;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Transient
@Accessors(fluent = true, chain = true)
public class MovementComponent extends PooledClonableComponent<MovementComponent> {

    @Getter
    @Setter
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

}
