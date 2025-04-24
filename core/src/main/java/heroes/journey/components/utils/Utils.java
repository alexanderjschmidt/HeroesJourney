package heroes.journey.components.utils;

import java.util.ArrayList;
import java.util.List;

import com.artemis.utils.IntBag;

import heroes.journey.GameState;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.place.CarriageComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.quests.Quest;
import heroes.journey.ui.ScrollPaneEntry;

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

    public static List<Action> getCarriageActions(GameState gameState, Integer town, Integer selected) {
        List<Action> questActions = new ArrayList<>();
        IntBag carriagableLocations = gameState.getWorld()
            .getEntitiesWith(LocationComponent.class, CarriageComponent.class, PositionComponent.class);
        for (Integer carriagableLocation : carriagableLocations.getData()) {
            if (carriagableLocation == town) {
                continue;
            }
            PositionComponent positionComponent = PositionComponent.get(gameState.getWorld(), selected);
            PositionComponent positionComponentLocation = PositionComponent.get(gameState.getWorld(),
                carriagableLocation);
            LocationComponent factionComponent = LocationComponent.get(gameState.getWorld(),
                carriagableLocation);
            questActions.add(Action.builder().name(factionComponent.toString()).onSelect((gs, e) -> {
                gameState.getEntities()
                    .moveEntity(positionComponent.getX(), positionComponent.getY(),
                        positionComponentLocation.getX(), positionComponentLocation.getY());
                return "You have arrived at " + factionComponent.toString();
            }).build());
        }
        return questActions;
    }

    public static List<ScrollPaneEntry<Action>> convertToScrollEntries(List<Action> actions) {
        return actions.stream().map(key -> new ScrollPaneEntry<>(key, true)).toList();
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
