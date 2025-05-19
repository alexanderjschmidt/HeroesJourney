package heroes.journey.initializers.utils;

import java.util.UUID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.tilemap.FogUtils;
import heroes.journey.ui.HUD;
import heroes.journey.ui.HUDEffectManager;
import heroes.journey.ui.ResourceBar;

public class StatsUtils {

    public static int getSpeed(Attributes stats) {
        stats.put(Stats.BODY, stats.get(Stats.BODY));
        return stats.get(Stats.BODY);
    }

    public static int getVision(Attributes stats) {
        return stats.get(Stats.BODY) + 3;
    }

    public static int getCarryCapacity(Attributes stats) {
        return stats.get(Stats.BODY) * 10;
    }

    public static int getMaxHealth(Attributes stats) {
        return 10;
    }

    public static int getMaxStamina(Attributes stats) {
        return 10;
    }

    public static int getMaxMana(Attributes stats) {
        return 10;
    }

    public static StringResult adjustBody(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.add(Stats.BODY, count);
        FogUtils.updateMap(gameState.getWorld(), gameState, entityId);
        return new StringResult("Successful Workout! Gain 1 Body");
    }

    public static StringResult adjustMind(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.add(Stats.MIND, count);
        return new StringResult("Successful Study! Gain 1 Mind");
    }

    public static void adjustHealth(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return;

        statsComponent.add(Stats.HEALTH, count);

        if (!PlayerInfo.isPlayer(entityId) || !gameState.isGlobal())
            return;
        ResourceBar health = HUD.get().getEntityUI().getHealth();
        Vector2 screenPos = health.localToStageCoordinates(new Vector2(0, 0));
        if (count > 0)
            HUDEffectManager.addTextEffect("+" + count, Color.PINK, (int)(screenPos.x + health.getWidth()),
                (int)screenPos.y);
        else if (count < 0)
            HUDEffectManager.addTextEffect("" + count, Color.RED, (int)(screenPos.x + health.getWidth()),
                (int)screenPos.y);
    }

    public static void adjustMana(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return;

        statsComponent.add(Stats.MANA, count);

        if (!PlayerInfo.isPlayer(entityId) || !gameState.isGlobal())
            return;
        ResourceBar mana = HUD.get().getEntityUI().getMana();
        Vector2 screenPos = mana.localToStageCoordinates(new Vector2(0, 0));
        if (count > 0)
            HUDEffectManager.addTextEffect("+" + count, Color.BLUE, (int)(screenPos.x + mana.getWidth()),
                (int)screenPos.y);
        else if (count < 0)
            HUDEffectManager.addTextEffect("" + count, Color.NAVY, (int)(screenPos.x + mana.getWidth()),
                (int)screenPos.y);
    }

    public static void adjustStamina(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return;

        statsComponent.add(Stats.STAMINA, count);

        if (!PlayerInfo.isPlayer(entityId) || !gameState.isGlobal())
            return;
        ResourceBar stamina = HUD.get().getEntityUI().getStamina();
        Vector2 screenPos = stamina.localToStageCoordinates(new Vector2(0, 0));
        if (count > 0)
            HUDEffectManager.addTextEffect("+" + count, Color.GREEN, (int)(screenPos.x + stamina.getWidth()),
                (int)screenPos.y);
        else if (count < 0)
            HUDEffectManager.addTextEffect("" + count, Color.OLIVE, (int)(screenPos.x + stamina.getWidth()),
                (int)screenPos.y);
    }

}
