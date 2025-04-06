package heroes.journey.entities.actions.options;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.effects.ConsumerChain;
import heroes.journey.entities.effects.FunctionChain;
import heroes.journey.entities.effects.PredicateChain;
import heroes.journey.utils.input.Options;

public class BooleanOptionAction extends OptionAction {

    private boolean toggle;

    public BooleanOptionAction(Builder builder) {
        super(builder);
        this.onHover = builder.onHover.build();
        this.onSelect = builder.onSelect.build();
        this.requirementsMet = builder.requirementsMet.build();
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

    public static class Builder extends Action.ActionBuilder<Builder,BooleanOptionAction> {

        private boolean defaultToggle;

        private final ConsumerChain.Builder<Builder> onHover = new ConsumerChain.Builder<>(this);
        private final FunctionChain.Builder<Builder,String> onSelect = new FunctionChain.Builder<>(this);
        private final PredicateChain.Builder<Builder> requirementsMet = new PredicateChain.Builder<>(this);

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

        public FunctionChain.Builder<Builder,String> onSelect() {
            return onSelect;
        }

        public BooleanOptionAction build() {
            return new BooleanOptionAction(this);
        }
    }

}
