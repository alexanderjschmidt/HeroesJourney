package heroes.journey.registries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;

public class Tags extends HashMap<Group,Set<Tag>> {

    private static Tags globalTags = new Tags();
    private final Map<String,Tag> tags = new HashMap<>();

    public static Tags get() {
        if (globalTags == null)
            globalTags = new Tags();
        return globalTags;
    }

    public void registerTag(Tag tag) {
        if (!containsKey(tag.getGroup())) {
            put(tag.getGroup(), new HashSet<>());
            tags.put(tag.toString(), tag);
        }
        get(tag.getGroup()).add(tag);
    }

    public static void register(Tag tag) {
        globalTags.registerTag(tag);
    }

    public static Tag getTag(String tag) {
        return get().tags.get(tag);
    }
}
