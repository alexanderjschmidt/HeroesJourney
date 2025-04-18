package heroes.journey.components.overworld.character;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.utils.ai.pathfinding.Cell;
import lombok.Getter;

public class MovementComponent implements Component {

    @Getter private Cell path;
    private boolean startedMoving;

    public MovementComponent(Cell path) {
        this.path = path;
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

    private static final ComponentMapper<MovementComponent> mapper = ComponentMapper.getFor(
        MovementComponent.class);

    public static MovementComponent get(Entity entity) {
        return mapper.get(entity);
    }

}
