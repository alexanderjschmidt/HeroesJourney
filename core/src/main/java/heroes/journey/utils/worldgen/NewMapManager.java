package heroes.journey.utils.worldgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heroes.journey.GameState;
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
        gameState.getMap().setEnvironment(new Tile[gameState.getWidth()][gameState.getHeight()]);
        for (MapGenerationPhase phase : MapGenerationPhase.values()) {
            long startTimePhase = System.nanoTime();
            for (MapGenerationEffect mapGenerationEffect : mapGenerationEffects.get(phase)) {
                long startTimeEffect = System.nanoTime();
                mapGenerationEffect.apply(gameState);
                System.out.println(mapGenerationEffect + ": " + (System.nanoTime() - startTimeEffect));
            }
            System.out.println(phase + ": " + (System.nanoTime() - startTimePhase));
        }
    }

}
