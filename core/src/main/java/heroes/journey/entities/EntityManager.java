package heroes.journey.entities;

public class EntityManager implements Cloneable {

    private final int width, height;
    // TODO should this just be the uuid?
    private final Integer[][] entitiesLocations;
    private final Integer[][] factionsLocations;

    public EntityManager(int width, int height) {
        this.width = width;
        this.height = height;
        entitiesLocations = new Integer[width][height];
        factionsLocations = new Integer[width][height];
    }

    public EntityManager clone() {
        return new EntityManager(width, height);
    }

    public Integer get(int x, int y) {
        if (x < 0 || y < 0 || y >= height || x >= width)
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

    // Faction Functions
    public Integer getFaction(int x, int y) {
        if (x < 0 || y < 0 || y >= height || x >= width)
            return null;
        return factionsLocations[x][y];
    }

    public void addFaction(int faction, int x, int y) {
        factionsLocations[x][y] = faction;
    }

    public void moveEntity(int currentX, int currentY, int newX, int newY) {
        entitiesLocations[newX][newY] = entitiesLocations[currentX][currentY];
        entitiesLocations[currentX][currentY] = null;
    }
}
