package heroes.journey.initializers.base.tags;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.entities.tagging.Tags;

public enum DefenseTypes implements Tag {

    PHYSICAL_DEF, MAGICAL;

    DefenseTypes() {
        Tags.register(this);
    }

    @Override
    public int getMin() {
        return 0;
    }

    @Override
    public int getMax() {
        return 10;
    }

    @Override
    public Group getGroup() {
        return Groups.Defense;
    }
}
