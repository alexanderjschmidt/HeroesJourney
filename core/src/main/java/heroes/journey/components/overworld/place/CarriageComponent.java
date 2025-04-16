package heroes.journey.components.overworld.place;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.interfaces.ClonableComponent;

public class CarriageComponent implements ClonableComponent<CarriageComponent> {

    @Override
    public CarriageComponent clone() {
        return new CarriageComponent();
    }

    private static final ComponentMapper<CarriageComponent> mapper = ComponentMapper.getFor(
        CarriageComponent.class);

    public static CarriageComponent get(Entity entity) {
        return mapper.get(entity);
    }

}
