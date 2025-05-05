package heroes.journey.components.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.quests.Quest;

public class Utils {

    public static UUID getLocation(GameState gameState, UUID entityId) {
        if (entityId == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getLocation(positionComponent.getX(), positionComponent.getY());
    }

    public static List<Action> getQuestClaimActions(GameState gameState, UUID entityId) {
        QuestsComponent questsComponent = QuestsComponent.get(gameState.getWorld(), entityId);
        List<Action> questActions = new ArrayList<>();
        for (Quest quest : questsComponent.getQuests()) {
            questActions.add(quest.getClaimAction());
        }
        return questActions;
    }

    public static StringResult addItem(GameState gameState, UUID entityId, Item item, int count) {
        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), entityId);
        if (inventoryComponent != null) {
            inventoryComponent.add(item, count);
        }
        return new StringResult("Gained " + count + " " + item);
    }

    public static StringResult adjustBody(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.setBody(statsComponent.getBody() + count);
        return new StringResult("Successful Workout! Gain 1 Body");
    }

    public static StringResult adjustMind(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.setMind(statsComponent.getMind() + count);
        return new StringResult("Successful Study! Gain 1 Mind");
    }

    public static boolean justCompletedAction(GameState gameState, UUID owner, Action action) {
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
