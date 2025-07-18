package heroes.journey.entities.tagging;

import static heroes.journey.mods.Registries.GroupManager;
import static heroes.journey.mods.Registries.StatManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import heroes.journey.modlib.attributes.IAttributes;
import heroes.journey.modlib.attributes.IGroup;
import heroes.journey.modlib.attributes.IStat;
import heroes.journey.modlib.registries.Registrable;
import kotlin.jvm.functions.Function1;

public class Stat extends Registrable implements IStat {

    private final int min, max;
    private final List<IGroup> groups;
    private final Function1<IAttributes,Integer> calc;

    public Stat(
        @NotNull String id,
        int min,
        int max,
        Function1<IAttributes,Integer> calc,
        List<IGroup> groups) {
        super(id);
        this.min = min;
        this.max = max;
        this.groups = groups;
        this.calc = calc;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public List<IGroup> getGroups() {
        return groups;
    }

    @Override
    public Function1<IAttributes,Integer> getFormula() {
        return calc;
    }

    @Override
    public Stat register() {
        return StatManager.register(this);
    }

    public boolean has(IGroup group) {
        return groups.contains(group);
    }

    @NotNull
    public static Set<Stat> getByGroup(IGroup group) {
        return StatManager.values().stream().filter(stat -> stat.has(group)).collect(Collectors.toSet());
    }

    public static Set<Stat> getByGroup(String groupId) {
        IGroup group = GroupManager.get(groupId);
        return getByGroup(group);
    }

    public int get(Attributes attributes) {
        return calc.invoke(attributes);
    }

    public static int get(String statId, Attributes attributes) {
        Stat stat = StatManager.get(statId);
        return stat.calc.invoke(attributes);
    }
}
