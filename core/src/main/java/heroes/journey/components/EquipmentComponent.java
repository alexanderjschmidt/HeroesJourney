package heroes.journey.components;

import static heroes.journey.mods.Registries.ItemManager;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.systems.GameWorld;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class EquipmentComponent extends PooledClonableComponent<EquipmentComponent> {

    private String head, chest, legs, boots, handOne, handTwo, accessoryOne, accessoryTwo;

    public void equip(Item item) {
        switch (item.getSubType().getId()) {
            case "sword":
                handOne = handOne != null && handOne.equals(item.getId()) ? null : item.getId();
                break;
            case "chest_armor":
                chest = chest != null && chest.equals(item.getId()) ? null : item.getId();
                break;
        }
    }

    public Item head() {
        return ItemManager.get(head);
    }

    public Item chest() {
        return ItemManager.get(chest);
    }

    public Item legs() {
        return ItemManager.get(legs);
    }

    public Item boots() {
        return ItemManager.get(boots);
    }

    public Item handOne() {
        return ItemManager.get(handOne);
    }

    public Item handTwo() {
        return ItemManager.get(handTwo);
    }

    public Item accessoryOne() {
        return ItemManager.get(accessoryOne);
    }

    public Item accessoryTwo() {
        return ItemManager.get(accessoryTwo);
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
