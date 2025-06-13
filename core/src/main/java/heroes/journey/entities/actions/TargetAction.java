package heroes.journey.entities.actions;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.inputs.TargetInput;
import heroes.journey.entities.actions.results.AIOnSelectNotFound;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;

public abstract class TargetAction<I> extends Action {

  public TargetAction(String id, String name, String description, Cost cost) {
    super(id, name, description, true, cost);
  }

  public abstract List<I> getTargets(ActionInput input);

  public String getTargetDisplayName(TargetInput<I> input) {
    return input.getInput().toString();
  }

  @Override
  public ShowAction requirementsMet(ActionInput input) {
    List<I> options = getTargets(input);
    if (options.isEmpty())
      return ShowAction.GRAYED;
    return super.requirementsMet(input);
  }

  @Override
  public ActionResult internalOnSelect(ActionInput input) {
    List<Action> actionOptions = new ArrayList<>();
    List<I> options = getTargets(input);
    for (I option : options) {
      TargetInput<I> targetInput = new TargetInput<>(input.getGameState(), input.getEntityId(), option);
      actionOptions.add(getAction(targetInput));
    }
    return new ActionListResult(actionOptions);
  }

  public Action getActionFromSelected(ActionInput inputBase, String selectedOption) {
    List<I> options = getTargets(inputBase);
    for (I option : options) {
      if (option.toString().equals(selectedOption)) {
        TargetInput<I> targetInput = new TargetInput<>(inputBase.getGameState(), inputBase.getEntityId(),
            option);
        cost.onUse(targetInput);
        return getAction(targetInput);
      }
    }
    throw new RuntimeException("Selected Target option not found " + selectedOption);
  }

  private Action getAction(TargetInput<I> targetInput) {
    String displayName = getTargetDisplayName(targetInput);
    I option = targetInput.getInput();
    return new Action(getId() + "," + option.toString(), displayName, "", false, null) {

      public ShowAction internalRequirementsMet(ActionInput input) {
        return requirementsMetTarget(targetInput);
      }

      public void internalOnHover(ActionInput input) {
        onHoverTarget(targetInput);
      }

      public ActionResult internalOnSelect(ActionInput input) {
        return onSelectTarget(targetInput);
      }

      public ActionResult internalOnSelectAI(ActionInput input) {
        return onSelectAITarget(targetInput);
      }
    };
  }

  protected ShowAction requirementsMetTarget(TargetInput<I> targetInput) {
    return ShowAction.YES;
  }

  protected ActionResult onSelectAITarget(TargetInput<I> targetInput) {
    return new AIOnSelectNotFound();
  }

  protected abstract ActionResult onSelectTarget(TargetInput<I> targetInput);

  protected void onHoverTarget(TargetInput<I> targetInput) {
  }

  public TargetAction<I> register() {
    ActionManager.register(this);
    return this;
  }

}
