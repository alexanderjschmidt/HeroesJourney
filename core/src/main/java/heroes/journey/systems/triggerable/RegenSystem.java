package heroes.journey.systems.triggerable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.artemis.annotations.All;

import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.modlib.Ids;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;

@All({StatsComponent.class, IdComponent.class})
public class RegenSystem extends TriggerableSystem {
    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID uuid = world.entityMap.entrySet()
            .stream()
            .filter(e -> e.getValue() == entityId)
            .map(e -> e.getKey())
            .findFirst()
            .orElse(null);
        if (uuid == null)
            return;
        StatsComponent statsComponent = world.getEntity(StatsComponent.class, uuid);
        if (statsComponent == null)
            return;
        Attributes attrs = statsComponent.getAttributes();
        for (Stat baseStat : Stat.BASE_STATS) {
            // Find the resource stat for this base stat (resource group + base stat group)
            List<String> resourceGroups = new ArrayList<>();
            resourceGroups.add(Ids.GROUP_RESOURCES);
            resourceGroups.add(baseStat.getGroups().getFirst().getId());
            Stat resourceStat = Stat.getByGroupIds(resourceGroups);
            if (resourceStat == null)
                continue;
            // Find the regen stat (resource group + base stat group + GROUP_REGEN)
            List<String> regenGroups = new ArrayList<>(resourceGroups);
            regenGroups.add(Ids.GROUP_REGEN);
            Stat regenStat = Stat.getByGroupIds(regenGroups);
            if (regenStat == null)
                continue;
            // Find the max stat (resource group + base stat group + GROUP_MAX)
            List<String> maxGroups = new ArrayList<>(resourceGroups);
            maxGroups.add(Ids.GROUP_MAX);
            Stat maxStat = Stat.getByGroupIds(maxGroups);
            if (maxStat == null)
                continue;

            Integer regen = attrs.get(regenStat);
            if (regen == null)
                continue;

            Integer max = attrs.get(maxStat);
            if (max == null)
                continue;

            int regenCalc = (int)((regen / 100f) * max);

            StatsComponent.adjustStat(world, uuid, resourceStat.getId(), regenCalc);
        }
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
