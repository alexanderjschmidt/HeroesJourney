package heroes.journey.entities.tagging;

import static heroes.journey.registries.Registries.StatManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Attributes extends HashMap<Stat,Integer> {
    private Operation defaultOperation = Operation.ADD;
    // Standard order of operations: ADD, SUBTRACT, MULTIPLY, DIVIDE
    private static final List<Operation> DEFAULT_OPERATIONS_ORDER = Collections.unmodifiableList(
        Arrays.asList(Operation.ADD, Operation.SUBTRACT, Operation.MULTIPLY, Operation.DIVIDE));

    public static List<Operation> getDefaultOperationsOrder() {
        return DEFAULT_OPERATIONS_ORDER;
    }

    public Attributes() {
    }

    public Attributes(Operation defaultOperation) {
        this.defaultOperation = defaultOperation;
    }

    public void setDefaultOperation(Operation op) {
        this.defaultOperation = op;
    }

    public Operation getDefaultOperation() {
        return defaultOperation;
    }

    public Attributes(Map<? extends Stat,? extends Integer> map) {
        super(map);
    }

    public int get(Stat stat) {
        return stat.get(this);
    }

    public int get(String statId) {
        return StatManager.get(statId).get(this);
    }

    public int getDirect(Stat stat) {
        return super.get(stat);
    }

    public int getDirect(String stat) {
        return super.get(StatManager.get(stat));
    }

    public Attributes put(String statId, Integer value) {
        this.put(StatManager.get(statId), value);
        return this;
    }

    public Attributes add(Stat stat, Integer value) {
        return put(stat, value, Operation.ADD);
    }

    public Attributes add(String stat, Integer value) {
        return put(StatManager.get(stat), value, Operation.ADD);
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
        return merge(attributesToMerge, defaultOperation);
    }

    // Multi-merge with order
    public Attributes merge(List<Attributes> attributesList, List<Operation> operationsOrder) {
        List<Operation> order = operationsOrder != null ? operationsOrder : DEFAULT_OPERATIONS_ORDER;
        for (Operation op : order) {
            for (Attributes attrs : attributesList) {
                if (attrs.getDefaultOperation() != op)
                    continue;
                this.merge(attrs, op);
            }
        }
        return this;
    }

    // Overload: use default order
    public Attributes merge(List<Attributes> attributesList) {
        return merge(attributesList, DEFAULT_OPERATIONS_ORDER);
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
