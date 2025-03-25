package heroes.journey.utils.ai.pathfinding;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

import heroes.journey.tilemap.TileMap;

public class EntityCursorPathing {

    public Cell getPath(TileMap map, int startX, int startY, int endX, int endY, Entity entity) {
        TileNode startNode = map.getNodes()[startX][startY];
        TileNode endNode = map.getNodes()[endX][endY];

        IndexedAStarPathFinder<TileNode> pathFinder = new IndexedAStarPathFinder<>(map);
        GraphPath<TileNode> path = new DefaultGraphPath<>();
        TerrainAwareHeuristic heuristic = new TerrainAwareHeuristic(entity, map);

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);

        return Cell.create(path);
    }

    private static class TerrainAwareHeuristic implements Heuristic<TileNode> {
        Entity entity;
        TileMap map;

        public TerrainAwareHeuristic(Entity entity, TileMap map) {
            this.entity = entity;
            this.map = map;
        }

        @Override
        public float estimate(TileNode node, TileNode endNode) {
            float baseHeuristic = Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
            float terrainCost = map.getTerrainCost(node.x, node.y, entity);
            return baseHeuristic * terrainCost;
        }
    }
}
