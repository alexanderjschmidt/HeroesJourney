package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import heroes.journey.modlib.utils.Position;

public class PositionSerializer extends CustomSerializer<Position> {
    @Override
    public void write(Json json, Position position, Class aClass) {
        json.writeObjectStart();
        json.writeValue("x", position.x);
        json.writeValue("y", position.y);
        json.writeObjectEnd();
    }

    @Override
    public Position read(Json json, JsonValue jsonValue, Class aClass) {
        int x = jsonValue.getInt("x", 0);
        int y = jsonValue.getInt("y", 0);
        return new Position(x, y);
    }

    @Override
    public void write(Kryo kryo, Output output, Position position) {
        output.writeInt(position.x);
        output.writeInt(position.y);
    }

    @Override
    public Position read(Kryo kryo, Input input, Class<Position> aClass) {
        int x = input.readInt();
        int y = input.readInt();
        return new Position(x, y);
    }
}
