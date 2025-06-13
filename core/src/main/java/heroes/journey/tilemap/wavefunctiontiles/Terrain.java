package heroes.journey.tilemap.wavefunctiontiles;

import static heroes.journey.registries.Registries.TerrainManager;

import heroes.journey.registries.Registrable;
import lombok.Getter;

@Getter
public class Terrain extends Registrable {

    private final int terrainCost;

    public Terrain(String id, String name, int terrainCost) {
        super(id, name);
        this.terrainCost = terrainCost;
        TerrainManager.put(name, this);
    }

}
