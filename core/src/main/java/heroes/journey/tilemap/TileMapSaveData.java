package heroes.journey.tilemap;

import lombok.Getter;

import java.util.Map;

@Getter
public class TileMapSaveData {

    private Map<String, Integer> terrainMap;
    private Map<String, Integer> tileToIntMap;
    private int[][] map;
    private int[][] environment;

    public TileMapSaveData(Map<String, Integer> terrainMap, Map<String, Integer> tileToIntMap, int[][] map, int[][] environment) {
        this.terrainMap = terrainMap;
        this.tileToIntMap = tileToIntMap;
        this.map = map;
        this.environment = environment;
    }

}
