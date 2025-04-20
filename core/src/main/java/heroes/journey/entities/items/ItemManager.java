package heroes.journey.entities.items;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemManager extends HashMap<String,Item> {

    private static ItemManager itemManager;

    private ItemManager() {
    }

    public static ItemManager get() {
        if (itemManager == null)
            itemManager = new ItemManager();
        return itemManager;
    }

    public static Item getItem(String itemString) {
        return get().get(itemString);
    }

    public static List<Item> get(List<String> itemStrings) {
        return itemStrings.stream().map(itemManager::get) // get the Action for each string
            .filter(Objects::nonNull) // optionally skip nulls
            .collect(Collectors.toList());
    }

    public static Item register(Item item) {
        get().put(item.getName(), item);
        return item;
    }

}
