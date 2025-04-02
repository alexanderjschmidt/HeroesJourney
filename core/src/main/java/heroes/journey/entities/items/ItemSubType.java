package heroes.journey.entities.items;

public class ItemSubType {

    private final ItemType type;
    private final String name;

    public ItemSubType(String name, ItemType type) {
        this.type = type;
        this.name = name;
        ItemManager.get().getSubTypes().put(name, this);
    }

    public ItemType getParentType() {
        return type;
    }

    public String toString() {
        return name;
    }

}
