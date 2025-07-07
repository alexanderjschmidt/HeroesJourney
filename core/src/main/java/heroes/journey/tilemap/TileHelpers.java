package heroes.journey.tilemap;

import static heroes.journey.registries.Registries.TerrainManager;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;

public class TileHelpers {

    public static TileLayout wangCorner = new TileLayout("Textures/wangCorner.png");
    public static TileLayout cliffTransitionTapper = new TileLayout("Textures/cliffTransitionTapper.png");
    public static TileLayout cliffTransition = new TileLayout("Textures/cliffTransition.png");
    public static TileLayout wangEdge = new TileLayout("Textures/wangEdge.png");

    public static void baseTile(Tile tile, String terrainStr, boolean addToBaseTiles) {
        Terrain terrain = TerrainManager.get(terrainStr);
        tile.add(Direction.NORTHWEST, terrain)
            .add(Direction.NORTH, terrain)
            .add(Direction.NORTHEAST, terrain)
            .add(Direction.EAST, terrain)
            .add(Direction.SOUTHEAST, terrain)
            .add(Direction.SOUTH, terrain)
            .add(Direction.SOUTHWEST, terrain)
            .add(Direction.WEST, terrain);
        if (addToBaseTiles)
            TileManager.baseTiles.add(tile);
    }

    public static void baseTile(Tile tile, String terrain) {
        baseTile(tile, terrain, true);
    }

    /**
     * @param terrain
     * @param adjacentTerrain
     * @param tiles           [y][x]
     * @param x               top left corner
     * @param y               top left corner
     */
    public static List<Tile> createEdge(
        String terrainId,
        String adjacentTerrainId,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y) {
        Terrain terrain = TerrainManager.get(terrainId);
        Terrain adjacentTerrain = TerrainManager.get(adjacentTerrainId);
        return wangEdge.generateTiles(tiles, weight, x, y, false, terrain, adjacentTerrain);
    }

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y][x]
     * @param x                 top left corner
     * @param y                 top left cornesr
     * @return
     */
    public static List<Tile> createWangCorner(
        String baseId,
        String adjacentTileOuterId,
        String adjacentTileInnerId,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        Terrain base = TerrainManager.get(baseId);
        Terrain adjacentTileOuter = TerrainManager.get(adjacentTileOuterId);
        Terrain adjacentTileInner = TerrainManager.get(adjacentTileInnerId);
        return wangCorner.generateTiles(tiles, weight, x, y, addToDefault, base, adjacentTileOuter,
            adjacentTileInner);
    }

    /**
     * @param base
     * @param adjacentTileOuter
     * @param adjacentTileInner
     * @param tiles             [y, x]
     * @param x                 top left corner
     * @param y                 top left corner
     */
    public static void createWangCornerAnimated(
        String baseId,
        String adjacentTileOuterId,
        String adjacentTileInnerId,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        Terrain base = TerrainManager.get(baseId);
        Terrain adjacentTileOuter = TerrainManager.get(adjacentTileOuterId);
        Terrain adjacentTileInner = TerrainManager.get(adjacentTileInnerId);
        wangCorner.generateAnimatedTiles(tiles, weight, x, y, 4, 5, addToDefault, base, adjacentTileOuter,
            adjacentTileInner);
    }

    public static void cliffTransitionTapper(
        String baseId,
        String adjacentTileOuterId,
        String adjacentTileInnerId,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        Terrain base = TerrainManager.get(baseId);
        Terrain adjacentTileOuter = TerrainManager.get(adjacentTileOuterId);
        Terrain adjacentTileInner = TerrainManager.get(adjacentTileInnerId);
        cliffTransitionTapper.generateTiles(tiles, weight * 1000, x, y, addToDefault, base, adjacentTileOuter,
            adjacentTileInner);
    }

    public static void cliffTransition(
        String cliff1Id,
        String cliff2Id,
        String adjacentTileOuter1Id,
        String adjacentTileOuter2Id,
        String adjacentTileInnerId,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        Terrain cliff1 = TerrainManager.get(cliff1Id);
        Terrain cliff2 = TerrainManager.get(cliff2Id);
        Terrain adjacentTileOuter1 = TerrainManager.get(adjacentTileOuter1Id);
        Terrain adjacentTileOuter2 = TerrainManager.get(adjacentTileOuter2Id);
        Terrain adjacentTileInner = TerrainManager.get(adjacentTileInnerId);
        cliffTransition.generateTiles(tiles, weight * 1000, x, y, addToDefault, cliff1, cliff2,
            adjacentTileOuter1, adjacentTileOuter2, adjacentTileInner);
    }

    public static void cliffTransitionAnimated(
        String cliff1Id,
        String cliff2Id,
        String adjacentTileOuter1Id,
        String adjacentTileOuter2Id,
        String adjacentTileInnerId,
        TextureRegion[][] tiles,
        int weight,
        int x,
        int y,
        boolean addToDefault) {
        Terrain cliff1 = TerrainManager.get(cliff1Id);
        Terrain cliff2 = TerrainManager.get(cliff2Id);
        Terrain adjacentTileOuter1 = TerrainManager.get(adjacentTileOuter1Id);
        Terrain adjacentTileOuter2 = TerrainManager.get(adjacentTileOuter2Id);
        Terrain adjacentTileInner = TerrainManager.get(adjacentTileInnerId);
        cliffTransition.generateAnimatedTiles(tiles, weight * 10, x, y, 4, 4, addToDefault, cliff1, cliff2,
            adjacentTileOuter1, adjacentTileOuter2, adjacentTileInner);
    }
}
