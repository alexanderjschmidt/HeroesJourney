package heroes.journey.components.character;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.systems.GameWorld;
import lombok.experimental.Accessors;

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

    public PlayerComponent playerId(UUID playerId) {
        this.playerId = playerId;
        return this;
    }

    public PlayerComponent fame(int fame) {
        this.fame = fame;
        return this;
    }

    public UUID playerId() {
        return this.playerId;
    }

    public int fame() {
        return this.fame;
    }
}
