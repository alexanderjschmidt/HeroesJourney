package heroes.journey.tilemap;

import java.util.HashMap;

import heroes.journey.tilemap.wavefunction.Terrain;

public class TerrainManager extends HashMap<String,Terrain> {

    private static TerrainManager terrainManager;

    public static TerrainManager get() {
        if (terrainManager == null)
            terrainManager = new TerrainManager();
        return terrainManager;
    }

}
