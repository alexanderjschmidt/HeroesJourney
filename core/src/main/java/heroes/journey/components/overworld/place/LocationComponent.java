package heroes.journey.components.overworld.place;

import com.artemis.PooledComponent;
import com.artemis.World;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
@Getter
public class LocationComponent extends PooledComponent {

    public static LocationComponent get(World world, int entityId) {
        return world.getMapper(LocationComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
    }
}
