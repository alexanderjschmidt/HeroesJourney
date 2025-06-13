package heroes.journey.entities.items;

import static heroes.journey.registries.Registries.ItemManager;

import heroes.journey.entities.tagging.Attributes;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Item {
    @NonNull private final String name;
    private final ItemSubType subType;
    private final int weight;
    private final int value;
    private final Attributes attributes;

    public Item(String name, ItemSubType subType, int weight, int value, Attributes attributes) {
        this.name = name;
        this.subType = subType;
        this.weight = weight;
        this.value = value;
        this.attributes = attributes;
    }

    public ItemType getType() {
        return subType.parentType();
    }

    public String toString() {
        return name;
    }

    public Item register() {
        return ItemManager.register(this);
    }
}
