package heroes.journey.entities.actions.options;

import heroes.journey.entities.actions.Action;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class OptionAction extends Action {

    @Builder.Default private String display = "";

    public void setDisplay(String value) {
        this.display = super.toString() + ": " + value;
    }

}
