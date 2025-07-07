package heroes.journey.tilemap;

import static heroes.journey.registries.Registries.TerrainManager;

import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.tilemap.wavefunctiontiles.Tile;
import heroes.journey.utils.Direction;

public class TileHelpers {

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
}
