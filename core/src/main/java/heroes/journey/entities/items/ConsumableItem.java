package heroes.journey.entities.items;

import heroes.journey.entities.effects.Applyable;

public class ConsumableItem extends Item {

    private final Applyable<ConsumableItem> consume;

    public ConsumableItem(String name, ItemSubType type, int weight, int value) {
        super(name, type, weight, value);
        consume = new Applyable<>(this);
    }

    public Applyable<ConsumableItem> consume() {
        return consume;
    }
}
