package heroes.journey.entities.actions.options;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.utils.input.Options;

public class BooleanOptionAction extends OptionAction {

    private boolean toggle;

    public BooleanOptionAction(Builder builder) {
        super(builder);
        this.toggle = builder.defaultToggle;
        setDisplay("");
        Options.optionsList.add(this);
    }

    public String onSelect() {
        return onSelect(null, null);
    }

    public String onSelect(GameState gameState, Entity selected) {
        toggle = !toggle;
        this.setDisplay(toggle + "");
        return null;
    }

    public boolean isTrue() {
        return toggle;
    }

    public static class Builder extends Action.ActionBuilder<Builder, BooleanOptionAction> {

        private boolean defaultToggle;

        public Builder() {
            super.terminalAction(false);
        }

        public Builder defaultToggle(boolean defaultToggle) {
            this.defaultToggle = defaultToggle;
            return this;
        }

        public Builder terminalAction(boolean terminalAction) {
            return this;
        }

        public BooleanOptionAction build() {
            return new BooleanOptionAction(this);
        }
    }

}
