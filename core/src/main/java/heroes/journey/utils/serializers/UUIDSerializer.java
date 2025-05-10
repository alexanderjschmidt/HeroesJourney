package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.UUID;

public class UUIDSerializer extends CustomSerializer<UUID> {
    @Override
    public void write(Json json, UUID uuid, Class aClass) {
        json.writeValue(uuid.toString());
    }

    @Override
    public UUID read(Json json, JsonValue jsonValue, Class aClass) {
        String id = jsonValue.asString();
        return UUID.fromString(id);
    }

    @Override
    public void write(Kryo kryo, Output output, UUID uuid) {
        output.writeString(uuid.toString());
    }

    @Override
    public UUID read(Kryo kryo, Input input, Class<UUID> aClass) {
        String id = input.readString();
        return UUID.fromString(id);
    }
}
