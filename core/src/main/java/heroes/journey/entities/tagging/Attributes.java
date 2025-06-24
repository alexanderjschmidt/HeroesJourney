package heroes.journey.entities.tagging;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import heroes.journey.registries.Tags;

public class Attributes extends HashMap<Tag,Integer> {

    private final Tags tags;

    public Attributes() {
        this.tags = new Tags();
    }

    public Attributes(Map<? extends Tag,? extends Integer> map) {
        super(map);
        this.tags = new Tags();
        refreshTags();
    }

    private void refreshTags() {
        this.tags.clear();
        this.forEach((tag, val) -> tags.registerTag(tag));
    }

    public Attributes add(Tag tag, Integer value) {
        return put(tag, value, Operation.ADD);
    }

    public Attributes put(Tag tag, Integer value, Operation operation) {
        if (this.containsKey(tag)) {
            this.compute(tag,
                (k, currentValue) -> Math.clamp(operation.apply(currentValue, value), tag.getMin(),
                    tag.getMax()));
        } else {
            put(tag, value);
            tags.registerTag(tag);
        }
        return this;
    }

    public Attributes applyOperation(Integer valueToApply, Operation operation) {
        this.forEach((tag, value) -> this.put(tag, valueToApply, operation));
        return this;
    }

    public Attributes merge(Attributes attributesToMerge) {
        return merge(attributesToMerge, Operation.ADD);
    }

    public Attributes merge(Attributes attributesToMerge, Operation operation) {
        if (attributesToMerge != null) {
            attributesToMerge.forEach((tag, value) -> this.put(tag, value, operation));
        }
        return this;
    }

    public Attributes getTagsWithGroup(Group group) {
        Set<Tag> tagsInGroup = tags.get(group);
        if (tagsInGroup == null) {
            return null;
        }
        Map<Tag,Integer> filteredMap = this.entrySet()
            .stream()
            .filter(entry -> tagsInGroup.contains(entry.getKey()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new Attributes(filteredMap);
    }

    public int getTotal() {
        return this.values().stream().mapToInt(Integer::intValue).sum();
    }
}
