package heroes.journey.initializers;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Initializer {

    public static void init() {
        Reflections reflections = new Reflections("heroes.journey.initializers");
        Set<Class<? extends InitializerInterface>> classes = reflections.getSubTypesOf(InitializerInterface.class);

        for (Class<? extends InitializerInterface> clazz : classes) {
            try {
                InitializerInterface instance = clazz.getDeclaredConstructor().newInstance();
                instance.init();
                System.out.println("Loaded " + clazz);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
