package heroes.journey.systems.triggerable;

import heroes.journey.GameState;
import heroes.journey.systems.TriggerableSystem;

public class EventSystem extends TriggerableSystem {

    private final GameState gameState;

    public EventSystem(GameState gameState) {
        this.gameState = gameState;
    }

    protected void processSystem() {

        setEnabled(false);
    }

    @Override
    protected void process(int entityId) {
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
