package heroes.journey.systems;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.States;

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
        System.out.println(this.getClass() + "");
        HUD.get().setState(States.LOCKED);
        super.update(delta);
        setProcessing(false);
        HUD.get().revertToPreviousState();
    }
}
