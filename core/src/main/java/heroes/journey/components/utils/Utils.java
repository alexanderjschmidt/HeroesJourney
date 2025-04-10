package heroes.journey.components.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import heroes.journey.GameState;
import heroes.journey.components.*;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ClaimQuestAction;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.quests.Quest;
import heroes.journey.ui.ScrollPaneEntry;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Entity getLocationsFaction(GameState gameState, Entity entity) {
        if (entity == null)
            return null;
        PositionComponent positionComponent = PositionComponent.get(entity);
        if (positionComponent == null)
            return null;
        return gameState.getEntities().getFaction(positionComponent.getX(), positionComponent.getY());
    }

    public static List<Action> getQuestClaimActions(Entity entity) {
        QuestsComponent questsComponent = QuestsComponent.get(entity);
        List<Action> questActions = new ArrayList<>();
        for (Quest quest : questsComponent) {
            questActions.add(new ClaimQuestAction.Builder().name(quest.toString()).quest(quest).build());
        }
        return questActions;
    }

    public static List<Action> getCarriageActions(GameState gameState, Entity town, Entity selected) {
        List<Action> questActions = new ArrayList<>();
        ImmutableArray<Entity> carriagableLocations = gameState.getEngine().getEntitiesFor(Family.all(FactionComponent.class, CarriageComponent.class, PositionComponent.class).get());
        for (Entity carriagableLocation : carriagableLocations) {
            PositionComponent positionComponent = PositionComponent.get(carriagableLocation);
            FactionComponent factionComponent = FactionComponent.get(carriagableLocation);
            questActions.add(new Action.Builder().name("Travel to " + factionComponent.toString()).onSelect((gs, e) -> {
                gameState.getEntities().moveEntity(selected, positionComponent.getX(), positionComponent.getY());
                return "You have arrived at " + factionComponent.toString();
            }).build());
        }
        return questActions;
    }

    public static List<ScrollPaneEntry<Action>> convertToScrollEntries(List<Action> actions) {
        return actions.stream()
            .map(key -> new ScrollPaneEntry<>(key, true))
            .toList();
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
        statsComponent.setBody(statsComponent.getBody() + count);
        return "Successful Workout! Gain 1 Body";
    }

    public static String adjustMind(Entity entity, int count) {
        StatsComponent statsComponent = StatsComponent.get(entity);
        if (statsComponent == null)
            return null;
        statsComponent.setMind(statsComponent.getMind() + count);
        return "Successful Study! Gain 1 Mind";
    }

    public static boolean justCompletedAction(GameState gameState, Entity owner, Action action) {
        return !gameState.getHistory().isEmpty() &&
            gameState.getHistory().getLast() instanceof ActionRecord record && record.getAction() == action &&
            gameState.get(record.getEntity()) == owner;
    }
}
