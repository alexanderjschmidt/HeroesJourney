package heroes.journey.components;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Accessors(fluent = true, chain = true)
public class NamedComponent extends PooledClonableComponent<NamedComponent> {

    private String name;

    public String toString() {
        return name;
    }

    public static String get(GameWorld world, UUID entityId, String defaultString) {
        NamedComponent name = world.getEntity(NamedComponent.class, entityId);
        return name != null ? name.toString() : defaultString;
    }

    @Override
    protected void reset() {
        name = null;
    }

    @Override
    public void copy(NamedComponent from) {
        name = from.name;
    }
}
