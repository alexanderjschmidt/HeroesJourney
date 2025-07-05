package heroes.journey.systems.listeners;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.systems.GameWorld;

import java.util.UUID;

@All({StatsComponent.class, PossibleActionsComponent.class, IdComponent.class})
public class StatsActionsListener extends IteratingSystem {

    @Override
    public void inserted(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(world, id);
        actionComponent.addAction(BaseActions.workout);
        actionComponent.addAction(BaseActions.study);
    }

    @Override
    public void removed(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        
        // Try to get the ID component - if it doesn't exist, all components are gone
        UUID id = null;
        try {
            id = IdComponent.get(world, entityId);
        } catch (Exception e) {
            return;
        }
        
        // If we have the ID, we can safely access other components
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(world, id);
        actionComponent.removeAction(BaseActions.workout);
        actionComponent.removeAction(BaseActions.study);
    }

    @Override
    protected void process(int i) {
    }
}
