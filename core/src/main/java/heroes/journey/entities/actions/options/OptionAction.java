package heroes.journey.entities.actions.options;

import heroes.journey.entities.actions.Action;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class OptionAction extends Action {

    private String display = "";

    @Override
    public String toString() {
        return display;
    }

    public void setDisplay(String value) {
        this.display = super.toString() + ": " + value;
    }

}
