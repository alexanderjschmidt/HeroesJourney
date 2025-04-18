package heroes.journey.entities.tagging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Tags extends HashMap<Group,Set<Tag>> {

    private static Tags globalTags = new Tags();

    public static Tags get() {
        if (globalTags == null)
            globalTags = new Tags();
        return globalTags;
    }

    public void registerTag(Tag tag) {
        if (!containsKey(tag.getGroup()))
            put(tag.getGroup(), new HashSet<>());
        get(tag.getGroup()).add(tag);
    }

    public static void register(Tag tag) {
        globalTags.registerTag(tag);
    }
}
