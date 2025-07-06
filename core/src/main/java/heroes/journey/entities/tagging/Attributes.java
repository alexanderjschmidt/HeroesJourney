package heroes.journey.entities.tagging;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Attributes extends HashMap<Stat,Integer> {

    public Attributes() {
    }

    public Attributes(Map<? extends Stat,? extends Integer> map) {
        super(map);
    }

    public int get(Stat stat) {
        if (stat.isConfluenceStat()) {
            int total = this.get(stat);
            for (Stat statPart : stat.getParts().keySet()) {
                total += super.get(statPart) * stat.getParts().get(statPart);
            }
            return total / stat.getTotalParts();
        } else {
            return super.get(stat);
        }
    }

    public Attributes add(Stat stat, Integer value) {
        return put(stat, value, Operation.ADD);
    }

    public Attributes add(String stat, Integer value) {
        return put(Stat.getById(stat), value, Operation.ADD);
    }

    public Attributes put(Stat stat, Integer value, Operation operation) {
        if (this.containsKey(stat)) {
            this.compute(stat,
                (k, currentValue) -> Math.clamp(operation.apply(currentValue, value), stat.getMin(),
                    stat.getMax()));
        } else {
            put(stat, value);
        }
        return this;
    }

    public Attributes applyOperation(Integer valueToApply, Operation operation) {
        this.forEach((stat, value) -> this.put(stat, valueToApply, operation));
        return this;
    }

    public Attributes merge(Attributes attributesToMerge) {
        return merge(attributesToMerge, Operation.ADD);
    }

    public Attributes merge(Attributes attributesToMerge, Operation operation) {
        if (attributesToMerge != null) {
            attributesToMerge.forEach((stat, value) -> this.put(stat, value, operation));
        }
        return this;
    }

    public Attributes getTagsWithGroup(Group group) {
        Set<Stat> statsInGroup = Stat.getByGroup(group);
        if (statsInGroup == null) {
            return null;
        }
        Map<Stat,Integer> filteredMap = this.entrySet()
            .stream()
            .filter(entry -> statsInGroup.contains(entry.getKey()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new Attributes(filteredMap);
    }

    public int getTotal() {
        return this.values().stream().mapToInt(Integer::intValue).sum();
    }
}
