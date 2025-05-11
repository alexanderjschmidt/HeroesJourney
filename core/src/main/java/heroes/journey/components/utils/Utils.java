package heroes.journey.components.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.*;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.buffs.Buff;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.quests.Quest;
import heroes.journey.tilemap.FogUtils;
import heroes.journey.ui.HUD;
import heroes.journey.ui.HUDEffectManager;
import heroes.journey.ui.ResourceBar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {

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

    public static List<Action> getQuestClaimActions(GameState gameState, UUID entityId) {
        QuestsComponent questsComponent = QuestsComponent.get(gameState.getWorld(), entityId);
        List<Action> questActions = new ArrayList<>();
        for (Quest quest : questsComponent.getQuests()) {
            questActions.add(quest.getClaimAction());
        }
        return questActions;
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

    public static StringResult adjustBody(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.setBody(statsComponent.getBody() + count);
        FogUtils.updateMap(gameState.getWorld(), gameState, entityId);
        return new StringResult("Successful Workout! Gain 1 Body");
    }

    public static StringResult adjustMind(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.setMind(statsComponent.getMind() + count);
        return new StringResult("Successful Study! Gain 1 Mind");
    }

    public static void adjustHealth(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return;

        statsComponent.adjustHealth(count);

        if (!PlayerInfo.isPlayer(entityId) || !gameState.isGlobal())
            return;
        ResourceBar health = HUD.get().getEntityUI().getHealth();
        Vector2 screenPos = health.localToStageCoordinates(new Vector2(0, 0));
        if (count > 0)
            HUDEffectManager.addTextEffect("+" + count, Color.PINK, (int) (screenPos.x + health.getWidth()),
                (int) screenPos.y);
        else if (count < 0)
            HUDEffectManager.addTextEffect("" + count, Color.RED, (int) (screenPos.x + health.getWidth()),
                (int) screenPos.y);
    }

    public static void adjustMana(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return;

        statsComponent.adjustMana(count);

        if (!PlayerInfo.isPlayer(entityId) || !gameState.isGlobal())
            return;
        ResourceBar mana = HUD.get().getEntityUI().getMana();
        Vector2 screenPos = mana.localToStageCoordinates(new Vector2(0, 0));
        if (count > 0)
            HUDEffectManager.addTextEffect("+" + count, Color.BLUE, (int) (screenPos.x + mana.getWidth()),
                (int) screenPos.y);
        else if (count < 0)
            HUDEffectManager.addTextEffect("" + count, Color.NAVY, (int) (screenPos.x + mana.getWidth()),
                (int) screenPos.y);
    }

    public static void adjustStamina(GameState gameState, UUID entityId, int count) {
        StatsComponent statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return;

        statsComponent.adjustStamina(count);

        if (!PlayerInfo.isPlayer(entityId) || !gameState.isGlobal())
            return;
        ResourceBar stamina = HUD.get().getEntityUI().getStamina();
        Vector2 screenPos = stamina.localToStageCoordinates(new Vector2(0, 0));
        if (count > 0)
            HUDEffectManager.addTextEffect("+" + count, Color.GREEN, (int) (screenPos.x + stamina.getWidth()),
                (int) screenPos.y);
        else if (count < 0)
            HUDEffectManager.addTextEffect("" + count, Color.OLIVE, (int) (screenPos.x + stamina.getWidth()),
                (int) screenPos.y);
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
