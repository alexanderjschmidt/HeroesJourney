package heroes.journey.systems.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.components.overworld.character.PossibleActionsComponent;
import heroes.journey.components.overworld.place.CarriageComponent;
import heroes.journey.components.overworld.place.FactionComponent;
import heroes.journey.initializers.base.BaseActions;

public class FactionCarriageListener implements EntityListener {

    public static Family getFamily() {
        return Family.all(FactionComponent.class, PositionComponent.class, CarriageComponent.class, PossibleActionsComponent.class).get();
    }

    @Override
    public void entityAdded(Entity entity) {
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(entity);
        actionComponent.addAction(BaseActions.carriage);
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
