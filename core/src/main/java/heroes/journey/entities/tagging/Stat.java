package heroes.journey.entities.tagging;

import static heroes.journey.registries.Registries.GroupManager;
import static heroes.journey.registries.Registries.StatManager;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import heroes.journey.registries.Registrable;

public class Stat extends Registrable {

    private final int min, max;
    private final List<Group> groups;
    private final Function<Attributes,Integer> calc;

    // Constructor for simple stats (no parts)
    public Stat(@NotNull String id, int min, int max, Function<Attributes,Integer> calc, List<Group> groups) {
        super(id);
        this.min = min;
        this.max = max;
        this.groups = groups;
        this.calc = calc;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public boolean has(Group group) {
        return groups.contains(group);
    }

    @NotNull
    public Stat register() {
        return StatManager.register(this);
    }

    // Static methods to replace Tags functionality
    public static Set<Stat> getByGroup(Group group) {
        return StatManager.values().stream().filter(stat -> stat.has(group)).collect(Collectors.toSet());
    }

    public static Set<Stat> getByGroup(String groupId) {
        Group group = GroupManager.get(groupId);
        return StatManager.values().stream().filter(stat -> stat.has(group)).collect(Collectors.toSet());
    }

    public int get(Attributes attributes) {
        return calc.apply(attributes);
    }

    public static int get(String statId, Attributes attributes) {
        Stat stat = StatManager.get(statId);
        return stat.calc.apply(attributes);
    }
}
