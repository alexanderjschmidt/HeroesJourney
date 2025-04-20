package heroes.journey.entities.items;

import java.util.function.BiConsumer;

import heroes.journey.GameState;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ConsumableItem extends Item {

    protected BiConsumer<GameState,Integer> onConsume;

    public void consume(GameState gameState, Integer consumer) {
        onConsume.accept(gameState, consumer);
    }

    public ConsumableItem register() {
        ItemManager.register(this);
        return this;
    }

}
