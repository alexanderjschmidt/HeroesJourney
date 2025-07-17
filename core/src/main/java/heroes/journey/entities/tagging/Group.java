package heroes.journey.entities.tagging;

import java.util.Objects;

import heroes.journey.modlib.IGroup;
import heroes.journey.modlib.Registrable;
import heroes.journey.registries.Registries;

public class Group extends Registrable implements IGroup {

    public Group(String id) {
        super(id);
    }

    @Override
    public Group register() {
        return Registries.GroupManager.register(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Group group = (Group)o;
        return getId().equals(group.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
