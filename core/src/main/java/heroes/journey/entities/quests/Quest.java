package heroes.journey.entities.quests;

import heroes.journey.entities.actions.ActionManager;
import heroes.journey.entities.actions.ClaimQuestAction;
import heroes.journey.entities.actions.inputs.ActionInput;
import lombok.Builder;
import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Builder
public class Quest {

    @NonNull
    private final String name;
    @NonNull
    private final Consumer<ActionInput> onComplete;
    @NonNull
    private final Predicate<ActionInput> isComplete;

    public void onComplete(ActionInput input) {
        onComplete.accept(input);
    }

    public boolean isComplete(ActionInput input) {
        return isComplete.test(input);
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
