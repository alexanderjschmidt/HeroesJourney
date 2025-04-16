package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.components.utils.Container;
import heroes.journey.entities.items.ItemInterface;

public class InventoryComponent extends Container<ItemInterface,InventoryComponent>
    implements ClonableComponent<InventoryComponent> {

    private int gold;

    public int getWeight() {
        int weight = 0;
        for (ItemInterface item : this.keySet()) {
            weight += item.getWeight() * this.get(item);
        }
        return weight;
    }

    public InventoryComponent clone() {
        return (InventoryComponent)super.clone();
    }

    private static final ComponentMapper<InventoryComponent> mapper = ComponentMapper.getFor(
        InventoryComponent.class);

    public static InventoryComponent get(Entity entity) {
        return mapper.get(entity);
    }

}
