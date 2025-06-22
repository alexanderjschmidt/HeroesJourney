package heroes.journey.utils.serializers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import heroes.journey.entities.actions.history.ActionRecord;

public class ActionRecordSerializer extends CustomSerializer<ActionRecord> {

    @Override
    public void write(Json json, ActionRecord actionRecord, Class aClass) {
        json.writeObjectStart();
        json.writeValue("entity", actionRecord.getEntity().toString());
        json.writeValue("action", actionRecord.getAction().toString());
        json.writeValue("input", actionRecord.getInput(), Map.class); // write the full map
        json.writeObjectEnd();
    }

    @Override
    public ActionRecord read(Json json, JsonValue jsonValue, Class aClass) {
        UUID entity = UUID.fromString(jsonValue.getString("entity"));
        String actionId = jsonValue.getString("action");

        @SuppressWarnings("unchecked") Map<String,String> inputMap = (Map<String,String>)json.readValue(
            HashMap.class, jsonValue.get("input"));

        return new ActionRecord(entity, actionId, inputMap);
    }

    @Override
    public void write(Kryo kryo, Output output, ActionRecord actionRecord) {
        output.writeString(actionRecord.getEntity().toString());
        output.writeString(actionRecord.getAction().toString());

        Map<String,String> input = actionRecord.getInput();
        output.writeInt(input.size(), true);
        for (Map.Entry<String,String> entry : input.entrySet()) {
            output.writeString(entry.getKey());
            output.writeString(entry.getValue());
        }
    }

    @Override
    public ActionRecord read(Kryo kryo, Input input, Class<ActionRecord> aClass) {
        UUID entity = UUID.fromString(input.readString());
        String actionId = input.readString();

        int size = input.readInt(true);
        Map<String,String> inputMap = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            String key = input.readString();
            String value = input.readString();
            inputMap.put(key, value);
        }

        return new ActionRecord(entity, actionId, inputMap);
    }
}
