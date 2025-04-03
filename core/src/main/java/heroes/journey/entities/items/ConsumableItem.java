package heroes.journey.entities.items;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.effects.Applicable;

public class ConsumableItem extends Item {

    private final Applicable consume;

    public ConsumableItem(Builder builder) {
        super(builder);
        consume = builder.consume.build();
    }

    public void consume(GameState gameState, Entity consumer) {
        consume.apply(gameState, consumer);
    }

    public static class Builder extends Item.ItemBuilder<Builder,ConsumableItem> {

        private final Applicable.Builder<Builder> consume = new Applicable.Builder<>(this);

        public Applicable.Builder<Builder> onConsume() {
            return consume;
        }

        public ConsumableItem build() {
            return new ConsumableItem(this);
        }
    }
}
