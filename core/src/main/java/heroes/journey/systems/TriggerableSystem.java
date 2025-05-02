package heroes.journey.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.utils.IntBag;

public abstract class TriggerableSystem extends BaseEntitySystem {

    @Override
    protected void initialize() {
        setEnabled(false);  // ✅ safe here — world is fully initialized.
    }

    protected abstract void process(int entityId);

    public abstract EventTrigger getTrigger();

    protected final void processSystem() {
        IntBag actives = this.subscription.getEntities();
        int[] ids = actives.getData();
        int i = 0;

        for (int s = actives.size(); s > i; ++i) {
            this.process(ids[i]);
        }
        setEnabled(false);
    }

    public enum EventTrigger {
        MOVE, TURN;
    }
}
