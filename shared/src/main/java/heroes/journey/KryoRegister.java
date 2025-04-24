package heroes.journey;

import com.esotericsoftware.kryo.Kryo;

public class KryoRegister {

    public static void registerClasses(Kryo kryo) {
        kryo.register(Message.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(java.util.HashSet.class);
        // Register additional message types
    }

}
