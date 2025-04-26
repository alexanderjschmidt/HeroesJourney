package heroes.journey.systems.listeners;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.PossibleActionsComponent;
import heroes.journey.initializers.base.actions.BaseActions;

@All({StatsComponent.class, PossibleActionsComponent.class})
public class StatsActionsListener extends IteratingSystem {

    @Override
    public void inserted(int entityId) {
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(getWorld(), entityId);
        actionComponent.addAction(BaseActions.workout, getWorld(), entityId);
        actionComponent.addAction(BaseActions.study, getWorld(), entityId);
    }

    @Override
    public void removed(int entityId) {
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(getWorld(), entityId);
        actionComponent.removeAction(BaseActions.workout);
        actionComponent.removeAction(BaseActions.study);
    }

    @Override
    protected void process(int i) {
    }
}
