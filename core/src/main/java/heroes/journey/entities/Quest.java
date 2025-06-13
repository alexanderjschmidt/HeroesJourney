package heroes.journey.entities;

import static heroes.journey.registries.Registries.QuestManager;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.registries.Registrable;
import lombok.NonNull;

public class Quest extends Registrable {

    @NonNull private final Consumer<ActionInput> onComplete;
    @NonNull private final Predicate<ActionInput> isComplete;
    private final int fameReward;

    public Quest(
        String id,
        String name,
        Consumer<ActionInput> onComplete,
        Predicate<ActionInput> isComplete,
        int fameReward) {
        super(id, name);
        this.onComplete = onComplete;
        this.isComplete = isComplete;
        this.fameReward = fameReward;
    }

    public Quest(
        String id,
        String name,
        Consumer<ActionInput> onComplete,
        Predicate<ActionInput> isComplete) {
        this(id, name, onComplete, isComplete, 0);
    }

    public void onComplete(ActionInput input) {
        PlayerComponent playerComponent = PlayerComponent.get(input.getGameState().getWorld(),
            input.getEntityId());
        if (playerComponent != null) {
            playerComponent.fame(playerComponent.fame() + fameReward);
        }
        onComplete.accept(input);
    }

    public boolean isComplete(ActionInput input) {
        return isComplete.test(input);
    }

    public Quest register() {
        return QuestManager.register(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Quest quest))
            return false;
        return Objects.equals(getId(), quest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
