package heroes.journey.tilemap.wavefunctiontiles;

import static heroes.journey.registries.Registries.TerrainManager;

public class Terrain {

    private final String name;
    private final int terrainCost;

    public Terrain(String name, int terrainCost) {
        this.name = name;
        this.terrainCost = terrainCost;
        TerrainManager.put(name, this);
    }

    public String toString() {
        return name;
    }

    public int getTerrainCost() {
        return terrainCost;
    }

}
