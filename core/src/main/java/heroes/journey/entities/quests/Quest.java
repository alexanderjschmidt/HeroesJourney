package heroes.journey.entities.quests;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class Quest {

    private final String name;
    private final BiConsumer<GameState, Entity> onComplete;
    private final BiPredicate<GameState, Entity> isComplete;

    public Quest(Builder builder) {
        this.name = builder.name;
        this.onComplete = builder.onComplete;
        this.isComplete = builder.isComplete;
    }

    public void onComplete(GameState gameState, Entity completer) {
        onComplete.accept(gameState, completer);
    }

    public boolean isComplete(GameState gameState, Entity owner) {
        return isComplete.test(gameState, owner);
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Builder {
        private String name;
        protected BiConsumer<GameState, Entity> onComplete;
        protected BiPredicate<GameState, Entity> isComplete;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder onComplete(BiConsumer<GameState, Entity> onComplete) {
            this.onComplete = onComplete;
            return this;
        }

        public Builder isComplete(BiPredicate<GameState, Entity> isComplete) {
            this.isComplete = isComplete;
            return this;
        }

        public Quest build() {
            return new Quest(this);
        }
    }
}
