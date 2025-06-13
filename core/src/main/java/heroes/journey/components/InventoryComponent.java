package heroes.journey.components;

import static heroes.journey.registries.Registries.ItemManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import heroes.journey.components.utils.DefaultContainer;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.systems.GameWorld;
import lombok.Getter;

public class InventoryComponent extends PooledClonableComponent<InventoryComponent> {

    private final DefaultContainer<String> inventory;

    @Getter private int gold;

    public InventoryComponent() {
        this.inventory = new DefaultContainer<>();
    }

    public int getWeight() {
        int weight = 0;
        List<Item> items = ItemManager.get(inventory.keySet().stream().toList());
        for (Item item : items) {
            weight += item.getWeight() * inventory.get(item.getId());
        }
        return weight;
    }

    public static InventoryComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(InventoryComponent.class, entityId);
    }

    public InventoryComponent add(Item item) {
        return add(item, 1);
    }

    public InventoryComponent add(Item item, int count) {
        inventory.add(item.getId(), count);
        return this;
    }

    public void remove(Item item, int i) {
        inventory.remove(item.getId(), i);
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

    public Map<Item,Integer> getInventory() {
        return inventory.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> ItemManager.get(entry.getKey()), Map.Entry::getValue));
    }

    public String toString(Item item) {
        return inventory.toString(item.getId());
    }

    public void adjustGold(int gold) {
        this.gold += gold;
    }
}
