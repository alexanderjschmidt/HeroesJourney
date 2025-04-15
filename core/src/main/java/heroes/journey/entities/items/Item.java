package heroes.journey.entities.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Item implements ItemInterface {

    @NonNull private final String name;
    private final ItemSubType subType;
    private final int weight, value;

    public ItemType getType() {
        return subType.getParentType();
    }

    public String toString() {
        return name;
    }

}
