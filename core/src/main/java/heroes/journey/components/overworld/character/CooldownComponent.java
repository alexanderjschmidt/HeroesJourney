package heroes.journey.components.overworld.character;

import java.util.HashMap;
import java.util.Map;

import com.artemis.PooledComponent;
import com.artemis.World;

import lombok.Getter;

@Getter
public class CooldownComponent extends PooledComponent {

    public final Map<String,Integer> cooldowns;

    public CooldownComponent() {
        cooldowns = new HashMap<>();
    }

    public static CooldownComponent get(World world, int entityId) {
        return world.getMapper(CooldownComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
        cooldowns.clear();
    }
}
