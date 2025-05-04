package heroes.journey.components.character;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Accessors(fluent = true, chain = true)
public class IdComponent extends PooledClonableComponent<IdComponent> {

    private UUID uuid = UUID.randomUUID();

    public static UUID get(GameWorld world, Integer entityId) {
        return world.getMapper(IdComponent.class).get(entityId).uuid;
    }

    public void copy(IdComponent from) {
        uuid = from.uuid;
    }
}
