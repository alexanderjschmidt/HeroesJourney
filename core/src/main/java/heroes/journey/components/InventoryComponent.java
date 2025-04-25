package heroes.journey.components;

import com.artemis.World;
import heroes.journey.components.utils.DefaultContainer;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemManager;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryComponent extends PooledClonableComponent<InventoryComponent> {

    private final DefaultContainer<String> inventory;

    @Getter
    private int gold;

    public InventoryComponent() {
        this.inventory = new DefaultContainer<>();
    }

    public int getWeight() {
        int weight = 0;
        List<Item> items = ItemManager.get(inventory.keySet().stream().toList());
        for (Item item : items) {
            weight += item.getWeight() * inventory.get(item.getName());
        }
        return weight;
    }

    public static InventoryComponent get(World world, int entityId) {
        return world.getMapper(InventoryComponent.class).get(entityId);
    }

    public InventoryComponent add(Item item) {
        return add(item, 1);
    }

    public InventoryComponent add(Item item, int count) {
        inventory.add(item.toString(), count);
        return this;
    }

    public void remove(Item item, int i) {
        inventory.remove(item.toString(), i);
    }

    @Override
    protected void reset() {
        inventory.clear();
    }

    @Override
    public void copy(InventoryComponent from) {
        inventory.putAll(from.inventory);
        gold = from.gold;
    }

    public Map<Item, Integer> getInventory() {
        return inventory.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> ItemManager.get().get(entry.getKey()), Map.Entry::getValue));
    }

    public String toString(Item item) {
        return inventory.toString(item.toString());
    }

    public void adjustGold(int gold) {
        this.gold += gold;
    }
}
