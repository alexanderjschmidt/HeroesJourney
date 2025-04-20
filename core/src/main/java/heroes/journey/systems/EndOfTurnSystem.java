package heroes.journey.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.utils.IntBag;

import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.States;

public abstract class EndOfTurnSystem extends BaseEntitySystem {

    public EndOfTurnSystem(Aspect.Builder aspect) {
        super(aspect);
        setEnabled(false);
    }

    public EndOfTurnSystem() {
    }

    @Override
    protected void initialize() {
        setEnabled(false);  // ✅ safe here — world is fully initialized.
    }

    protected abstract void process(int var1);

    protected final void processSystem() {
        System.out.println(this.getClass() + "");
        HUD.get().setState(States.LOCKED);

        IntBag actives = this.subscription.getEntities();
        int[] ids = actives.getData();
        int i = 0;

        for (int s = actives.size(); s > i; ++i) {
            this.process(ids[i]);
        }

        setEnabled(false);
        HUD.get().revertToPreviousState();
    }
}
