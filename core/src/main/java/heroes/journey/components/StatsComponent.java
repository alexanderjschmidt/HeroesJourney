package heroes.journey.components;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.initializers.base.tags.Groups;
import heroes.journey.registries.Tags;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StatsComponent extends PooledClonableComponent<StatsComponent> {

    private final Attributes attributes = new Attributes();

    private int fame = 0;

    public StatsComponent() {
        // TODO how to auto load things with maxes
        for (Tag stat : Tags.get().get(Groups.BaseStats)) {
            if (!attributes.containsKey(stat))
                attributes.put(stat, stat.getMin());
        }
        for (Tag stat : Tags.get().get(Groups.Renown)) {
            if (!attributes.containsKey(stat))
                attributes.put(stat, 3);
        }
    }

    public static Attributes get(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        return stats == null ? null : stats.getAttributes();
    }

    public static int getFame(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        return stats.fame;
    }

    public static void addFame(GameWorld world, UUID entityId, int amount) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        stats.fame += amount;
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
