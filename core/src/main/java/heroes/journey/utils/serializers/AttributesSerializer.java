package heroes.journey.utils.serializers;

import java.util.Map;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.entities.tagging.Tags;

public class AttributesSerializer extends CustomSerializer<Attributes> {

    @Override
    public void write(Json json, Attributes attributes, Class aClass) {
        json.writeObjectStart();
        for (Map.Entry<Tag,Integer> entry : attributes.entrySet()) {
            json.writeValue(entry.getKey().toString(), entry.getValue());
        }
        json.writeObjectEnd();
    }

    @Override
    public Attributes read(Json json, JsonValue jsonValue, Class aClass) {
        Attributes attributes = new Attributes();
        for (JsonValue entry = jsonValue.child; entry != null; entry = entry.next) {
            Tag tag = Tags.getTag(entry.name());
            int value = entry.asInt();
            attributes.put(tag, value);
        }
        return attributes;
    }

    @Override
    public void write(Kryo kryo, Output output, Attributes attributes) {
        output.writeInt(attributes.size());
        for (Map.Entry<Tag,Integer> entry : attributes.entrySet()) {
            output.writeString(entry.getKey().toString());
            output.writeInt(entry.getValue());
        }
    }

    @Override
    public Attributes read(Kryo kryo, Input input, Class<Attributes> aClass) {
        int size = input.readInt();
        Attributes attributes = new Attributes();
        for (int i = 0; i < size; i++) {
            String tagString = input.readString();
            int value = input.readInt();
            Tag tag = Tags.getTag(tagString);
            attributes.put(tag, value);
        }
        return attributes;
    }
}
