package heroes.journey.components.overworld.place;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class DungeonComponent implements ClonableComponent<DungeonComponent> {

    private Entity[] layout;

    // Secrets/Bonus Rooms
    // private float percentOfTraps; // The left over after these two percentages is empty rooms
    // private List<Entity> possibleTraps;
    // private float environmentalHazardLevel; // Theme of dungeon

    @Override
    public DungeonComponent clone() {
        return this.toBuilder().build();
    }

    private static final ComponentMapper<DungeonComponent> mapper = ComponentMapper.getFor(
        DungeonComponent.class);

    public static DungeonComponent get(Entity entity) {
        return mapper.get(entity);
    }

}
