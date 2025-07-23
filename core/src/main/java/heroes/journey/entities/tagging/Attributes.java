package heroes.journey.entities.tagging;

import static heroes.journey.mods.Registries.GroupManager;
import static heroes.journey.mods.Registries.StatManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import heroes.journey.modlib.Ids;
import heroes.journey.modlib.attributes.IAttributes;
import heroes.journey.modlib.attributes.IGroup;
import heroes.journey.modlib.attributes.IStat;
import heroes.journey.modlib.attributes.Operation;

public class Attributes extends HashMap<Stat,Integer> implements IAttributes {

    public Attributes() {
    }

    public Attributes(Map<? extends Stat,? extends Integer> map) {
        super(map);
    }

    // ((stat1 * stat1Mult) + (stat2 * stat2Mult)) * (stat1GlobalMult + stat2GlobalMult)
    // TODO this should also take in strengths and weaknesses and immunities of challenge
    // or for strengths and weaknesses do a attribute merge operation?
    // immunities would need to just make the mults for that stat 0
    public Integer getSum(String... statIds) {
        int val = 0;
        for (String statId : statIds) {
            Integer statVal = this.get(statId);
            val += statVal == null ? 0 : statVal;
        }
        int globalMult = 0;
        for (String statId : statIds) {
            IStat stat = StatManager.get(statId);
            if (stat == null)
                continue;
            List<IGroup> globalMultGroups = new ArrayList<>(stat.getGroups());
            globalMultGroups.add(GroupManager.get(Ids.GROUP_GLOBAL_MULT));
            IStat globalMultStat = Stat.getByGroups(globalMultGroups);
            Integer mult = this.getDirect(globalMultStat);
            if (mult != null) {
                globalMult += mult;
            }
        }
        return globalMult == 0 ? val : val * globalMult;
    }

    @Override
    public Integer get(String statId) {
        return StatManager.get(statId).get(this);
    }

    @Override
    public Integer get(IStat stat) {
        if (stat == null)
            return null;
        return ((Stat)stat).get(this);
    }

    @Override
    public Integer getDirect(String statId) {
        IStat stat = StatManager.get(statId);
        Integer val = super.get(stat);
        if (val == null) {
            System.out.println(this);
            throw new RuntimeException("Could not find stat for " + statId);
        }

        return val;
    }

    @Override
    public Integer getDirect(IStat stat) {
        return super.get(stat);
    }

    public Attributes put(String statId, Integer value) {
        super.put(StatManager.get(statId), value);
        return this;
    }

    public Attributes put(Stat stat, Integer value, Operation operation) {
        if (this.containsKey(stat)) {
            this.compute(stat,
                (k, currentValue) -> Math.clamp(operation.apply(currentValue, value), stat.getMin(this),
                    stat.getMax(this)));
        } else {
            super.put(stat, value);
        }
        return this;
    }

    @NotNull
    @Override
    public IAttributes add(@NotNull IStat stat, int value) {
        return put((Stat)stat, value, Operation.ADD);
    }

    @NotNull
    @Override
    public IAttributes add(@Nullable String stat, int value) {
        return put(StatManager.get(stat), value, Operation.ADD);
    }

    public Attributes merge(Attributes attributesToMerge, Operation operation) {
        if (attributesToMerge != null) {
            attributesToMerge.forEach((stat, value) -> this.put(stat, value, operation));
        }
        return this;
    }
}

