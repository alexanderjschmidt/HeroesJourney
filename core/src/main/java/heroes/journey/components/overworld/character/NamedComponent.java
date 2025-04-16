package heroes.journey.components.overworld.character;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;

public class NamedComponent implements ClonableComponent<NamedComponent> {

    private final String name;

    public NamedComponent(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private static final ComponentMapper<NamedComponent> mapper = ComponentMapper.getFor(
        NamedComponent.class);

    public static String get(Entity entity, String defaultString) {
        NamedComponent component = mapper.get(entity);
        return component == null ? defaultString : mapper.get(entity).toString();
    }

    @Override
    public NamedComponent clone() {
        return new NamedComponent(name);
    }
}
