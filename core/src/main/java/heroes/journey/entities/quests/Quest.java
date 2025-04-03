package heroes.journey.entities.quests;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.effects.Applicable;
import heroes.journey.entities.effects.PredicateChain;

public class Quest {

    private final String name;
    private final Applicable onComplete;
    private final PredicateChain isComplete;

    public Quest(Builder builder) {
        this.name = builder.name;
        this.onComplete = builder.onComplete.build();
        this.isComplete = builder.isComplete.build();
    }

    public void onComplete(GameState gameState, Entity completer) {
        onComplete.apply(gameState, completer);
    }

    public boolean isComplete(GameState gameState, Entity owner) {
        return isComplete.isTrue(gameState, owner);
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Builder {
        private String name;
        private final Applicable.Builder<Builder> onComplete = new Applicable.Builder<>(this);
        private final PredicateChain.Builder<Builder> isComplete = new PredicateChain.Builder<>(this);

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Applicable.Builder<Builder> onComplete() {
            return onComplete;
        }

        public PredicateChain.Builder<Builder> isComplete() {
            return isComplete;
        }

        public Quest build() {
            return new Quest(this);
        }
    }
}
