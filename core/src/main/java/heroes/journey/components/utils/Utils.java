package heroes.journey.components.utils;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.quests.Quest;

public class Utils {

    public static Integer getLocation(GameState gameState, Integer entityId) {
        if (entityId == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), entityId);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getLocation(positionComponent.getX(), positionComponent.getY());
    }

    public static List<Action> getQuestClaimActions(GameState gameState, Integer entityId) {
        QuestsComponent questsComponent = QuestsComponent.get(gameState.getWorld(), entityId);
        List<Action> questActions = new ArrayList<>();
        for (Quest quest : questsComponent.getQuests()) {
            questActions.add(quest.getClaimAction());
        }
        return questActions;
    }

    public static String addItem(GameState gameState, Integer entityId, Item item, int count) {
        InventoryComponent inventoryComponent = InventoryComponent.get(gameState.getWorld(), entityId);
        if (inventoryComponent != null) {
            inventoryComponent.add(item, count);
        }
        return "Gained " + count + " " + item;
    }

    public static String adjustBody(GameState gameState, Integer entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.setBody(statsComponent.getBody() + count);
        return "Successful Workout! Gain 1 Body";
    }

    public static String adjustMind(GameState gameState, Integer entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.setMind(statsComponent.getMind() + count);
        return "Successful Study! Gain 1 Mind";
    }

    public static boolean justCompletedAction(GameState gameState, Integer owner, Action action) {
        return !gameState.getHistory().isEmpty() &&
            gameState.getHistory().getLast() instanceof ActionRecord record && record.getAction() == action &&
            record.getEntity() == owner;
    }

}
