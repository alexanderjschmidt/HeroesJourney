package heroes.journey.components;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.initializers.base.tags.Groups;
import heroes.journey.registries.Tags;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

@Getter
public class StatsComponent extends PooledClonableComponent<StatsComponent> {

    private final Attributes attributes = new Attributes();

    public StatsComponent() {
        // TODO how to auto load things with maxes
        for (Tag stat : Tags.get().get(Groups.BaseStats)) {
            if (!attributes.containsKey(stat))
                attributes.put(stat, stat.getMin());
        }
    }

    public static Attributes get(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        return stats == null ? null : stats.getAttributes();
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
