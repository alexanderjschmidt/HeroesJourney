package heroes.journey.utils.serializers;

import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.utils.FogMap;
import heroes.journey.entities.Position;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;

import java.util.HashSet;
import java.util.UUID;

public class Serializers {

    public static JsonArtemisSerializer json(GameWorld world) {
        JsonArtemisSerializer jsonSerializer = new JsonArtemisSerializer(world);
        // Utils
        jsonSerializer.register(Position.class, new PositionSerializer());
        jsonSerializer.register(UUID.class, new UUIDSerializer());

        // Components
        jsonSerializer.register(FogMap.class, new FogMapSerializer());

        jsonSerializer.prettyPrint(true);
        System.out.println("Json serializer created");
        return jsonSerializer;
    }

    public static KryoArtemisSerializer kryo(GameWorld world) {
        KryoArtemisSerializer kryoSerializer = new KryoArtemisSerializer(world);
        // Utils
        kryoSerializer.register(Position.class, new PositionSerializer());
        kryoSerializer.register(Fog.class);
        kryoSerializer.register(UUID.class, new UUIDSerializer());

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
