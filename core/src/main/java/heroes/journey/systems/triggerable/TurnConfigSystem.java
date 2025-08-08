package heroes.journey.systems.triggerable;

import com.artemis.annotations.All;

import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;
import heroes.journey.utils.TurnConfigManager;

@All()
public class TurnConfigSystem extends TriggerableSystem {

    protected void processSystem() {
        GameWorld world = (GameWorld)getWorld();
        int currentTurn = world.getGameState().getTurnNumber();

        // Apply turn configurations for the current turn
        TurnConfigManager.INSTANCE.applyTurnConfigurations(currentTurn);
    }

    @Override
    protected void process(int entityId) {
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
