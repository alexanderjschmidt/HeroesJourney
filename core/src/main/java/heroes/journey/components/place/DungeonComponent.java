package heroes.journey.components.place;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
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

}
