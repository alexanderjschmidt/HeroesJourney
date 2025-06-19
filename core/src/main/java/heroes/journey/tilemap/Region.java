package heroes.journey.tilemap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import heroes.journey.entities.Position;

public class Region {
    public final int id;
    private Biome biome;
    private int ring;
    private final Set<Position> tiles = new HashSet<>();
    private final List<Feature> features = new ArrayList<>();
    public Position center;
    public final Set<Integer> neighborRegionIds = new HashSet<>();

    public Region(int id, int ring) {
        this.id = id;
        this.ring = ring;
    }

    public void addTile(Position pos) {
        tiles.add(pos);
    }

    public void addNeighbor(int neighborId) {
        if (neighborId != this.id)
            neighborRegionIds.add(neighborId);
    }

    public Set<Position> getTiles() {
        return tiles;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String toString() {
        return Integer.toString(id);
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

    public Integer getId() {
        return id;
    }
}
