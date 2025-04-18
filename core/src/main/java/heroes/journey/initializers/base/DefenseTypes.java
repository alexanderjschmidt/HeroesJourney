package heroes.journey.initializers.base;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.entities.tagging.Tags;

public enum DefenseTypes implements Tag {

    PHYSICAL_DEF, MAGICAL;

    DefenseTypes() {
        Tags.register(this);
    }

    @Override
    public Group getGroup() {
        return Groups.Defense;
    }
}
