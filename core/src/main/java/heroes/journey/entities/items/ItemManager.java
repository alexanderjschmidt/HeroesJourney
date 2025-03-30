package heroes.journey.entities.items;

import java.util.HashMap;
import java.util.Map;

public class ItemManager extends HashMap<String,Item> {

    private final Map<String,ItemSubType> itemSubTypeManager;

    private static ItemManager itemManager;

    private ItemManager() {
        itemSubTypeManager = new HashMap<>();
    }

    public Map<String,ItemSubType> getSubTypes() {
        return itemSubTypeManager;
    }

    public static ItemManager get() {
        if (itemManager == null)
            itemManager = new ItemManager();
        return itemManager;
    }

}
