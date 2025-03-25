package heroes.journey.tilemap.wavefunction;

import heroes.journey.tilemap.TerrainManager;

public class Terrain {

    private final String name;
    private final int terrainCost;

    public Terrain(String name, int terrainCost) {
        this.name = name;
        this.terrainCost = terrainCost;
        TerrainManager.get().put(name, this);
    }

    public String toString() {
        return name;
    }

    public int getTerrainCost() {
        return terrainCost;
    }

}
