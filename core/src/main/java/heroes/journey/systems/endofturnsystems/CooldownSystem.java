package heroes.journey.systems.endofturnsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import heroes.journey.components.overworld.character.CooldownComponent;
import heroes.journey.systems.EndOfTurnSystem;
import heroes.journey.systems.GameEngine;

public class CooldownSystem extends EndOfTurnSystem {

    public CooldownSystem(GameEngine engine) {
        super(Family.all(CooldownComponent.class).get(), engine);
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        CooldownComponent cooldownComponent = CooldownComponent.get(entity);
        cooldownComponent.entrySet().removeIf(entry -> (entry.setValue(entry.getValue() - 1)) <= 0);
    }
}
