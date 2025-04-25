package heroes.journey.entities.actions.history;

import java.util.Stack;

import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.utils.ai.pathfinding.Cell;

public class History extends Stack<Record> implements Cloneable {

    public void add(Cell path, Integer currentEntity) {
        this.add(new MovementRecord(currentEntity, path.toPos(), path.getEnd().toPos()));
    }

    public void add(Action action, Position pos, Integer currentEntity) {
        this.add(new ActionRecord(currentEntity, action, pos));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public History clone() {
        return (History)super.clone();
    }
}

