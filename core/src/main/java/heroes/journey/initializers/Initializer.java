package heroes.journey.initializers;

import org.reflections.Reflections;

import java.lang.invoke.MethodHandles;
import java.util.Set;

public class Initializer {

    public static void init() {
        Reflections reflections = new Reflections("heroes.journey.initializers");
        Set<Class<? extends InitializerInterface>> classes = reflections.getSubTypesOf(InitializerInterface.class);

        for (Class<? extends InitializerInterface> clazz : classes) {
            try {
                System.out.println("Loaded " + clazz);
                MethodHandles.lookup().ensureInitialized(clazz);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
