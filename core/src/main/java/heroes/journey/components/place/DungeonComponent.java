package heroes.journey.components.place;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;

public class DungeonComponent extends PooledClonableComponent<DungeonComponent> {

    private UUID[] layout;

    // Secrets/Bonus Rooms
    // private float percentOfTraps; // The left over after these two percentages is empty rooms
    // private List<Entity> possibleTraps;
    // private float environmentalHazardLevel; // Theme of dungeon

    public static DungeonComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(DungeonComponent.class, entityId);
    }

    @Override
    protected void reset() {
        layout = null;
    }

    @Override
    public void copy(DungeonComponent from) {
        layout = from.layout;
    }

    public UUID[] layout() {
        return this.layout;
    }

    public DungeonComponent layout(UUID[] layout) {
        this.layout = layout;
        return this;
    }
}
