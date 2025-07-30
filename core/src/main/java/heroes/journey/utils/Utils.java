package heroes.journey.utils;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.entities.actions.history.ActionRecord;

import java.util.UUID;

import static heroes.journey.mods.Registries.ActionManager;

public class Utils {

    public static UUID getLocation(GameState gameState, UUID entityId) {
        if (entityId == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getLocation(positionComponent.getX(), positionComponent.getY());
    }

    public static UUID getRegion(ActionContext input) {
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

    public static boolean justCompletedAction(GameState gameState, UUID owner, String actionId) {
        Action action = ActionManager.get(actionId);
        return !gameState.getHistory().isEmpty() &&
            gameState.getHistory().getLast() instanceof ActionRecord record && record.getAction() == action &&
            record.getEntity() == owner;
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
