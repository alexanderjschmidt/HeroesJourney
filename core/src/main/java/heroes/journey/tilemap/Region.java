package heroes.journey.tilemap;

import static heroes.journey.registries.Registries.RegionManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import heroes.journey.entities.Position;
import heroes.journey.registries.Registrable;
import heroes.journey.utils.worldgen.namegen.MarkovTownNameGenerator;

public class Region extends Registrable {

    private Biome biome;
    private int ring;
    private final Set<Position> tiles = new HashSet<>();
    private final List<UUID> features = new ArrayList<>();
    public Position center;
    public final Set<String> neighborRegionIds = new HashSet<>();

    public Region(String id, int ring) {
        super(id, MarkovTownNameGenerator.get().generateTownName());
        this.ring = ring;
    }

    public void addTile(Position pos) {
        tiles.add(pos);
    }

    public void addNeighbor(String neighborId) {
        if (!Objects.equals(neighborId, this.getId()))
            neighborRegionIds.add(neighborId);
    }

    public Set<Position> getTiles() {
        return tiles;
    }

    public List<UUID> getFeatures() {
        return features;
    }

    public void finalizeCenter() {
        int sumX = 0, sumY = 0;
        for (Position p : tiles) {
            sumX += p.getX();
            sumY += p.getY();
        }
        int count = tiles.size();
        this.center = new Position(sumX / count, sumY / count);
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public int getRing() {
        return ring;
    }

    @NotNull
    @Override
    public Registrable register() {
        return RegionManager.register(this);
    }
}
