package heroes.journey.systems.listeners;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.registries.Registries;
import heroes.journey.systems.GameWorld;

import java.util.UUID;

@All({StatsComponent.class, PossibleActionsComponent.class, IdComponent.class})
public class StatsActionsListener extends IteratingSystem {

    @Override
    public void inserted(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PossibleActionsComponent actionComponent = PossibleActionsComponent.get(world, id);
        actionComponent.addAction((heroes.journey.entities.actions.Action) Registries.ActionManager.get("workout"));
        actionComponent.addAction((heroes.journey.entities.actions.Action) Registries.ActionManager.get("study"));
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
        actionComponent.removeAction((heroes.journey.entities.actions.Action) Registries.ActionManager.get("workout"));
        actionComponent.removeAction((heroes.journey.entities.actions.Action) Registries.ActionManager.get("study"));
    }

    @Override
    protected void process(int i) {
    }
}
