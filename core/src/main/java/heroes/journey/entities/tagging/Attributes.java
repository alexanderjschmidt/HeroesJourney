package heroes.journey.entities.tagging;

import static heroes.journey.mods.Registries.StatManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import heroes.journey.modlib.attributes.IAttributes;
import heroes.journey.modlib.attributes.Operation;
import heroes.journey.modlib.attributes.Relation;
import heroes.journey.modlib.attributes.Stat;

// NOTE this has an error in intellij, but it's not real because HashMap covers IAttributes need for Map implementation
public class Attributes extends HashMap<Stat,Integer> implements IAttributes {

    public Attributes() {
    }

    public Attributes(Map<? extends Stat,? extends Integer> map) {
        super(map);
    }

    @Override
    public Integer get(String statId) {
        return this.get(StatManager.get(statId));
    }

    @Override
    public Integer get(Stat stat) {
        if (stat == null)
            return null;

        // Get the base value from the stat's formula
        Integer val = stat.getFormula().invoke(this);
        if (val == null) {
            return null;
        }

        // Apply parent stat value (additive)
        Stat parentStat = stat.getRelation(Relation.PARENT);
        if (parentStat != null) {
            Integer parentVal = get(parentStat);
            if (parentVal != null) {
                val += parentVal;
            }
        }

        // Apply multiplier using the new MULTIPLIER relation
        Stat multiplierStat = stat.getRelation(Relation.MULTIPLIER);
        if (multiplierStat != null) {
            Integer multiplier = get(multiplierStat);
            if (multiplier != null) {
                val = val * multiplier;
            }
        }

        return val;
    }

    @Override
    public Integer getDirect(String statId) {
        Stat stat = StatManager.get(statId);

        return super.get(stat);
    }

    public Attributes put(String statId, boolean cascade) {
        Stat stat = StatManager.get(statId);
        this.put(statId, stat.getDefaultValue(), cascade);
        return this;
    }

    public Attributes put(String statId, Integer value, boolean cascade) {
        Stat stat = StatManager.get(statId);
        super.put(stat, value);
        if (cascade) {
            for (Relation relation : stat.getRelations()) {
                if (relation.isOne()) {
                    Stat relatedStat = stat.getRelation(relation);
                    if (!this.containsKey(relatedStat)) {
                        this.put(relatedStat.getId(), relatedStat.getDefaultValue(), cascade);
                    }
                } else {
                    List<Stat> relatedStats = stat.getRelatedStats(relation);
                    for (Stat relatedStat : relatedStats) {
                        if (!this.containsKey(relatedStat)) {
                            this.put(relatedStat.getId(), relatedStat.getDefaultValue(), cascade);
                        }
                    }
                }
            }
        }
        return this;
    }

    public Attributes put(String statId, Integer value, Operation operation) {
        Stat stat = StatManager.get(statId);
        if (this.containsKey(stat)) {
            this.compute(stat, (k, currentValue) -> Math.clamp(operation.apply(currentValue, value),
                stat.getRelation(this, Relation.MIN), stat.getRelation(this, Relation.MAX)));
        } else {
            super.put(stat, value);
        }
        return this;
    }

    @NotNull
    @Override
    public IAttributes add(@Nullable String statId, int value) {
        return put(statId, value, Operation.ADD);
    }

    public Attributes merge(Attributes attributesToMerge, Operation operation) {
        if (attributesToMerge != null) {
            attributesToMerge.forEach((stat, value) -> this.put(stat.getId(), value, operation));
        }
        return this;
    }

}

