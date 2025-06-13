package heroes.journey.entities.actions.options;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.Cost;

public abstract class OptionAction extends Action {
    private String display;

    public OptionAction(
        String id,
        String name,
        String description,
        boolean returnsActionList,
        Cost cost,
        String display) {
        super(id, name, description, returnsActionList, cost);
        this.display = display != null ? display : "";
    }
    
    public void setDisplay(String value) {
        this.display = super.toString() + ": " + value;
    }
}
