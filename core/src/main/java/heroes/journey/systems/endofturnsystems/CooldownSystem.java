package heroes.journey.systems.endofturnsystems;

import com.artemis.World;
import com.artemis.annotations.All;

import heroes.journey.components.overworld.character.CooldownComponent;
import heroes.journey.systems.EndOfTurnSystem;

@All({CooldownComponent.class})
public class CooldownSystem extends EndOfTurnSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        CooldownComponent cooldownComponent = CooldownComponent.get(world, entityId);
        cooldownComponent.getCooldowns()
            .entrySet()
            .removeIf(entry -> (entry.setValue(entry.getValue() - 1)) <= 0);
    }
}
