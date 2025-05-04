package heroes.journey.entities;

import java.util.UUID;

import static heroes.journey.initializers.base.Map.inBounds;

public class EntityManager implements Cloneable {

    private final int width, height;
    // TODO should this just be the uuid?
    private final UUID[][] entitiesLocations;
    private final UUID[][] factionsLocations;

    public EntityManager(int width, int height) {
        this.width = width;
        this.height = height;
        entitiesLocations = new UUID[width][height];
        factionsLocations = new UUID[width][height];
    }

    public EntityManager clone() {
        return new EntityManager(width, height);
    }

    public UUID get(int x, int y) {
        if (!inBounds(x, y, width, height))
            return null;
        return entitiesLocations[x][y];
    }

    public void addEntity(UUID entityId, int x, int y) {
        if (entitiesLocations[x][y] == null) {
            entitiesLocations[x][y] = entityId;
        }
    }

    public void removeEntity(int x, int y) {
        entitiesLocations[x][y] = null;
    }

    public void moveEntity(int currentX, int currentY, int newX, int newY) {
        entitiesLocations[newX][newY] = entitiesLocations[currentX][currentY];
        entitiesLocations[currentX][currentY] = null;
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
