package heroes.journey.components;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.base.Items;
import heroes.journey.systems.GameWorld;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

import static heroes.journey.registries.Registries.ItemManager;

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
        return ItemManager.getOrNull(head);
    }

    public Item chest() {
        return ItemManager.getOrNull(chest);
    }

    public Item legs() {
        return ItemManager.getOrNull(legs);
    }

    public Item boots() {
        return ItemManager.getOrNull(boots);
    }

    public Item handOne() {
        return ItemManager.getOrNull(handOne);
    }

    public Item handTwo() {
        return ItemManager.getOrNull(handTwo);
    }

    public Item accessoryOne() {
        return ItemManager.getOrNull(accessoryOne);
    }

    public Item accessoryTwo() {
        return ItemManager.getOrNull(accessoryTwo);
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
