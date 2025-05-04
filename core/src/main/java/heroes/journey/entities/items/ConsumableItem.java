package heroes.journey.entities.items;

import heroes.journey.GameState;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
import java.util.function.BiConsumer;

@SuperBuilder
public class ConsumableItem extends Item {

    protected BiConsumer<GameState, UUID> onConsume;

    public void consume(GameState gameState, UUID consumer) {
        onConsume.accept(gameState, consumer);
    }

    public ConsumableItem register() {
        ItemManager.register(this);
        return this;
    }

}
