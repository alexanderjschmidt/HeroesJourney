package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

import heroes.journey.tilemap.TileMap;

public class EntityCursorPathing {

    public Cell getPath(TileMap map, int startX, int startY, int endX, int endY, Integer entityId) {
        TileNode startNode = map.getNodes()[startX][startY];
        TileNode endNode = map.getNodes()[endX][endY];

        IndexedAStarPathFinder<TileNode> pathFinder = new IndexedAStarPathFinder<>(map);
        GraphPath<TileNode> path = new DefaultGraphPath<>();
        TerrainAwareHeuristic heuristic = new TerrainAwareHeuristic(entityId, map);

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);

        return Cell.create(path);
    }

    private static class TerrainAwareHeuristic implements Heuristic<TileNode> {
        Integer entityId;
        TileMap map;

        public TerrainAwareHeuristic(Integer entityId, TileMap map) {
            this.entityId = entityId;
            this.map = map;
        }

        @Override
        public float estimate(TileNode node, TileNode endNode) {
            float baseHeuristic = Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
            float terrainCost = map.getTerrainCost(node.x, node.y, entityId);
            return baseHeuristic * terrainCost;
        }
    }
}
