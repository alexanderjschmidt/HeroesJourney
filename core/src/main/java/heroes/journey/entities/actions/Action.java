package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Action {

    protected final String name;
    protected final boolean terminalAction;
    protected BiConsumer<GameState, Entity> onHover;
    protected BiFunction<GameState, Entity, String> onSelect;
    protected BiFunction<GameState, Entity, ShowAction> requirementsMet;

    protected Action(ActionBuilder builder) {
        this.name = builder.name;
        this.terminalAction = builder.terminalAction;
        this.onHover = builder.onHover;
        this.onSelect = builder.onSelect;
        this.requirementsMet = builder.requirementsMet;
        ActionManager.get().put(name, this);
        if (builder.teamAction)
            ActionManager.addTeamAction(this);
    }

    public ShowAction requirementsMet(GameState gameState, Entity selected) {
        return requirementsMet.apply(gameState, selected);
    }

    public void onHover(GameState gameState, Entity selected) {
        gameState.getRangeManager().clearRange();
        onHover.accept(gameState, selected);
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

    public static class Builder extends ActionBuilder<Builder, Action> {

        public Action build() {
            return new Action(this);
        }

    }

    public static abstract class ActionBuilder<B extends ActionBuilder<B, I>, I> {

        String name;
        boolean teamAction = false, terminalAction = true;

        private BiConsumer<GameState, Entity> onHover = (gs, e) -> {
        };
        private BiFunction<GameState, Entity, String> onSelect = (gs, e) -> {
            return null;
        };
        private BiFunction<GameState, Entity, ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;

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

        public B onHover(BiConsumer<GameState, Entity> onHover) {
            this.onHover = onHover;
            return self();
        }

        public B onSelect(BiFunction<GameState, Entity, String> onSelect) {
            this.onSelect = onSelect;
            return self();
        }

        public B requirementsMet(BiFunction<GameState, Entity, ShowAction> requirementsMet) {
            this.requirementsMet = requirementsMet;
            return self();
        }

        protected B self() {
            return (B) this;
        }

        public abstract I build();
    }

}
