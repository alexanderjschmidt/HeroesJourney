package heroes.journey.entities.tagging;

import heroes.journey.registries.Registrable;
import heroes.journey.registries.Registries;

public class Group extends Registrable {

    public Group(String id) {
        super(id);
    }

    @Override
    public Group register() {
        return Registries.GroupManager.register(this);
    }
}
