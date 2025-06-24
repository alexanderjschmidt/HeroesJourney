package heroes.journey.entities.tagging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import heroes.journey.registries.Registrable;
import heroes.journey.registries.Tags;

public class Tag extends Registrable {

    private final int min, max;
    private final List<Group> groups;

    public Tag(@NotNull String id, @Nullable String nameInternal, int min, int max, Group... groups) {
        super(id, nameInternal);
        this.min = min;
        this.max = max;
        this.groups = new ArrayList<>(Arrays.stream(groups).toList());
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
    @Override
    public Tag register() {
        return Tags.register(this);
    }
}
