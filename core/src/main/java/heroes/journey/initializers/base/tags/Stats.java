package heroes.journey.initializers.base.tags;

import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.entities.tagging.Tags;

public enum Stats implements Tag {

    BODY, MIND, HEALTH, STAMINA, MANA;

    Stats() {
        Tags.register(this);
    }

    @Override
    public int getMin() {
        return 1;
    }

    @Override
    public int getMax() {
        return 10;
    }

    @Override
    public Group getGroup() {
        return Groups.Stats;
    }
}
