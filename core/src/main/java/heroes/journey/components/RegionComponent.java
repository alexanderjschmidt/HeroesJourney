package heroes.journey.components;

import static heroes.journey.mods.Registries.BiomeManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.modlib.utils.Position;
import heroes.journey.modlib.worldgen.Biome;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class RegionComponent extends PooledClonableComponent<RegionComponent> {

    private String biome;
    @Getter private int ring, ringPos;
    private final Set<Position> tiles = new HashSet<>();
    private final List<UUID> features = new ArrayList<>();
    public final Set<UUID> neighborRegionIds = new HashSet<>();
    private final List<UUID> challenges = new ArrayList<>();

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

    public List<UUID> getChallenges() {
        return challenges;
    }

    public void removeChallenge(UUID challengeId) {
        challenges.remove(challengeId);
    }

    public RegionComponent addChallenge(UUID challengeId) {
        challenges.add(challengeId);
        return this;
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
