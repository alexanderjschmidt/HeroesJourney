package heroes.journey.components;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
@Getter
@Setter
public class LocationComponent extends PooledClonableComponent<LocationComponent> {

    private boolean capital = false;

    public static LocationComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(LocationComponent.class, entityId);
    }

    @Override
    protected void reset() {
    }

    @Override
    public void copy(LocationComponent from) {
        capital = from.capital;
    }
}
