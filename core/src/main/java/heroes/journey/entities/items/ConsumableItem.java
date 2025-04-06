package heroes.journey.entities.items;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;

import java.util.function.BiConsumer;

public class ConsumableItem extends Item {

    protected BiConsumer<GameState, Entity> consume;

    public ConsumableItem(Builder builder) {
        super(builder);
        consume = builder.consume;
    }

    public void consume(GameState gameState, Entity consumer) {
        consume.accept(gameState, consumer);
    }

    public static class Builder extends Item.ItemBuilder<Builder, ConsumableItem> {

        private BiConsumer<GameState, Entity> consume;

        public Builder onConsume(BiConsumer<GameState, Entity> consume) {
            this.consume = consume;
            return this;
        }

        public ConsumableItem build() {
            return new ConsumableItem(this);
        }
    }
}
