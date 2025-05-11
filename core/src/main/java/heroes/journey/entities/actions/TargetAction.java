package heroes.journey.entities.actions;

import heroes.journey.GameState;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuperBuilder(toBuilder = true, builderMethodName = "targetBuilder")
public class TargetAction<I> extends Action {

    protected BiFunction<GameState, UUID, List<I>> getTargets;
    protected Function<TargetInput<I>, ActionResult> onSelectTarget;
    @Builder.Default
    protected Function<I, String> getDisplayName = Object::toString;

    @Builder.Default
    protected final boolean returnsActionList = true;

    @Override
    public ShowAction requirementsMet(GameState gameState, UUID entityId) {
        List<I> options = getTargets.apply(gameState, entityId);
        if (options.isEmpty())
            return ShowAction.GRAYED;
        return requirementsMet.apply(gameState, entityId);
    }

    @Override
    public ActionResult onSelect(GameState gameState, UUID entityId) {
        List<Action> actionOptions = new ArrayList<>();
        List<I> options = getTargets.apply(gameState, entityId);
        for (I option : options) {
            String displayName = getDisplayName.apply(option);
            actionOptions.add(Action.builder().name(name + "," + option.toString()).displayName(displayName).build());
        }
        return new ActionListResult(actionOptions);
    }

    public Action getAction(GameState gameState, UUID entityId, String selectedOption) {
        List<I> options = getTargets.apply(gameState, entityId);
        for (I option : options) {
            if (option.toString().equals(selectedOption))
                return Action.builder().name(option.toString()).onSelect((gs, e) -> {
                    TargetInput<I> input = new TargetInput<>(gs, e, option);
                    return onSelectTarget.apply(input);
                }).build();
        }
        throw new RuntimeException("Selected Target option not found " + selectedOption);
    }

    public TargetAction<I> register() {
        ActionManager.register(this);
        return this;
    }

    @Getter
    public static class TargetInput<I> {
        GameState gameState;
        UUID entityId;
        I input;

        public TargetInput(GameState gameState, UUID entityId, I input) {
            this.gameState = gameState;
            this.entityId = entityId;
            this.input = input;
        }
    }

}
