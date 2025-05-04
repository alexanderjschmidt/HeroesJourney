package heroes.journey.systems.triggerable;

import com.artemis.annotations.All;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;

import java.util.UUID;

@All({PossibleActionsComponent.class, IdComponent.class})
public class CooldownSystem extends TriggerableSystem {

    // TODO update to check history and run on Move
    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PossibleActionsComponent cooldownComponent = PossibleActionsComponent.get(world, id);
        cooldownComponent.getCooldowns()
            .entrySet()
            .removeIf(entry -> (entry.setValue(entry.getValue() - 1)) <= 0);
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
