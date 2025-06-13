package heroes.journey.entities.items;

import static heroes.journey.registries.Registries.ItemManager;

import java.util.function.Consumer;

import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.tagging.Attributes;

public class ConsumableItem extends Item {
  protected final Consumer<ActionInput> onConsume;

  public ConsumableItem(
      String id,
      String name,
      ItemSubType subType,
      int weight,
      int value,
      Attributes attributes,
      Consumer<ActionInput> onConsume) {
    super(id, name, subType, weight, value, attributes);
    this.onConsume = onConsume;
  }

  public void consume(ActionInput input) {
    onConsume.accept(input);
  }

  public ConsumableItem register() {
    ItemManager.register(this);
    return this;
  }
}
