package heroes.journey.entities.items;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemSubType {

    private final String name;
    private final ItemType parentType;

    public String toString() {
        return name;
    }

}
