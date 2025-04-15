package heroes.journey.entities.items;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemSubType {

    private final ItemType parentType;
    private final String name;

}
