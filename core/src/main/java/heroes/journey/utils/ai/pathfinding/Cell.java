package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;

public class Cell implements Comparable<Cell> {

    public int f, g, h;
    public int i;
    public int j;
    public int t;
    public Cell parent;

    public Cell(int i, int j, int h) {
        this.i = i;
        this.j = j;
        this.h = h;
        this.f = h;
    }

    public static Cell create(GraphPath<TileNode> path) {
        Cell parent = null;
        Cell node = null;
        for (TileNode t : path) {
            node = new Cell(t.x, t.y, 1);
            node.parent = parent;
            parent = node;
        }
        return node;
    }

    @Override
    public String toString() {
        return "(" + this.i + ", " + this.j + ": {" + f + "," + g + "," + h + "," + t + "})";
    }

    @Override
    public boolean equals(Object o) {
        Cell obj = (Cell)o;
        return i == obj.i && j == obj.j;
    }

    @Override
    public int compareTo(Cell o) {
        return f - o.f == 0 ? h - o.h : f - o.f;
    }

    public static Cell clone(Cell path) {
        Cell holder = null;
        while (path != null) {
            Cell holder2 = holder;
            holder = new Cell(path.i, path.j, 1);
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
}
