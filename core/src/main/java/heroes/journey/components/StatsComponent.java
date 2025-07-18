package heroes.journey.components;

import static heroes.journey.mods.Registries.StatManager;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.modlib.Ids;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

@Getter
public class StatsComponent extends PooledClonableComponent<StatsComponent> {

    private final Attributes attributes = new Attributes();

    private int fame = 0;

    public StatsComponent() {
        // TODO how to auto load things with maxes
        for (Stat stat : Stat.getByGroup(Ids.GROUP_BASESTATS)) {
            if (!attributes.containsKey(stat))
                attributes.put(stat, stat.getMin());
        }
        for (Stat stat : Stat.getByGroup(Ids.GROUP_RENOWN)) {
            if (!attributes.containsKey(stat))
                attributes.put(stat, 3);
        }
    }

    public static Attributes get(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        if (stats == null)
            return null;
        Attributes attr = stats.getAttributes();
        BuffsComponent buffs = world.getEntity(BuffsComponent.class, entityId);
        if (buffs != null) {
            attr.merge(buffs.getAttributes());
        }
        return attr;
    }

    public static int getFame(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        return stats.fame;
    }

    public static void addFame(GameWorld world, UUID entityId, int amount) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        stats.fame += amount;
    }

    public static void adjustStat(GameWorld world, UUID entityId, String statId, int count) {
        StatsComponent statsComponent = world.getEntity(StatsComponent.class, entityId);
        if (statsComponent == null)
            return;
        statsComponent.getAttributes().add(StatManager.get(statId), count);
    }

    @Override
    protected void reset() {
        attributes.clear();
    }

    @Override
    public void copy(StatsComponent from) {
        attributes.putAll(from.attributes);
    }
}
