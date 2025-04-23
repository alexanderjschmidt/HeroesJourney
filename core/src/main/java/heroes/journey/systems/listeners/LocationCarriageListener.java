package heroes.journey.systems.listeners;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.components.overworld.character.PossibleActionsComponent;
import heroes.journey.components.overworld.place.CarriageComponent;
import heroes.journey.components.overworld.place.LocationComponent;
import heroes.journey.initializers.base.BaseActions;

@All({LocationComponent.class, PositionComponent.class, CarriageComponent.class,
    PossibleActionsComponent.class})
public class LocationCarriageListener extends IteratingSystem {

    @Override
    public void inserted(int entityId) {
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(getWorld(), entityId);
        actionComponent.addAction(BaseActions.carriage);
    }

    @Override
    public void removed(int entityId) {
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(getWorld(), entityId);
        actionComponent.removeAction(BaseActions.carriage);
    }

    @Override
    protected void process(int i) {

    }
}
