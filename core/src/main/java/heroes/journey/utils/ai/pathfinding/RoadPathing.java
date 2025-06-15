package heroes.journey.utils.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import heroes.journey.tilemap.TileMap;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;

import static heroes.journey.registries.Registries.TerrainManager;

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
            if (terrain == TerrainManager.get("path"))
                return baseHeuristic;
            if (terrain == TerrainManager.get("plains"))
                return baseHeuristic * terrain.terrainCost * 10;
            return baseHeuristic * terrain.terrainCost * 50;
        }
    }
}
