package heroes.journey.components.overworld.place;

import com.artemis.PooledComponent;
import com.artemis.World;

public class CarriageComponent extends PooledComponent {

    public static CarriageComponent get(World world, int entityId) {
        return world.getMapper(CarriageComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
    }

}
