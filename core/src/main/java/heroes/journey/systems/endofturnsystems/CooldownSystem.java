package heroes.journey.systems.endofturnsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import heroes.journey.components.CooldownComponent;
import heroes.journey.systems.EndOfTurnSystem;

public class CooldownSystem extends EndOfTurnSystem {

    public CooldownSystem() {
        super(Family.all(CooldownComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        cooldownComponent.entrySet().removeIf(entry -> (entry.setValue(entry.getValue() - 1)) <= 0);
    }
}
