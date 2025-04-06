package heroes.journey.components.utils;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
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

    public static String addItem(Entity entity, Item item, int count) {
        InventoryComponent inventoryComponent = InventoryComponent.get(entity);
        if (inventoryComponent != null) {
            inventoryComponent.add(item, count);
        }
        return "Gained " + count + " " + item;
    }

    public static String adjustBody(Entity entity, int count) {
        StatsComponent statsComponent = StatsComponent.get(entity);
        if (statsComponent == null)
            return null;
        statsComponent.setBody(statsComponent.getBody() + 1);
        return "Successful Workout! Gain 1 Body";
    }

    public static String adjustMind(Entity entity, int count) {
        StatsComponent statsComponent = StatsComponent.get(entity);
        if (statsComponent == null)
            return null;
        statsComponent.setMind(statsComponent.getMind() + 1);
        return "Successful Study! Gain 1 Mind";
    }

    public static boolean justCompletedAction(GameState gameState, Entity owner, Action action) {
        return !gameState.getHistory().isEmpty() &&
            gameState.getHistory().getLast() instanceof ActionRecord record && record.getAction() == action &&
            gameState.get(record.getEntity()) == owner;
    }

}
