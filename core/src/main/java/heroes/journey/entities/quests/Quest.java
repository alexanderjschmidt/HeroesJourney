package heroes.journey.entities.quests;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class Quest {

    @NonNull private final String name;
    @NonNull private final BiConsumer<GameState,Entity> onComplete;
    @NonNull private final BiPredicate<GameState,Entity> isComplete;

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

}
