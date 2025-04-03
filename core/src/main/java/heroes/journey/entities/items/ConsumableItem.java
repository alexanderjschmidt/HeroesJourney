package heroes.journey.entities.items;

import heroes.journey.entities.effects.Applicable;

public class ConsumableItem extends Item {

    private final Applicable<ConsumableItem> consume;

    public ConsumableItem(String name, ItemSubType type, int weight, int value) {
        super(name, type, weight, value);
        consume = new Applicable<>(this);
    }

    public Applicable<ConsumableItem> consume() {
        return consume;
    }
}
