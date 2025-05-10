package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import heroes.journey.GameStateSaveData;
import heroes.journey.entities.actions.history.History;
import heroes.journey.tilemap.TileMapSaveData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameStateSaveDataSerializer extends CustomSerializer<GameStateSaveData> {
    @Override
    public void write(Json json, GameStateSaveData saveData, Class knownType) {
        json.writeObjectStart();
        json.writeValue("map", saveData.getMap());
        json.writeValue("history", saveData.getHistory());
        json.writeValue("turn", saveData.getTurn());
        json.writeValue("currentEntity", saveData.getCurrentEntity().toString());
        json.writeArrayStart("entitiesInActionOrder");
        for (UUID id : saveData.getEntitiesInActionOrder()) {
            json.writeValue(id.toString());
        }
        json.writeArrayEnd();
        json.writeObjectEnd();
    }

    @Override
    public GameStateSaveData read(Json json, JsonValue jsonValue, Class type) {
        TileMapSaveData map = json.readValue("map", TileMapSaveData.class, jsonValue);
        History history = json.readValue("history", History.class, jsonValue);
        int turn = json.readValue("turn", Integer.class, jsonValue);
        UUID currentEntity = UUID.fromString(json.readValue("currentEntity", String.class, jsonValue));

        JsonValue uuidArray = jsonValue.get("entitiesInActionOrder");
        List<UUID> entitiesInActionOrder = new ArrayList<>();
        for (JsonValue val = uuidArray.child; val != null; val = val.next) {
            entitiesInActionOrder.add(UUID.fromString(val.asString()));
        }

        return new GameStateSaveData(map, history, turn, currentEntity, entitiesInActionOrder);
    }

    @Override
    public void write(Kryo kryo, Output output, GameStateSaveData saveData) {
        kryo.writeObject(output, saveData.getMap());
        kryo.writeObject(output, saveData.getHistory());
        output.writeInt(saveData.getTurn());
        output.writeString(saveData.getCurrentEntity().toString());

        List<UUID> entities = saveData.getEntitiesInActionOrder();
        output.writeInt(entities.size());
        for (UUID uuid : entities) {
            output.writeString(uuid.toString());
        }
    }

    @Override
    public GameStateSaveData read(Kryo kryo, Input input, Class<GameStateSaveData> type) {
        TileMapSaveData map = kryo.readObject(input, TileMapSaveData.class);
        History history = kryo.readObject(input, History.class);
        int turn = input.readInt();
        UUID currentEntity = UUID.fromString(input.readString());

        int size = input.readInt();
        List<UUID> entitiesInActionOrder = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            entitiesInActionOrder.add(UUID.fromString(input.readString()));
        }

        return new GameStateSaveData(map, history, turn, currentEntity, entitiesInActionOrder);
    }
}
