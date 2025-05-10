package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import heroes.journey.entities.actions.history.ActionRecord;

import java.util.UUID;

public class ActionRecordSerializer extends CustomSerializer<ActionRecord> {
    @Override
    public void write(Json json, ActionRecord actionRecord, Class aClass) {
        json.writeObjectStart();
        json.writeValue("entity", actionRecord.getEntity().toString());
        json.writeValue("action", actionRecord.getAction().toString()); // Assuming getId() returns the original action string
        json.writeObjectEnd();
    }

    @Override
    public ActionRecord read(Json json, JsonValue jsonValue, Class aClass) {
        UUID entity = UUID.fromString(jsonValue.getString("entity"));
        String actionId = jsonValue.getString("action");
        return new ActionRecord(entity, actionId);
    }

    @Override
    public void write(Kryo kryo, Output output, ActionRecord actionRecord) {
        output.writeString(actionRecord.getEntity().toString());
        output.writeString(actionRecord.getAction().toString()); // Or however you get the raw string
    }

    @Override
    public ActionRecord read(Kryo kryo, Input input, Class<ActionRecord> aClass) {
        UUID entity = UUID.fromString(input.readString());
        String actionId = input.readString();
        return new ActionRecord(entity, actionId);
    }
}
