package heroes.journey.components.character;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
public class PlayerComponent extends PooledClonableComponent<PlayerComponent> {

    private UUID playerId;
    private int fame = 0;

    public static PlayerComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(PlayerComponent.class, entityId);
    }

    @Override
    protected void reset() {
        playerId = null;
        fame = 0;
    }

    @Override
    public void copy(PlayerComponent from) {
        playerId = from.playerId;
        fame = from.fame;
    }
}
