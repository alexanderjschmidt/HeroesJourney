package heroes.journey.components.overworld.character;

import com.artemis.World;
import com.artemis.annotations.Transient;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.utils.ai.pathfinding.Cell;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Transient
@Accessors(fluent = true, chain = true)
public class MovementComponent extends PooledClonableComponent<MovementComponent> {

    @Getter @Setter private Cell path;
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

    public static MovementComponent get(World world, int entityId) {
        return world.getMapper(MovementComponent.class).get(entityId);
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
