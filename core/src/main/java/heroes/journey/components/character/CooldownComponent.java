package heroes.journey.components.character;

import java.util.HashMap;
import java.util.Map;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import lombok.Getter;

@Getter
public class CooldownComponent extends PooledClonableComponent<CooldownComponent> {

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

    @Override
    public void copy(CooldownComponent from) {
        cooldowns.putAll(from.cooldowns);
    }
}
