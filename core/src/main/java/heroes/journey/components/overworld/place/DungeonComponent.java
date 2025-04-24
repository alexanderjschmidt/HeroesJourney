package heroes.journey.components.overworld.place;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
public class DungeonComponent extends PooledClonableComponent<DungeonComponent> {

    private Integer[] layout;

    // Secrets/Bonus Rooms
    // private float percentOfTraps; // The left over after these two percentages is empty rooms
    // private List<Entity> possibleTraps;
    // private float environmentalHazardLevel; // Theme of dungeon

    public static DungeonComponent get(World world, int entityId) {
        return world.getMapper(DungeonComponent.class).get(entityId);
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
