package heroes.journey.entities.effects;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Applyable<T> {

    private final List<BiConsumer<GameState, Entity>> completionActions = new ArrayList<>();
    private final T owner;

    public Applyable(T owner) {
        this.owner = owner;
    }

    public void apply(GameState gameState, Entity entity) {
        for (BiConsumer<GameState, Entity> action : completionActions) {
            action.accept(gameState, entity);
        }
    }

    // Add an item, can chain multiple adds
    public Applyable<T> add(BiConsumer<GameState, Entity> action) {
        completionActions.add(action);
        return this;
    }

    // Add the end of a chain of adds, you can build and then grab another applyable
    public T build() {
        return owner;
    }
}
