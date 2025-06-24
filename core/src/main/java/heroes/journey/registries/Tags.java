package heroes.journey.registries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;

public class Tags extends HashMap<Group,Set<Tag>> {

    private static Tags globalTags = new Tags();

    public static Tags get() {
        if (globalTags == null)
            globalTags = new Tags();
        return globalTags;
    }
    
    private final Map<String,Tag> tags = new HashMap<>();

    public void registerTag(Tag tag) {
        List<Group> groups = tag.getGroups();
        for (Group group : groups) {
            if (!containsKey(group)) {
                put(group, new HashSet<>());
                tags.put(tag.toString(), tag);
            }
            get(group).add(tag);
        }
    }

    public static Tag register(Tag tag) {
        globalTags.registerTag(tag);
        return tag;
    }

    public static Tag getTag(String tag) {
        return get().tags.get(tag);
    }
}
