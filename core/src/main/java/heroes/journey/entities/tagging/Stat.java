package heroes.journey.entities.tagging;

import static heroes.journey.mods.Registries.GroupManager;
import static heroes.journey.mods.Registries.StatManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import heroes.journey.modlib.Ids;
import heroes.journey.modlib.attributes.IAttributes;
import heroes.journey.modlib.attributes.IGroup;
import heroes.journey.modlib.attributes.IStat;
import heroes.journey.modlib.registries.Registrable;
import kotlin.jvm.functions.Function1;

public class Stat extends Registrable implements IStat {

    private final Integer minValue, maxValue;
    private final List<IGroup> groups;
    private final Function1<IAttributes,Integer> calc;

    public Stat(
        @NotNull String id,
        Integer minValue,
        Integer maxValue,
        Function1<IAttributes,Integer> calc,
        List<IGroup> groups) {
        super(id);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.groups = groups;
        this.calc = calc;
    }

    @Override
    public int getMin() {
        // Try to find a stat with the same groups + GROUP_MIN
        List<IGroup> minGroups = new java.util.ArrayList<>(groups);
        IGroup groupMin = heroes.journey.mods.Registries.GroupManager.get(Ids.GROUP_MIN);
        if (groupMin != null && !minGroups.contains(groupMin))
            minGroups.add(groupMin);
        Stat minStat = Stat.getByGroups(minGroups);
        if (minStat != null && minStat != this) {
            return minStat.getFormula().invoke(null); // null for IAttributes, or pass as needed
        }
        return minValue != null ? minValue : Integer.MIN_VALUE;
    }

    @Override
    public int getMax() {
        // Try to find a stat with the same groups + GROUP_MAX
        List<IGroup> maxGroups = new java.util.ArrayList<>(groups);
        IGroup groupMax = heroes.journey.mods.Registries.GroupManager.get(Ids.GROUP_MAX);
        if (groupMax != null && !maxGroups.contains(groupMax))
            maxGroups.add(groupMax);
        Stat maxStat = Stat.getByGroups(maxGroups);
        if (maxStat != null && maxStat != this) {
            return maxStat.getFormula().invoke(null); // null for IAttributes, or pass as needed
        }
        return maxValue != null ? maxValue : Integer.MAX_VALUE;
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
        Stat sameGroupsStat = getByGroups(groups);
        if (!groups.isEmpty() && sameGroupsStat != null)
            throw new IllegalArgumentException(
                "You cannot have a stat that registers to the same group combinations. " + sameGroupsStat +
                    " already has the combination of groups: " + sameGroupsStat.groups);
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

    public static Stat getByGroups(List<IGroup> groups) {
        if (groups == null || groups.isEmpty())
            return null;
        return StatManager.values()
            .stream()
            .filter(
                stat -> stat.getGroups().size() == groups.size() && stat.getGroups().containsAll(groups) &&
                    groups.containsAll(stat.getGroups()))
            .findFirst()
            .orElse(null);
    }

    public static Stat getByGroupIds(List<String> groupIds) {
        if (groupIds == null || groupIds.isEmpty())
            return null;
        List<IGroup> groups = groupIds.stream()
            .map(GroupManager::get)
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toList());
        return getByGroups(groups);
    }

    public int get(Attributes attributes) {
        return calc.invoke(attributes);
    }

    public static int get(String statId, Attributes attributes) {
        Stat stat = StatManager.get(statId);
        return stat.calc.invoke(attributes);
    }
}
