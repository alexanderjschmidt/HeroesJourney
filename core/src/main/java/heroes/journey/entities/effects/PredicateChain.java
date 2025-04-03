package heroes.journey.entities.effects;

import java.util.function.BiPredicate;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import heroes.journey.GameState;

public class PredicateChain {

    private final ImmutableArray<BiPredicate<GameState,Entity>> predicates;

    private PredicateChain(Array<BiPredicate<GameState,Entity>> completionActionsArray) {
        predicates = new ImmutableArray<>(completionActionsArray);
    }

    public boolean isTrue(GameState gameState, Entity entity) {
        boolean isTrue = true;
        for (BiPredicate<GameState,Entity> action : predicates) {
            isTrue &= action.test(gameState, entity);
        }
        return isTrue;
    }

    public static class Builder<B> {

        private final Array<BiPredicate<GameState,Entity>> predicates = new Array<>();
        private final B owner;

        public Builder(B owner) {
            this.owner = owner;
        }

        // Returns the parent builder for chaining
        public B owner() {
            return owner;
        }

        // Add an item, can chain multiple adds
        public Builder<B> add(BiPredicate<GameState,Entity> action) {
            predicates.add(action);
            return this;
        }

        public PredicateChain build() {
            return new PredicateChain(predicates);
        }
    }
}
