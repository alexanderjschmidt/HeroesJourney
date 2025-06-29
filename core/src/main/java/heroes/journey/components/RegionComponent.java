package heroes.journey.components;

import static heroes.journey.registries.Registries.BiomeManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.Position;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Biome;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class RegionComponent extends PooledClonableComponent<RegionComponent> {

    private String biome;
    private int ring;
    private final Set<Position> tiles = new HashSet<>();
    private final List<UUID> features = new ArrayList<>();
    public final Set<UUID> neighborRegionIds = new HashSet<>();

    public static RegionComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(RegionComponent.class, entityId);
    }

    public void addTile(Position pos) {
        tiles.add(pos);
    }

    public void addNeighbor(UUID neighborId) {
        neighborRegionIds.add(neighborId);
    }

    public Set<Position> getTiles() {
        return tiles;
    }

    public List<UUID> getFeatures() {
        return features;
    }

    public Biome getBiome() {
        return BiomeManager.get(biome);
    }

    public void setBiome(Biome biome) {
        this.biome = biome.getId();
    }

    @Override
    protected void reset() {
        biome = "";
        ring = -1;
        tiles.clear();
        features.clear();
        neighborRegionIds.clear();
    }

    @Override
    public void copy(RegionComponent from) {
        reset();
        biome = from.biome;
        ring = from.ring;
        tiles.addAll(from.tiles);
        features.addAll(from.features);
        neighborRegionIds.addAll(from.neighborRegionIds);
    }
}
