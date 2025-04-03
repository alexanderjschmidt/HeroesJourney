package heroes.journey.components.utils;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.items.Item;

public class Utils {

    public static Entity getLocationsFaction(GameState gameState, Entity entity) {
        if (entity == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(entity);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getFaction(positionComponent.getX(), positionComponent.getY());
    }

    public static void addItem(Entity entity, Item item, int count) {
        InventoryComponent inventoryComponent = InventoryComponent.get(entity);
        if (inventoryComponent != null) {
            inventoryComponent.add(item, count);
        }
    }

    public static boolean justCompletedAction(GameState gameState, Entity owner, Action action) {
        return !gameState.getHistory().isEmpty() &&
            gameState.getHistory().getLast() instanceof ActionRecord record && record.getAction() == action &&
            gameState.get(record.getEntity()) == owner;
    }

}
