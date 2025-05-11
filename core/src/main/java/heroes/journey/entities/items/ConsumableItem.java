package heroes.journey.entities.items;

import heroes.journey.entities.actions.inputs.ActionInput;
import lombok.experimental.SuperBuilder;

import java.util.function.Consumer;

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
