package heroes.journey.tilemap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import heroes.journey.entities.Position;

public class Region {
    public final int id;
    private int biome;
    private final Set<Position> tiles = new HashSet<>();
    private final List<Object> features = new ArrayList<>(); // Placeholder
    public Position center;
    public final Set<Integer> neighborRegionIds = new HashSet<>();

    public Region(int id) {
        this.id = id;
    }

    public void addTile(Position pos) {
        tiles.add(pos);
    }

    public void addNeighbor(int neighborId) {
        if (neighborId != this.id)
            neighborRegionIds.add(neighborId);
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
}
