package heroes.journey.systems.triggerable;

import com.artemis.World;
import com.artemis.annotations.All;

import heroes.journey.components.CooldownComponent;
import heroes.journey.systems.TriggerableSystem;

@All({CooldownComponent.class})
public class CooldownSystem extends TriggerableSystem {

    // TODO update to check history and run on Move
    @Override
    protected void process(int entityId) {
        World world = getWorld();
        CooldownComponent cooldownComponent = CooldownComponent.get(world, entityId);
        cooldownComponent.getCooldowns()
            .entrySet()
            .removeIf(entry -> (entry.setValue(entry.getValue() - 1)) <= 0);
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
