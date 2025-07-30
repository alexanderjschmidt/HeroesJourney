package heroes.journey.entities.tagging;

import heroes.journey.modlib.attributes.IAttributes;
import heroes.journey.modlib.attributes.IStat;
import heroes.journey.modlib.attributes.Operation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static heroes.journey.mods.Registries.StatManager;

public class Attributes extends HashMap<IStat, Integer> implements IAttributes {

    public Attributes() {
    }

    public Attributes(Map<? extends Stat, ? extends Integer> map) {
        super(map);
    }

    @Override
    public Integer get(String statId) {
        return StatManager.get(statId).get(this);
    }

    @Override
    public Integer get(IStat stat) {
        if (stat == null)
            return null;
        return ((Stat) stat).get(this);
    }

    @Override
    public Integer getDirect(String statId) {
        IStat stat = StatManager.get(statId);

        return super.get(stat);
    }

    @Override
    public Integer getDirect(IStat stat) {
        return super.get(stat);
    }

    public Attributes put(String statId, Integer value) {
        super.put(StatManager.get(statId), value);
        return this;
    }

    public Attributes put(IStat stat, Integer value, Operation operation) {
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
        return put((Stat) stat, value, Operation.ADD);
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

