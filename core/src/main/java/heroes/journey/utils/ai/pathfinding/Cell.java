package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;

import heroes.journey.modlib.utils.Position;

public class Cell {

    public int x;
    public int y;
    public Cell parent;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Cell create(GraphPath<TileNode> path) {
        Cell parent = null;
        Cell node = null;
        for (TileNode t : path) {
            node = new Cell(t.x, t.y);
            node.parent = parent;
            parent = node;
        }
        return node;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ") " + parent;
    }

    public Cell getEnd() {
        Cell next = this;
        while (next.parent != null) {
            next = next.parent;
        }
        return next;
    }

    public static Cell clone(Cell path) {
        Cell holder = null;
        while (path != null) {
            Cell holder2 = holder;
            holder = new Cell(path.x, path.y);
            holder.parent = holder2;
            path = path.parent;
        }
        return holder;
    }

    public Cell reverse() {
        return reversePath(this);
    }

    public static Cell reversePath(Cell node) {
        Cell prev = null;
        Cell current = node;
        Cell next = null;
        while (current != null) {
            next = current.parent;
            current.parent = prev;
            prev = current;
            current = next;
        }
        node = prev;
        return node;
    }

    public Position toPos() {
        return new Position(x, y);
    }
}
