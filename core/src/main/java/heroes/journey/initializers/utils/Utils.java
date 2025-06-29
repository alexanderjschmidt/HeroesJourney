package heroes.journey.initializers.utils;

import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.Buff;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionInput;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.items.Item;

public class Utils {

    // This gets the location of the entity included in the input, the entity taking action
    public static UUID getLocation(ActionInput input) {
        return getLocation(input.getGameState(), input.getEntityId());
    }

    public static UUID getLocation(GameState gameState, UUID entityId) {
        if (entityId == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getLocation(positionComponent.getX(), positionComponent.getY());
    }

    public static UUID getRegion(ActionInput input) {
        return getRegion(input.getGameState(), input.getEntityId());
    }

    public static UUID getRegion(GameState gameState, UUID entityId) {
        if (entityId == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        if (positionComponent == null)
            return null;
        return gameState.getMap().getRegionMap()[positionComponent.getX()][positionComponent.getY()];
    }

    public static StringResult addItem(ActionInput input, Item item, int count) {
        return addItem(input.getGameState(), input.getEntityId(), item, count);
    }

    public static StringResult addItem(GameState gameState, UUID entityId, Item item, int count) {
        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), entityId);
        if (inventoryComponent != null) {
            inventoryComponent.add(item, count);
        }
        return new StringResult("Gained " + count + " " + item);
    }

    /**
     * @return true if the buff could be used (combines checking if it has the buff and if so decrements its counter)
     */
    public static boolean useBuff(GameState gameState, UUID entityId, Buff buff) {
        BuffsComponent buffsComponent = BuffsComponent.get(gameState.getWorld(), entityId);
        if (buffsComponent == null)
            return false;
        return buffsComponent.useBuff(buff);
    }

    public static boolean justCompletedAction(GameState gameState, UUID owner, Action action) {
        return !gameState.getHistory().isEmpty() &&
            gameState.getHistory().getLast() instanceof ActionRecord record && record.getAction() == action &&
            record.getEntity() == owner;
    }

    public static void popUp(String message) {

    }

    public static void logTime(String log, long start, long printIfLongerThan) {
        if ((System.nanoTime() - start) / 1_000_000.0 > printIfLongerThan) {
            System.out.println(log + " " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();
            long totalMemory = runtime.totalMemory();

            System.out.printf("Used: %.2f MB | Total: %.2f MB | Max: %.2f MB%n", usedMemory / (1024.0 * 1024),
                totalMemory / (1024.0 * 1024), maxMemory / (1024.0 * 1024));
        }
    }

}
