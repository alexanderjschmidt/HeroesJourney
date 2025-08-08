package heroes.journey.systems.triggerable;

import static heroes.journey.mods.Registries.StatManager;

import java.util.List;
import java.util.UUID;

import com.artemis.annotations.All;

import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.attributes.Attributes;
import heroes.journey.modlib.attributes.Relation;
import heroes.journey.modlib.attributes.Stat;
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
        Stat regenParentStat = StatManager.get(Ids.GROUP_REGEN);
        List<Stat> regenStats = regenParentStat.getRelatedStats(Relation.CHILD);
        assert regenStats != null;
        for (Stat regenStat : regenStats) {

            Stat resourceStat = regenStat.getRelation(Relation.RESOURCE);
            if (resourceStat == null)
                continue;

            Stat maxStat = resourceStat.getRelation(Relation.MAX);
            if (maxStat == null)
                continue;

            Integer resource = attrs.get(resourceStat);
            if (resource == null)
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
