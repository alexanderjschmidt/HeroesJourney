package heroes.journey.utils.serializers;

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
        json.writeValue("action",
            actionRecord.getAction().toString());// Assuming getId() returns the original action string
        json.writeValue("input", actionRecord.getInput());
        json.writeObjectEnd();
    }

    @Override
    public ActionRecord read(Json json, JsonValue jsonValue, Class aClass) {
        UUID entity = UUID.fromString(jsonValue.getString("entity"));
        String actionId = jsonValue.getString("action");
        String input = jsonValue.getString("input");
        return new ActionRecord(entity, actionId, input);
    }

    @Override
    public void write(Kryo kryo, Output output, ActionRecord actionRecord) {
        output.writeString(actionRecord.getEntity().toString());
        output.writeString(actionRecord.getAction().toString());
        output.writeString(actionRecord.getInput());
    }

    @Override
    public ActionRecord read(Kryo kryo, Input input, Class<ActionRecord> aClass) {
        UUID entity = UUID.fromString(input.readString());
        String actionId = input.readString();
        String inputStr = input.readString();
        return new ActionRecord(entity, actionId, inputStr);
    }
}
