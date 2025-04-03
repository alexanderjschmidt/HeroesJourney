package heroes.journey.entities.items;

public class ItemSubType {

    private final ItemType parentType;
    private final String name;

    private ItemSubType(Builder builder) {
        this.parentType = builder.parentType;
        this.name = builder.name;
    }

    public ItemType getParentType() {
        return parentType;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Builder {
        private ItemType parentType;
        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parentType(ItemType parentType) {
            this.parentType = parentType;
            return this;
        }

        public ItemSubType build() {
            return new ItemSubType(this);
        }
    }

}
