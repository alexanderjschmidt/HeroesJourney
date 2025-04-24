package heroes.journey.components.overworld.place;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
@Getter
public class LocationComponent extends PooledClonableComponent<LocationComponent> {

    public static LocationComponent get(World world, int entityId) {
        return world.getMapper(LocationComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
    }

    @Override
    public void copy(LocationComponent from) {
    }
}
