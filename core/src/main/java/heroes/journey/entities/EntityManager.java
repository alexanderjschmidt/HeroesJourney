package heroes.journey.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.systems.GameEngine;

import java.util.HashMap;
import java.util.UUID;

public class EntityManager implements Cloneable {

    private final int width, height;
    private final Entity[][] entitiesLocations;
    private final Entity[][] factionsLocations;

    private final HashMap<UUID, Entity> entities;

    public EntityManager(int width, int height) {
        this.width = width;
        this.height = height;
        entitiesLocations = new Entity[width][height];
        factionsLocations = new Entity[width][height];
        entities = new HashMap<>();
    }

    public EntityManager clone() {
        EntityManager clone = new EntityManager(width, height);

        for (UUID id : entities.keySet()) {
            clone.entities.put(id, cloneEntity(clone, entities.get(id)));
        }

        return clone;
    }

    private Entity cloneEntity(EntityManager manager, Entity e) {
        Entity clone = new Entity();
        for (Component component : e.getComponents()) {
            if (component instanceof ClonableComponent<?> clonableComponent) {
                clone.add(clonableComponent.clone());
                if (component instanceof PositionComponent) {
                    manager.addEntity(clone);
                }
            }
        }
        GameEngine.get().addEntity(clone);
        return clone;
    }

    public Entity get(int x, int y) {
        if (x < 0 || y < 0 || y >= height || x >= width)
            return null;
        return entitiesLocations[x][y];
    }

    public void addEntity(Entity e) {
        PositionComponent position = PositionComponent.get(e);
        if (entitiesLocations[position.getX()][position.getY()] == null) {
            entitiesLocations[position.getX()][position.getY()] = e;
        }
    }

    public Entity moveEntity(Entity e, int x, int y) {
        PositionComponent position = PositionComponent.get(e);
        removeEntity(e);
        position.setPos(x, y);
        addEntity(e);
        return e;
    }

    public Entity removeEntity(Entity e) {
        PositionComponent position = PositionComponent.get(e);
        Entity removed = entitiesLocations[position.getX()][position.getY()];
        entitiesLocations[position.getX()][position.getY()] = null;
        return removed;
    }

    // Faction Functions
    public void addFaction(Entity faction, int x, int y) {
        factionsLocations[x][y] = faction;
    }

    public Entity getFaction(int x, int y) {
        if (x < 0 || y < 0 || y >= height || x >= width)
            return null;
        return factionsLocations[x][y];
    }

    // ID Based Functions
    public Entity getEntity(UUID id) {
        return entities.get(id);
    }

    public void registerEntity(UUID id, Entity e) {
        entities.put(id, e);
    }

    public void unregisterEntity(UUID id) {
        entities.remove(id);
    }

    public void print() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Entity e = this.get(x, y);
                if (e != null) {
                    System.out.print("[" + x + ": " + y + "]  \t");
                } else {
                    System.out.print("(" + x + ", " + y + ")  \t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void dispose() {
        for (Entity entity : entities.values()) {
            GameEngine.get().removeEntity(entity);
        }
    }
}
