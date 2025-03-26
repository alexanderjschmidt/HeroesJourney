package heroes.journey.systems;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import heroes.journey.ui.HUD;

public abstract class EndOfTurnSystem extends IteratingSystem {

    public EndOfTurnSystem(Family family) {
        super(family);
        setProcessing(false);
        GameEngine.get().endOfTurnSystems.add(this);
    }

    public EndOfTurnSystem(Family family, int priority) {
        super(family, priority);
        setProcessing(false);
        GameEngine.get().endOfTurnSystems.add(this);
    }

    @Override
    public void update(float delta) {
        HUD.HUDState previousState = HUD.get().getState();
        HUD.get().setState(HUD.HUDState.LOCKED);
        super.update(delta);
        setProcessing(false);
        HUD.get().setState(previousState);
    }
}
