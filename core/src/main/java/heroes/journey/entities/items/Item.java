package heroes.journey.entities.items;

public class Item implements ItemInterface {

    private final String name;
    private final ItemSubType type;
    private final int weight, value;

    Item(ItemBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.weight = builder.weight;
        this.value = builder.value;
        ItemManager.get().put(name, this);
    }

    public ItemType getType() {
        return type.getParentType();
    }

    public ItemSubType getSubType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return name;
    }

    public static class Builder extends ItemBuilder<Builder,Item> {

        public Item build() {
            return new Item(this);
        }

    }

    public static abstract class ItemBuilder<B extends ItemBuilder<B,I>, I> {
        private ItemSubType type;
        private String name;
        private int weight = 1, value = 1;

        public B name(String name) {
            this.name = name;
            return self();
        }

        public B type(ItemSubType type) {
            this.type = type;
            return self();
        }

        public B weight(int weight) {
            this.weight = weight;
            return self();
        }

        public B value(int value) {
            this.value = value;
            return self();
        }

        protected B self() {
            return (B)this;
        }

        public abstract I build();
    }

}
