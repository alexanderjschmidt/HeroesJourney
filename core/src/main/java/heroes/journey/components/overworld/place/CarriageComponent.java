package heroes.journey.components.overworld.place;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;

public class CarriageComponent extends PooledClonableComponent<CarriageComponent> {

    public static CarriageComponent get(World world, int entityId) {
        return world.getMapper(CarriageComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
    }

    @Override
    public void copy(CarriageComponent from) {
    }

}
