package heroes.journey.entities.actions.options;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.Cost;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;

import java.util.function.Function;

public abstract class OptionAction extends Action {
    private String display;

    public OptionAction(String name, String displayName, String description, boolean returnsActionList,
                        Cost cost,
                        String display) {
        super(name, displayName, description, returnsActionList, cost);
        this.display = display != null ? display : "";
    }

    public OptionAction(String name, Function<ActionInput, ActionResult> onSelect) {
        this(name, null, "", false, null, "");
    }

    public void setDisplay(String value) {
        this.display = super.toString() + ": " + value;
    }
}
