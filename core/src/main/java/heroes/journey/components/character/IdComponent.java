package heroes.journey.components.character;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class IdComponent extends PooledClonableComponent<IdComponent> {

    private int id = -1;

    public static IdComponent get(World world, int entityId) {
        return world.getMapper(IdComponent.class).get(entityId);
    }

    public void copy(IdComponent from) {
        id = from.id;
    }

}
