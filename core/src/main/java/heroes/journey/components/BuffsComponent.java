package heroes.journey.components;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.buffs.Buff;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class BuffsComponent extends PooledClonableComponent<BuffsComponent> {

    // For Buffs that run out after x time
    private final Map<String, Integer> timeLeft;
    // For Buffs that run out after x uses
    private final Map<String, Integer> triggerCount;

    public BuffsComponent() {
        timeLeft = new HashMap<>();
        triggerCount = new HashMap<>();
    }

    public void add(Buff buff) {
        if (buff.getTurnsBuffLasts() > 0) {
            timeLeft.put(buff.toString(), buff.getTurnsBuffLasts());
        }
        if (buff.getTimesBuffCanBeUsed() > 0) {
            triggerCount.put(buff.toString(), buff.getTimesBuffCanBeUsed());
        }
    }

    public void remove(String buff) {
        timeLeft.remove(buff);
        triggerCount.remove(buff);
    }

    public static BuffsComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(BuffsComponent.class, entityId);
    }

    @Override
    public void copy(BuffsComponent from) {
        timeLeft.putAll(from.timeLeft);
        triggerCount.putAll(from.triggerCount);
    }

    @Override
    public void reset() {
        timeLeft.clear();
        triggerCount.clear();
    }

    public boolean useBuff(Buff buff) {
        boolean hasBuff = timeLeft.containsKey(buff.toString()) || triggerCount.containsKey(buff.toString());
        if (triggerCount.containsKey(buff.toString())) {
            triggerCount.put(buff.toString(), triggerCount.get(buff.toString()) - 1);
        }
        return hasBuff;
    }

    public void decrementTimes() {
        timeLeft.replaceAll((buff, val) -> val - 1);
    }
}
