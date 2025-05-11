package heroes.journey.entities.actions;

import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.inputs.TargetInput;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@SuperBuilder(toBuilder = true, builderMethodName = "targetBuilder")
public class TargetAction<I> extends Action {

    protected Function<ActionInput, List<I>> getTargets;
    @Builder.Default
    protected Consumer<TargetInput<I>> onHoverTarget = (input) -> {
    };
    protected Function<TargetInput<I>, ActionResult> onSelectTarget;
    // This is used for complex actions that need to be simplified for the AI
    protected Function<TargetInput<I>, ActionResult> onSelectAITarget;
    @Builder.Default
    protected Function<TargetInput<I>, ShowAction> requirementsMetTarget = (input) -> ShowAction.YES;
    @Builder.Default
    protected Cost<TargetInput<I>> costTarget = Cost.<TargetInput<I>>builder().build();

    @Builder.Default
    protected Function<TargetInput<I>, String> getTargetDisplayName = Object::toString;

    @Builder.Default
    protected final boolean returnsActionList = true;

    @Override
    public ShowAction requirementsMet(ActionInput input) {
        List<I> options = getTargets.apply(input);
        if (options.isEmpty())
            return ShowAction.GRAYED;
        return requirementsMet.apply(input);
    }

    @Override
    public ActionResult onSelect(ActionInput input) {
        List<Action> actionOptions = new ArrayList<>();
        List<I> options = getTargets.apply(input);
        for (I option : options) {
            TargetInput<I> targetInput = new TargetInput<>(input.getGameState(), input.getEntityId(), option);
            actionOptions.add(getAction(targetInput));
        }
        return new ActionListResult(actionOptions);
    }

    public Action getActionFromSelected(ActionInput inputBase, String selectedOption) {
        List<I> options = getTargets.apply(inputBase);
        for (I option : options) {
            if (option.toString().equals(selectedOption)) {
                TargetInput<I> targetInput = new TargetInput<>(inputBase.getGameState(), inputBase.getEntityId(), option);
                costTarget.onUse(targetInput);
                return getAction(targetInput);
            }
        }
        throw new RuntimeException("Selected Target option not found " + selectedOption);
    }

    private Action getAction(TargetInput<I> targetInput) {
        String displayName = getTargetDisplayName.apply(targetInput);
        I option = targetInput.getInput();
        return Action.builder().name(name + "," + option.toString()).displayName(displayName)
            .onSelect((input) -> onSelectTarget.apply(targetInput))
            .onSelectAI((input) -> onSelectAITarget.apply(targetInput))
            .onHover((input) -> onHoverTarget.accept(targetInput))
            .requirementsMet((input) -> requirementsMetTarget.apply(targetInput).and(costTarget.requirementsMet(targetInput))).build();
    }

    public TargetAction<I> register() {
        ActionManager.register(this);
        return this;
    }

}
