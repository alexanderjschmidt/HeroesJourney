package heroes.journey.components.character;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(fluent = true, chain = true)
public class PlayerComponent extends PooledClonableComponent<PlayerComponent> {

    private UUID playerId;

    public static PlayerComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(PlayerComponent.class, entityId);
    }

    @Override
    protected void reset() {
        playerId = null;
    }

    @Override
    public void copy(PlayerComponent from) {
        playerId = from.playerId;
    }

    public PlayerComponent playerId(UUID playerId) {
        this.playerId = playerId;
        return this;
    }

    public PlayerComponent fame(int fame) {
        return this;
    }

    public UUID playerId() {
        return this.playerId;
    }
}
