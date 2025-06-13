package heroes.journey.entities.items;

import heroes.journey.registries.Registrable;
import lombok.Getter;

@Getter
public class ItemSubType extends Registrable {

    private final String name;
    private final ItemType parentType;

    public ItemSubType(String id, String name, ItemType parentType) {
        super(id);
        this.name = name;
        this.parentType = parentType;
    }

    public String toString() {
        return name;
    }

}
