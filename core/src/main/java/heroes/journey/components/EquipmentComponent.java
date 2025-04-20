package heroes.journey.components;

import com.artemis.PooledComponent;
import com.artemis.World;

import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemManager;
import heroes.journey.initializers.base.Items;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class EquipmentComponent extends PooledComponent {

    private String head, chest, legs, boots, handOne, handTwo, accessoryOne, accessoryTwo;

    public void equip(Item item) {
        switch (item.getSubType().toString()) {
            case Items.SWORD:
                handOne = handOne.equals(item.getName()) ? null : item.getName();
                break;
            case Items.CHEST_ARMOR:
                chest = chest.equals(item.getName()) ? null : item.getName();
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

    public static EquipmentComponent get(World world, int entityId) {
        return world.getMapper(EquipmentComponent.class).get(entityId);
    }

    @Override
    protected void reset() {

    }
}
