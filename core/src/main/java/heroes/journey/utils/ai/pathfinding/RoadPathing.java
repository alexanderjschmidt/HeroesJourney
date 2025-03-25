package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

import heroes.journey.initializers.base.Tiles;
import heroes.journey.tilemap.TileMap;
import heroes.journey.tilemap.wavefunction.Terrain;

public class RoadPathing {

    public Cell getPath(TileMap map, int startX, int startY, int endX, int endY) {
        TileNode startNode = map.getNodes()[startX][startY];
        TileNode endNode = map.getNodes()[endX][endY];

        IndexedAStarPathFinder<TileNode> pathFinder = new IndexedAStarPathFinder<>(map);
        GraphPath<TileNode> path = new DefaultGraphPath<>();
        PathFavoringHeuristic heuristic = new PathFavoringHeuristic(map);

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);

        return Cell.create(path);
    }

    private static class PathFavoringHeuristic implements Heuristic<TileNode> {

        TileMap map;

        public PathFavoringHeuristic(TileMap map) {
            this.map = map;
        }

        @Override
        public float estimate(TileNode node, TileNode endNode) {
            float baseHeuristic = Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
            Terrain terrain = map.getTileMap()[node.x][node.y].getTerrain();
            if (terrain == Tiles.PATH)
                return baseHeuristic;
            return baseHeuristic * terrain.getTerrainCost() * 10;
        }
    }
}
