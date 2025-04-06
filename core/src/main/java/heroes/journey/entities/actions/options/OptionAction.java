package heroes.journey.entities.actions.options;

import heroes.journey.entities.actions.Action;
import heroes.journey.utils.input.Options;

public abstract class OptionAction extends Action {

    private String display;

    public OptionAction(ActionBuilder builder) {
        super(builder);
        setDisplay("");
        Options.optionsList.add(this);
    }

    @Override
    public String toString() {
        return display;
    }

    public void setDisplay(String value) {
        this.display = name + ": " + value;
    }

}
