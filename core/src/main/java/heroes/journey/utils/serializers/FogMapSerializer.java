package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import heroes.journey.components.utils.FogMap;
import heroes.journey.tilemap.Fog;

public class FogMapSerializer extends CustomSerializer<FogMap> {

    @Override
    public void write(Json json, FogMap fogMap, Class aClass) {
        json.writeObjectStart();
        Fog[][] fog = fogMap.getFog();

        // Write fog dimensions and data as 2D char array
        json.writeArrayStart("fog");
        for (Fog[] row : fog) {
            StringBuilder sb = new StringBuilder();
            for (Fog f : row) {
                if (f == null) {
                    sb.append(Fog.NONE.name().charAt(0));
                } else {
                    sb.append(f.name().charAt(0));
                }
            }
            json.writeValue(sb.toString());
        }
        json.writeArrayEnd();
        json.writeObjectEnd();
    }

    @Override
    public FogMap read(Json json, JsonValue jsonValue, Class aClass) {
        // Parse fog array
        JsonValue fogJson = jsonValue.get("fog");
        int height = fogJson.size;
        int width = fogJson.get(0).size;

        Fog[][] fog = new Fog[height][width];

        int y = 0;
        for (JsonValue row = fogJson.child; row != null; row = row.next, y++) {
            String rowStr = row.asString();
            for (int x = 0; x < rowStr.length(); x++) {
                char ch = rowStr.charAt(x);
                fog[y][x] = Fog.fogFromChar(ch);
            }
        }

        return new FogMap(fog);
    }

    @Override
    public void write(Kryo kryo, Output output, FogMap fogMap) {
        Fog[][] fog = fogMap.getFog();

        // Write fog dimensions
        int height = fog.length;
        int width = fog[0].length;
        output.writeInt(height);
        output.writeInt(width);

        // Write fog as single characters
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder(width);
            for (int x = 0; x < width; x++) {
                if (fog[y][x] == null) {
                    sb.append(Fog.NONE.name().charAt(0));
                } else {
                    sb.append(fog[y][x].name().charAt(0));
                }
            }
            output.writeString(sb.toString());
        }
    }

    @Override
    public FogMap read(Kryo kryo, Input input, Class<FogMap> aClass) {
        int height = input.readInt();
        int width = input.readInt();

        Fog[][] fog = new Fog[height][width];

        for (int y = 0; y < height; y++) {
            String rowStr = input.readString();
            for (int x = 0; x < width; x++) {
                fog[y][x] = Fog.fogFromChar(rowStr.charAt(x));
            }
        }

        // Reuse Artemis pooled component pattern if needed
        return new FogMap(fog);
    }
}
