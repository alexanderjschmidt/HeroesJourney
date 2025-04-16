package heroes.journey.components;

import java.util.HashMap;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;

public class ExplorationComponent extends HashMap<Entity,Integer>
    implements ClonableComponent<ExplorationComponent> {

    @Override
    public ExplorationComponent clone() {
        return this.clone();
    }

    private static final ComponentMapper<ExplorationComponent> mapper = ComponentMapper.getFor(
        ExplorationComponent.class);

    public static ExplorationComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
