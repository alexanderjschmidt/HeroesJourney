package heroes.journey.entities.quests;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionManager;
import heroes.journey.entities.actions.ClaimQuestAction;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class Quest {

    @NonNull private final String name;
    @NonNull private final BiConsumer<GameState,Integer> onComplete;
    @NonNull private final BiPredicate<GameState,Integer> isComplete;

    public void onComplete(GameState gameState, Integer completer) {
        onComplete.accept(gameState, completer);
    }

    public boolean isComplete(GameState gameState, Integer owner) {
        return isComplete.test(gameState, owner);
    }

    @Override
    public String toString() {
        return name;
    }

    public Quest register() {
        ClaimQuestAction.builder().name(this.toString()).quest(this).build().register();
        return QuestManager.register(this);
    }

    public ClaimQuestAction getClaimAction() {
        return (ClaimQuestAction)ActionManager.get().get(name);
    }
}
