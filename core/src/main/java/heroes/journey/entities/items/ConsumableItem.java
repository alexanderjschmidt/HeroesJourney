package heroes.journey.entities.items;

import java.util.function.BiConsumer;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ConsumableItem extends Item {

    protected BiConsumer<GameState,Entity> onConsume;

    public void consume(GameState gameState, Entity consumer) {
        onConsume.accept(gameState, consumer);
    }

}
