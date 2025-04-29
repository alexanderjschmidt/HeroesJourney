package heroes.journey.entities;

import static heroes.journey.initializers.base.Map.inBounds;

import java.util.HashMap;
import java.util.Map;

public class EntityManager implements Cloneable {

    private final int width, height;
    // TODO should this just be the uuid?
    private final Integer[][] entitiesLocations;
    private final Integer[][] factionsLocations;

    private int entityCount = 0;
    private final Map<Integer,Integer> entityMap;

    public EntityManager(int width, int height) {
        this.width = width;
        this.height = height;
        entitiesLocations = new Integer[width][height];
        factionsLocations = new Integer[width][height];
        entityMap = new HashMap<>();
    }

    public EntityManager clone() {
        return new EntityManager(width, height);
    }

    public Integer get(int x, int y) {
        if (!inBounds(x, y, width, height))
            return null;
        return entitiesLocations[x][y];
    }

    public void addEntity(int entityId, int x, int y) {
        if (entitiesLocations[x][y] == null) {
            entitiesLocations[x][y] = entityId;
        }
    }

    public Integer removeEntity(int x, int y) {
        Integer removed = entitiesLocations[x][y];
        entitiesLocations[x][y] = null;
        return removed;
    }

    public void moveEntity(int currentX, int currentY, int newX, int newY) {
        entitiesLocations[newX][newY] = entitiesLocations[currentX][currentY];
        entitiesLocations[currentX][currentY] = null;
    }

    // Location Functions
    public Integer getLocation(int x, int y) {
        if (!inBounds(x, y, width, height))
            return null;
        return factionsLocations[x][y];
    }

    public void addLocation(int faction, int x, int y) {
        factionsLocations[x][y] = faction;
    }

    public void removeLocation(int x, int y) {
        Integer removed = factionsLocations[x][y];
        factionsLocations[x][y] = null;
    }

    // Registration
    public int register(int entityId) {
        entityMap.put(entityCount, entityId);
        return entityCount++;
    }

    public int register(int entityId, int id) {
        entityMap.put(id, entityId);
        return id;
    }

    public void unregister(int id) {
        entityMap.remove(id);
    }
}
