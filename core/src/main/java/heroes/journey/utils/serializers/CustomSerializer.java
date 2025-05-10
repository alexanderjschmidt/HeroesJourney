package heroes.journey.utils.serializers;

import com.badlogic.gdx.utils.Json;
import com.esotericsoftware.kryo.Serializer;

public abstract class CustomSerializer<T> extends Serializer<T> implements Json.Serializer<T> {
}
