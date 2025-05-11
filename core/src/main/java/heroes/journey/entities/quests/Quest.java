package heroes.journey.entities.quests;

import heroes.journey.entities.actions.inputs.ActionInput;
import lombok.Builder;
import lombok.NonNull;

import java.util.Objects;
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
        return QuestManager.register(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quest quest)) return false;
        return Objects.equals(name, quest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
