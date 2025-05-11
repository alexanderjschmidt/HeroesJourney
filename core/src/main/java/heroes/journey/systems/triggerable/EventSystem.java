package heroes.journey.systems.triggerable;

import com.artemis.annotations.Exclude;
import heroes.journey.GameState;
import heroes.journey.components.character.IdComponent;
import heroes.journey.systems.TriggerableSystem;

@Exclude({IdComponent.class})
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
