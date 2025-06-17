package heroes.journey.utils.serializers;

import static heroes.journey.registries.Registries.FeatureTypeManager;

import java.util.UUID;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import heroes.journey.entities.Position;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FeatureType;

public class FeatureSerializer extends CustomSerializer<Feature> {
    @Override
    public void write(Json json, Feature feature, Class aClass) {
        json.writeObjectStart();
        json.writeValue("entityId", feature.entityId.toString());
        json.writeValue("type", feature.type.toString()); // Assuming enum
        json.writeValue("location", feature.location, Position.class);

        json.writeObjectEnd();
    }

    @Override
    public Feature read(Json json, JsonValue jsonValue, Class aClass) {
        UUID entityId = UUID.fromString(jsonValue.getString("entityId"));
        FeatureType type = FeatureTypeManager.get(jsonValue.getString("type"));
        Position location = json.readValue(Position.class, jsonValue.get("location"));

        Feature feature = new Feature(entityId, type, location);
        return feature;
    }

    @Override
    public void write(Kryo kryo, Output output, Feature feature) {
        output.writeString(feature.entityId.toString());
        output.writeString(feature.type.toString());
        kryo.writeObject(output, feature.location);
    }

    @Override
    public Feature read(Kryo kryo, Input input, Class<Feature> aClass) {
        UUID entityId = UUID.fromString(input.readString());
        FeatureType type = FeatureTypeManager.get(input.readString());
        Position location = kryo.readObject(input, Position.class);

        Feature feature = new Feature(entityId, type, location);
        return feature;
    }
}
