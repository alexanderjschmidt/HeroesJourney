package heroes.journey.entities.quests;

import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionManager;
import heroes.journey.entities.actions.ClaimQuestAction;
import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

@Builder
public class Quest {

    @NonNull
    private final String name;
    @NonNull
    private final BiConsumer<GameState, UUID> onComplete;
    @NonNull
    private final BiPredicate<GameState, UUID> isComplete;

    public void onComplete(GameState gameState, UUID completer) {
        onComplete.accept(gameState, completer);
    }

    public boolean isComplete(GameState gameState, UUID owner) {
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
        return (ClaimQuestAction) ActionManager.get().get(name);
    }
}
