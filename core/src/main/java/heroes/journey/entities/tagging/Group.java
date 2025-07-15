package heroes.journey.entities.tagging;

import heroes.journey.registries.Registrable;
import heroes.journey.registries.Registries;
import heroes.journey.modlib.IGroup;

public class Group extends Registrable implements IGroup {

    public Group(String id) {
        super(id);
    }

    @Override
    public Group register() {
        return Registries.GroupManager.register(this);
    }
}
