package heroes.journey.modlib;

public class Position {

    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public Position setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int distanceTo(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        // return (int) Math.sqrt(dx * dx + dy * dy); // Euclidean
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y); // Manhattan
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void reset() {
        x = -1;
        y = -1;
    }
}
