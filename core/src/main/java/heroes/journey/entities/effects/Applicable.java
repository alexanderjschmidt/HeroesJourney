package heroes.journey.entities.effects;

import java.util.function.BiConsumer;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import heroes.journey.GameState;

public class Applicable {

    private final ImmutableArray<BiConsumer<GameState,Entity>> completionActions;

    private Applicable(Array<BiConsumer<GameState,Entity>> completionActionsArray) {
        completionActions = new ImmutableArray<>(completionActionsArray);
    }

    public void apply(GameState gameState, Entity entity) {
        for (BiConsumer<GameState,Entity> action : completionActions) {
            action.accept(gameState, entity);
        }
    }

    public static class Builder<B> {

        private final Array<BiConsumer<GameState,Entity>> completionActions = new Array<>();
        private final B owner;

        public Builder(B owner) {
            this.owner = owner;
        }

        // Returns the parent builder for chaining
        public B owner() {
            return owner;
        }

        // Add an item, can chain multiple adds
        public Builder<B> add(BiConsumer<GameState,Entity> action) {
            completionActions.add(action);
            return this;
        }

        public Applicable build() {
            return new Applicable(completionActions);
        }
    }
}
