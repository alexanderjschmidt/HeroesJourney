package heroes.journey.entities.items;

import static heroes.journey.registries.Registries.ItemManager;

import java.util.function.Consumer;

import heroes.journey.entities.actions.inputs.ActionInput;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ConsumableItem extends Item {

    protected Consumer<ActionInput> onConsume;

    public void consume(ActionInput input) {
        onConsume.accept(input);
    }

    public ConsumableItem register() {
        ItemManager.register(this);
        return this;
    }

}
