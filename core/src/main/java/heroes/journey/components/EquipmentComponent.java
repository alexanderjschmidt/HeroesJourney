package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.base.Items;

public class EquipmentComponent implements ClonableComponent<EquipmentComponent> {

    private Item head, chest, legs, boots, handOne, handTwo, accessoryOne, accessoryTwo;

    public void equip(Item item) {
        switch (item.getSubType().toString()) {
            case Items.SWORD:
                handOne = handOne == item ? null : item;
                break;
            case Items.CHEST_ARMOR:
                chest = chest == item ? null : item;
                break;
        }
    }

    public Item getHead() {
        return head;
    }

    public Item getChest() {
        return chest;
    }

    public Item getLegs() {
        return legs;
    }

    public Item getBoots() {
        return boots;
    }

    public Item getHandOne() {
        return handOne;
    }

    public Item getHandTwo() {
        return handTwo;
    }

    public Item getAccessoryOne() {
        return accessoryOne;
    }

    public Item getAccessoryTwo() {
        return accessoryTwo;
    }

    @Override
    public EquipmentComponent clone() {
        EquipmentComponent clone = new EquipmentComponent();
        clone.head = head;
        clone.chest = chest;
        clone.legs = legs;
        clone.boots = boots;
        clone.handOne = handOne;
        clone.handTwo = handTwo;
        clone.accessoryOne = accessoryOne;
        clone.accessoryTwo = accessoryTwo;
        return clone;
    }

    private static final ComponentMapper<EquipmentComponent> mapper = ComponentMapper.getFor(
        EquipmentComponent.class);

    public static EquipmentComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
