package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import heroes.journey.tilemap.TileMapSaveData;

import java.util.HashMap;
import java.util.Map;

public class TileMapSaveDataSerializer extends CustomSerializer<TileMapSaveData> {
    @Override
    public void write(Json json, TileMapSaveData saveData, Class knownType) {
        json.writeObjectStart();
        json.writeValue("terrainMap", saveData.getTerrainMap(), HashMap.class, Integer.class);
        json.writeValue("tileToIntMap", saveData.getTileToIntMap(), HashMap.class, Integer.class);
        json.writeValue("map", saveData.getMap(), int[][].class);
        json.writeValue("environment", saveData.getEnvironment(), int[][].class);
        json.writeObjectEnd();
    }

    @Override
    public TileMapSaveData read(Json json, JsonValue jsonValue, Class type) {
        Map<String, Integer> terrainMap = json.readValue("terrainMap", HashMap.class, Integer.class, jsonValue);
        Map<String, Integer> tileToIntMap = json.readValue("tileToIntMap", HashMap.class, Integer.class, jsonValue);
        int[][] map = json.readValue("map", int[][].class, jsonValue);
        int[][] environment = json.readValue("environment", int[][].class, jsonValue);
        return new TileMapSaveData(terrainMap, tileToIntMap, map, environment);
    }

    @Override
    public void write(Kryo kryo, Output output, TileMapSaveData saveData) {
        kryo.writeObject(output, saveData.getTerrainMap());
        kryo.writeObject(output, saveData.getTileToIntMap());

        int[][] map = saveData.getMap();
        output.writeInt(map.length);
        output.writeInt(map[0].length);
        for (int[] row : map) {
            for (int cell : row) {
                output.writeInt(cell);
            }
        }

        int[][] env = saveData.getEnvironment();
        for (int[] row : env) {
            for (int cell : row) {
                output.writeInt(cell);
            }
        }
    }

    @Override
    public TileMapSaveData read(Kryo kryo, Input input, Class<TileMapSaveData> type) {
        Map<String, Integer> terrainMap = kryo.readObject(input, HashMap.class);
        Map<String, Integer> tileToIntMap = kryo.readObject(input, HashMap.class);

        int width = input.readInt();
        int height = input.readInt();
        int[][] map = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = input.readInt();
            }
        }

        int[][] env = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                env[x][y] = input.readInt();
            }
        }

        return new TileMapSaveData(terrainMap, tileToIntMap, map, env);
    }
}
