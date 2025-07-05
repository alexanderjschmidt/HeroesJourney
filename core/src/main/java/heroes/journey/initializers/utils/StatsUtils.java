package heroes.journey.initializers.utils;

import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.tilemap.FogUtils;

public class StatsUtils {

    public static int getSpeed(Attributes stats) {
        stats.put(Stat.BODY, stats.get(Stat.BODY));
        return stats.get(Stat.BODY);
    }

    public static int getVision(Attributes stats) {
        return stats.get(Stat.BODY) + 3;
    }

    public static int getCarryCapacity(Attributes stats) {
        return stats.get(Stat.BODY) * 10;
    }

    public static StringResult adjustBody(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.add(Stat.BODY, count);
        FogUtils.updateMap(gameState.getWorld(), gameState, entityId);
        return new StringResult("Successful Workout! Gain 1 Body");
    }

    public static StringResult adjustMind(GameState gameState, UUID entityId, int count) {
        Attributes statsComponent = StatsComponent.get(gameState.getWorld(), entityId);
        if (statsComponent == null)
            return null;
        statsComponent.add(Stat.MIND, count);
        return new StringResult("Successful Study! Gain 1 Mind");
    }

}
