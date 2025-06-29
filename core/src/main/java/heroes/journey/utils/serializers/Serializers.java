package heroes.journey.utils.serializers;

import java.util.HashSet;
import java.util.UUID;

import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import heroes.journey.GameStateSaveData;
import heroes.journey.PlayerInfo;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.utils.FogMap;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import heroes.journey.tilemap.TileMapSaveData;

public class Serializers {

    public static JsonArtemisSerializer json(GameWorld world) {
        JsonArtemisSerializer jsonSerializer = new JsonArtemisSerializer(world);
        // Utils
        jsonSerializer.register(Position.class, new PositionSerializer());
        jsonSerializer.register(UUID.class, new UUIDSerializer());
        jsonSerializer.register(Attributes.class, new AttributesSerializer());

        // Components
        jsonSerializer.register(FogMap.class, new FogMapSerializer());

        jsonSerializer.prettyPrint(true);
        System.out.println("Json serializer created");
        return jsonSerializer;
    }

    public static Json jsonGameState() {
        Json json = new Json();
        json.prettyPrint(true);
        json.setSerializer(GameStateSaveData.class, new GameStateSaveDataSerializer());
        json.setSerializer(PlayerInfo.class, new PlayerInfoSerializer());
        json.setSerializer(TileMapSaveData.class, new TileMapSaveDataSerializer());
        json.setSerializer(ActionRecord.class, new ActionRecordSerializer());
        json.setSerializer(UUID.class, new UUIDSerializer());
        json.setSerializer(Position.class, new PositionSerializer());

        json.setOutputType(JsonWriter.OutputType.json); // Pretty JSON, use OutputType.minimal for compact

        return json;
    }

    public static KryoArtemisSerializer kryo(GameWorld world) {
        KryoArtemisSerializer kryoSerializer = new KryoArtemisSerializer(world);
        // Utils
        kryoSerializer.register(Position.class, new PositionSerializer());
        kryoSerializer.register(Fog.class);
        kryoSerializer.register(UUID.class, new UUIDSerializer());
        kryoSerializer.register(Attributes.class, new AttributesSerializer());

        // Components
        kryoSerializer.register(PositionComponent.class);
        kryoSerializer.register(FogMap.class, new FogMapSerializer());

        // Java base classes
        kryoSerializer.register(HashSet.class);

        kryoSerializer.getKryo().setReferences(false);
        // To debug what doesnt have a custom serializer
        // kryoSerializer.getKryo().setRegistrationRequired(true);
        return kryoSerializer;
    }

}
