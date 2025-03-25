package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

import heroes.journey.tilemap.TileConnection;
import heroes.journey.tilemap.TileNode;

public class Pathing {
    public GraphPath<TileNode> getPath() {
        int width = 5, height = 5;
        IndexedGraph<TileNode> graph = new DefaultIndexedGraph<>();

        TileNode[][] nodes = new TileNode[width][height];

        // Create Nodes
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new TileNode(y * width + x, x, y);
                graph.addNode(nodes[x][y]);
            }
        }

        // Connect Nodes (4-directional movement)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x > 0)
                    graph.addConnection(new TileConnection(nodes[x][y], nodes[x - 1][y], 1));
                if (x < width - 1)
                    graph.addConnection(new TileConnection(nodes[x][y], nodes[x + 1][y], 1));
                if (y > 0)
                    graph.addConnection(new TileConnection(nodes[x][y], nodes[x][y - 1], 1));
                if (y < height - 1)
                    graph.addConnection(new TileConnection(nodes[x][y], nodes[x][y + 1], 1));
            }
        }

        // A* Pathfinding
        TileNode startNode = nodes[0][0];
        TileNode endNode = nodes[4][4];

        IndexedAStarPathFinder<TileNode> pathFinder = new IndexedAStarPathFinder<>(graph);
        GraphPath<TileNode> path = new com.badlogic.gdx.ai.pfa.DefaultGraphPath<>();
        ManhattanHeuristic heuristic = new ManhattanHeuristic();

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        return path;
    }
}

class ManhattanHeuristic implements Heuristic<TileNode> {
    @Override
    public float estimate(TileNode node, TileNode endNode) {
        return Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
    }
}
