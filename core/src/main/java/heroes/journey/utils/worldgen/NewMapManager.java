package heroes.journey.utils.worldgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heroes.journey.GameState;
import heroes.journey.systems.GameEngine;
import heroes.journey.tilemap.wavefunction.Tile;

public class NewMapManager {

    Map<MapGenerationPhase,List<MapGenerationEffect>> mapGenerationEffects;

    private static NewMapManager newMapManager;

    public static NewMapManager get() {
        if (newMapManager == null)
            newMapManager = new NewMapManager();
        return newMapManager;
    }

    private NewMapManager() {
        mapGenerationEffects = new HashMap<>();
        for (MapGenerationPhase phase : MapGenerationPhase.values()) {
            mapGenerationEffects.put(phase, new ArrayList<>());
        }
    }

    public void addMapGenerationEffect(MapGenerationEffect effect) {
        mapGenerationEffects.get(effect.getPhase()).add(effect);
    }

    public void initMapGeneration(GameState gameState) {
        GameEngine.get().removeAllEntities();
        gameState.getMap().setEnvironment(new Tile[gameState.getWidth()][gameState.getHeight()]);
        for (MapGenerationPhase phase : MapGenerationPhase.values()) {
            for (MapGenerationEffect mapGenerationEffect : mapGenerationEffects.get(phase)) {
                mapGenerationEffect.apply(gameState);
            }
        }
    }

}
