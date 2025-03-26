package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.actions.CooldownAction;

import java.util.HashMap;

public class CooldownComponent extends HashMap<CooldownAction, Integer> implements ClonableComponent<CooldownComponent> {

    public CooldownComponent() {
    }

    public CooldownComponent(CooldownComponent cooldownComponent) {
        super(cooldownComponent);
    }

    @Override
    public CooldownComponent clone() {
        return new CooldownComponent(this);
    }

    private static final ComponentMapper<CooldownComponent> mapper = ComponentMapper.getFor(
        CooldownComponent.class);

    public static CooldownComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
