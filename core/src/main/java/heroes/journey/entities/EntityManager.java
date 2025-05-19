package heroes.journey.entities;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.inBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.utils.ai.pathfinding.Cell;

public class EntityManager implements Cloneable {

    private final int width, height;
    private final List<UUID>[][] entitiesLocations;
    private final UUID[][] factionsLocations;

    public EntityManager(int width, int height) {
        this.width = width;
        this.height = height;
        entitiesLocations = new ArrayList[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                entitiesLocations[x][y] = new ArrayList<>();
            }
        }
        factionsLocations = new UUID[width][height];
    }

    public EntityManager clone() {
        return new EntityManager(width, height);
    }

    public List<UUID> get(int x, int y) {
        if (!inBounds(x, y, width, height))
            return null;
        return entitiesLocations[x][y];
    }

    public void addEntity(UUID entityId, int x, int y) {
        entitiesLocations[x][y].add(entityId);
    }

    public void removeEntity(UUID entityId, int x, int y) {
        entitiesLocations[x][y].remove(entityId);
    }

    public Cell moveEntity(UUID entityId, Cell path) {
        Cell end = path.getEnd();
        removeEntity(entityId, path.x, path.y);
        addEntity(entityId, end.x, end.y);
        return end;
    }

    // Location Functions
    public UUID getLocation(int x, int y) {
        if (!inBounds(x, y, width, height))
            return null;
        return factionsLocations[x][y];
    }

    public UUID getLocationUUID(int x, int y) {
        if (!inBounds(x, y, width, height))
            return null;
        return factionsLocations[x][y];
    }

    public void addLocation(UUID faction, int x, int y) {
        factionsLocations[x][y] = faction;
    }

    public void removeLocation(int x, int y) {
        factionsLocations[x][y] = null;
    }
}
