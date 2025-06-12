package heroes.journey.entities.items;

import heroes.journey.entities.tagging.Attributes;
import heroes.journey.modding.api.definitions.ItemSubType;
import heroes.journey.modding.api.definitions.ItemType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import static heroes.journey.registries.Registries.ItemManager;

@SuperBuilder
@Getter
public class Item {

    @NonNull
    private final String name;
    private final ItemSubType subType;
    private final int weight, value;
    private final Attributes attributes;

    public ItemType getType() {
        return subType.getParentType();
    }

    public String toString() {
        return name;
    }

    public Item register() {
        return ItemManager.register(this);
    }

}
