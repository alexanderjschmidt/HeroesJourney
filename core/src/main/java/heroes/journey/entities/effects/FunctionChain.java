package heroes.journey.entities.effects;

import java.util.function.BiFunction;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import heroes.journey.GameState;

public class FunctionChain<R> {

    private final ImmutableArray<BiFunction<GameState,Entity,R>> completionActions;

    private FunctionChain(Array<BiFunction<GameState,Entity,R>> completionActionsArray) {
        completionActions = new ImmutableArray<>(completionActionsArray);
    }

    public R apply(GameState gameState, Entity entity) {
        R returnVal = null;
        for (BiFunction<GameState,Entity,R> action : completionActions) {
            returnVal = action.apply(gameState, entity);
        }
        return returnVal;
    }

    public static class Builder<B, R> {

        private final Array<BiFunction<GameState,Entity,R>> completionActions = new Array<>();
        private final B owner;

        public Builder(B owner) {
            this.owner = owner;
        }

        // Returns the parent builder for chaining
        public B owner() {
            return owner;
        }

        // Add an item, can chain multiple adds
        public Builder<B,R> add(BiFunction<GameState,Entity,R> action) {
            completionActions.add(action);
            return this;
        }

        public FunctionChain<R> build() {
            return new FunctionChain<R>(completionActions);
        }
    }
}
