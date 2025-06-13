package heroes.journey.entities.actions.options;

import static heroes.journey.registries.Registries.ActionManager;

import heroes.journey.entities.actions.Cost;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Getter;
import lombok.NonNull;

public class BooleanOptionAction extends OptionAction {
    @NonNull private Boolean toggle;
    @Getter protected final boolean terminal;

    public BooleanOptionAction(
        String id,
        String name,
        String description,
        boolean returnsActionList,
        Cost cost,
        String display,
        Boolean toggle,
        boolean terminal) {
        super(id, name, description, returnsActionList, cost, display);
        this.toggle = toggle;
        this.terminal = terminal;
    }

    public BooleanOptionAction(String id, String name, Boolean toggle) {
        this(id, name, "", false, null, "", toggle, false);
    }

    public boolean isTrue() {
        return toggle;
    }

    @Override
    public ActionResult internalOnSelect(ActionInput input) {
        toggle = !toggle;
        this.setDisplay(toggle + "");
        return null;
    }

    public BooleanOptionAction register() {
        return (BooleanOptionAction)ActionManager.register(this);
    }
}
