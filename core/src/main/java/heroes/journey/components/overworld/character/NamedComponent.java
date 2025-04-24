package heroes.journey.components.overworld.character;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class NamedComponent extends PooledClonableComponent<NamedComponent> {

    private String name;

    public String toString() {
        return name;
    }

    public static String get(World world, int entityId, String defaultString) {
        NamedComponent name = world.getMapper(NamedComponent.class).get(entityId);
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
