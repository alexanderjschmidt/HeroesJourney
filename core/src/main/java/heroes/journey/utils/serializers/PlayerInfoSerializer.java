package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import heroes.journey.PlayerInfo;

import java.util.UUID;

public class PlayerInfoSerializer extends CustomSerializer<PlayerInfo> {
    @Override
    public void write(Json json, PlayerInfo actionRecord, Class aClass) {
        json.writeObjectStart();
        json.writeValue("uuid", actionRecord.getUuid().toString());
        json.writeValue("playersHero", actionRecord.getPlayersHero().toString());
        json.writeObjectEnd();
    }

    @Override
    public PlayerInfo read(Json json, JsonValue jsonValue, Class aClass) {
        UUID uuid = UUID.fromString(jsonValue.getString("uuid"));
        UUID playersHero = UUID.fromString(jsonValue.getString("playersHero"));
        return new PlayerInfo(uuid, playersHero);
    }

    @Override
    public void write(Kryo kryo, Output output, PlayerInfo actionRecord) {
        output.writeString(actionRecord.getUuid().toString());
        output.writeString(actionRecord.getPlayersHero().toString());
    }

    @Override
    public PlayerInfo read(Kryo kryo, Input input, Class<PlayerInfo> aClass) {
        UUID uuid = UUID.fromString(input.readString());
        UUID playersHero = UUID.fromString(input.readString());
        return new PlayerInfo(uuid, playersHero);
    }
}
