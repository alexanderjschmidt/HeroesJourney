package heroes.journey.initializers.base.tags;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.registries.Tags;

public enum DamageTypes implements Tag {

    PHYSICAL, MAGICAL, TRUE;

    DamageTypes() {
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
        return Groups.Damage;
    }
}
