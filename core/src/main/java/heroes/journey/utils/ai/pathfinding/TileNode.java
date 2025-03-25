package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class TileNode {
    public int x, y;
    private final Array<Connection<TileNode>> connections = new Array<>();

    public TileNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Array<Connection<TileNode>> getConnections() {
        return connections;
    }
}
