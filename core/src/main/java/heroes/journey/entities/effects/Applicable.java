package heroes.journey.entities.effects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;

public class Applicable<T> {

    private final List<BiConsumer<GameState,Entity>> completionActions = new ArrayList<>();
    private final T owner;

    public Applicable(T owner) {
        this.owner = owner;
    }

    public void apply(GameState gameState, Entity entity) {
        for (BiConsumer<GameState,Entity> action : completionActions) {
            action.accept(gameState, entity);
        }
    }

    // Add an item, can chain multiple adds
    public Applicable<T> add(BiConsumer<GameState,Entity> action) {
        completionActions.add(action);
        return this;
    }

    // Add the end of a chain of adds, you can build and then grab another applicable
    public T build() {
        return owner;
    }
}
