package heroes.journey.components;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.initializers.base.tags.Groups;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.registries.Tags;
import heroes.journey.systems.GameWorld;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StatsComponent extends PooledClonableComponent<StatsComponent> {

    @Setter private int handicapMult = 10;

    private final Attributes attributes = new Attributes();

    public StatsComponent() {
        // TODO how to auto load things with maxes
        attributes.put(Stats.HEALTH, StatsUtils.getMaxHealth(attributes));
        attributes.put(Stats.MANA, StatsUtils.getMaxMana(attributes));
        attributes.put(Stats.STAMINA, StatsUtils.getMaxStamina(attributes));
        for (Tag stat : Tags.get().get(Groups.Stats)) {
            if (!attributes.containsKey(stat))
                attributes.put(stat, stat.getMin());
        }
    }

    public static Attributes get(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        return stats == null ? null : stats.getAttributes();
    }

    public static int getHandicap(GameWorld world, UUID entityId) {
        StatsComponent stats = world.getEntity(StatsComponent.class, entityId);
        return stats == null ? 1 : stats.getHandicapMult();
    }

    @Override
    protected void reset() {
        attributes.clear();
    }

    @Override
    public void copy(StatsComponent from) {
        handicapMult = from.getHandicapMult();
        attributes.putAll(from.attributes);
    }
}
