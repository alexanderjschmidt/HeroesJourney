package heroes.journey.components;

import static heroes.journey.mods.Registries.BuffManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.modlib.misc.Buff;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

@Getter
public class BuffsComponent extends PooledClonableComponent<BuffsComponent> {

    // For Buffs that run out after x time
    private final Map<String,Integer> timeLeft;

    public BuffsComponent() {
        timeLeft = new HashMap<>();
    }

    public void add(Buff buff) {
        if (buff.getTurnsBuffLasts() > 0) {
            timeLeft.put(buff.toString(), buff.getTurnsBuffLasts());
        }
    }

    public void remove(String buff) {
        timeLeft.remove(buff);
    }

    public List<Attributes> getAttributes() {
        List<Attributes> attributes = new ArrayList<>();
        for (String buffId : timeLeft.keySet()) {
            Buff buff = BuffManager.get(buffId);
            attributes.add((Attributes)buff.getAttributes());
        }
        return attributes;
    }

    public static BuffsComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(BuffsComponent.class, entityId);
    }

    @Override
    public void copy(BuffsComponent from) {
        timeLeft.putAll(from.timeLeft);
    }

    @Override
    public void reset() {
        timeLeft.clear();
    }

    public void decrementTimes() {
        timeLeft.replaceAll((buff, val) -> val - 1);
    }
}
