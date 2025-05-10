package heroes.journey.systems.triggerable;

import com.artemis.annotations.All;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;

import java.util.UUID;

@All({BuffsComponent.class, IdComponent.class})
public class BuffSystem extends TriggerableSystem {

    // TODO update to check history and run on Move
    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        BuffsComponent buffsComponent = BuffsComponent.get(world, id);
        buffsComponent.decrementTimes();
        for (String buff : buffsComponent.getTimeLeft().keySet()) {
            if (buffsComponent.getTimeLeft().get(buff) == 0) {
                buffsComponent.remove(buff);
            }
        }
        for (String buff : buffsComponent.getTriggerCount().keySet()) {
            if (buffsComponent.getTriggerCount().get(buff) == 0) {
                buffsComponent.remove(buff);
            }
        }
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
