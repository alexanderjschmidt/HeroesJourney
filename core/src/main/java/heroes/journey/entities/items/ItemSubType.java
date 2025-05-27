package heroes.journey.entities.items;

public record ItemSubType(String name, ItemType parentType) {

    public String toString() {
        return name;
    }

}
