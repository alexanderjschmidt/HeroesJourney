package heroes.journey.systems.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.overworld.character.PossibleActionsComponent;
import heroes.journey.initializers.base.BaseActions;

public class StatsActionsListener implements EntityListener {

    public static Family getFamily() {
        return Family.all(StatsComponent.class, PossibleActionsComponent.class).get();
    }

    @Override
    public void entityAdded(Entity entity) {
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(entity);
        actionComponent.addAction(BaseActions.workout, entity);
        actionComponent.addAction(BaseActions.study, entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
