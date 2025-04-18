package heroes.journey.initializers.base;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.entities.tagging.Tags;

public enum DamageTypes implements Tag {

    PHYSICAL, MAGICAL, TRUE;

    DamageTypes() {
        Tags.register(this);
    }

    @Override
    public Group getGroup() {
        return Groups.Damage;
    }
}
