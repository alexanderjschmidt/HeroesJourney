package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.effects.ConsumerChain;
import heroes.journey.entities.effects.FunctionChain;
import heroes.journey.entities.effects.PredicateChain;

public class Action {

    protected final String name;
    protected final boolean terminalAction;
    protected ConsumerChain onHover;
    protected FunctionChain<String> onSelect;
    protected PredicateChain requirementsMet;

    protected Action(ActionBuilder builder) {
        this.name = builder.name;
        this.terminalAction = builder.terminalAction;
        ActionManager.get().put(name, this);
        if (builder.teamAction)
            ActionManager.addTeamAction(this);
    }

    protected Action(Builder builder) {
        this((ActionBuilder)builder);
        this.onHover = builder.onHover.build();
        this.onSelect = builder.onSelect.build();
        this.requirementsMet = builder.requirementsMet.build();
    }

    public boolean requirementsMet(GameState gameState, Entity selected) {
        return requirementsMet.isTrue(gameState, selected);
    }

    public void onHover(GameState gameState, Entity selected) {
        gameState.getRangeManager().clearRange();
        onHover.apply(gameState, selected);
    }

    /**
     * @param gameState
     * @param selected
     * @return the results of the action for a popup window
     */
    public String onSelect(GameState gameState, Entity selected) {
        return onSelect.apply(gameState, selected);
    }

    public String toString() {
        return name;
    }

    public boolean isTerminal() {
        return terminalAction;
    }

    public static class Builder extends ActionBuilder<Builder,Action> {

        private final ConsumerChain.Builder<Builder> onHover = new ConsumerChain.Builder<>(this);
        private final FunctionChain.Builder<Builder,String> onSelect = new FunctionChain.Builder<>(this);
        private final PredicateChain.Builder<Builder> requirementsMet = new PredicateChain.Builder<>(this);

        public ConsumerChain.Builder<Builder> onHover() {
            return onHover;
        }

        public FunctionChain.Builder<Builder,String> onSelect() {
            return onSelect;
        }

        public PredicateChain.Builder<Builder> requirementsMet() {
            return requirementsMet;
        }

        public Action build() {
            return new Action(this);
        }

    }

    public static abstract class ActionBuilder<B extends ActionBuilder<B,I>, I> {

        String name;
        boolean teamAction = false, terminalAction = true;

        public B name(String name) {
            this.name = name;
            return self();
        }

        public B teamAction(boolean teamAction) {
            this.teamAction = teamAction;
            return self();
        }

        public B terminalAction(boolean terminalAction) {
            this.terminalAction = terminalAction;
            return self();
        }

        protected B self() {
            return (B)this;
        }

        public abstract I build();
    }

}
