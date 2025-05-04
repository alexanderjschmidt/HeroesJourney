package heroes.journey.components;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemManager;
import heroes.journey.initializers.base.Items;
import heroes.journey.systems.GameWorld;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Accessors(fluent = true, chain = true)
public class EquipmentComponent extends PooledClonableComponent<EquipmentComponent> {

    private String head, chest, legs, boots, handOne, handTwo, accessoryOne, accessoryTwo;

    public void equip(Item item) {
        switch (item.getSubType().toString()) {
            case Items.SWORD:
                handOne = handOne != null && handOne.equals(item.getName()) ? null : item.getName();
                break;
            case Items.CHEST_ARMOR:
                chest = chest != null && chest.equals(item.getName()) ? null : item.getName();
                break;
        }
    }

    public Item head() {
        return ItemManager.getItem(head);
    }

    public Item chest() {
        return ItemManager.getItem(chest);
    }

    public Item legs() {
        return ItemManager.getItem(legs);
    }

    public Item boots() {
        return ItemManager.getItem(boots);
    }

    public Item handOne() {
        return ItemManager.getItem(handOne);
    }

    public Item handTwo() {
        return ItemManager.getItem(handTwo);
    }

    public Item accessoryOne() {
        return ItemManager.getItem(accessoryOne);
    }

    public Item accessoryTwo() {
        return ItemManager.getItem(accessoryTwo);
    }

    public static EquipmentComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(EquipmentComponent.class, entityId);
    }

    @Override
    protected void reset() {

    }

    @Override
    public void copy(EquipmentComponent from) {
        head = from.head;
        chest = from.chest;
        legs = from.legs;
        boots = from.boots;
        handOne = from.handOne;
        handTwo = from.handTwo;
        accessoryOne = from.accessoryOne;
        accessoryTwo = from.accessoryTwo;
    }
}
