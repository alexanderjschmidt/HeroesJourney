package heroes.journey.entities.items;

import static heroes.journey.registries.Registries.ItemManager;

import heroes.journey.entities.tagging.Attributes;
import heroes.journey.registries.Registrable;
import lombok.Getter;

@Getter
public class Item extends Registrable {

  private final ItemSubType subType;
  private final int weight;
  private final int value;
  private final Attributes attributes;

  public Item(String id, String name, ItemSubType subType, int weight, int value, Attributes attributes) {
    super(id, name);
    this.subType = subType;
    this.weight = weight;
    this.value = value;
    this.attributes = attributes;
  }

  public ItemType getType() {
    return subType.getParentType();
  }

  public Item register() {
    return ItemManager.register(this);
  }
}
