package heroes.journey.components.overworld.character;

import com.artemis.PooledComponent;
import com.artemis.World;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
public class PlayerComponent extends PooledComponent {

    private String playerId;
    private int fame = 0;

    public static PlayerComponent get(World world, int entityId) {
        return world.getMapper(PlayerComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
        playerId = null;
        fame = 0;
    }
}
