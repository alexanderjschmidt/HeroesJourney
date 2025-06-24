package heroes.journey.entities.tagging;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import heroes.journey.initializers.base.tags.Groups;
import heroes.journey.registries.Tags;

public class ConfluenceTag extends Tag {

    private final Map<Tag,Integer> parts = new HashMap<>();
    private final int totalParts;

    public ConfluenceTag(
        @NotNull String id,
        @Nullable String nameInternal,
        int min,
        int max,
        Tag... groupsFromTags) {
        super(id, nameInternal, min, max, Arrays.stream(groupsFromTags)
            .flatMap(tag -> tag.getGroups().stream())
            .filter(group -> group != Groups.BaseStats)
            .toArray(Group[]::new));
        int partsCount = 0;
        for (Tag tag : groupsFromTags) {
            if (!parts.containsKey(tag)) {
                parts.put(tag, 0);
            }
            parts.put(tag, parts.get(tag) + 1);
            partsCount++;
        }
        totalParts = partsCount;
    }

    @NotNull
    @Override
    public ConfluenceTag register() {
        return (ConfluenceTag)Tags.register(this);
    }

}
