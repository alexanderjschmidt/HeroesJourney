package heroes.journey.entities.actions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Action {

    protected final String name;
    @Getter @Builder.Default protected final boolean terminal = true;
    @Builder.Default protected BiConsumer<GameState,Integer> onHover = (gs, e) -> {
    };
    @Builder.Default protected BiFunction<GameState,Integer,String> onSelect = (gs, e) -> {
        return null;
    };
    @Builder.Default
    protected BiFunction<GameState,Integer,ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;

    public ShowAction requirementsMet(GameState gameState, Integer selected) {
        return requirementsMet.apply(gameState, selected);
    }

    public void onHover(GameState gameState, Integer selected) {
        gameState.getRangeManager().clearRange();
        onHover.accept(gameState, selected);
    }

    /**
     * @param gameState
     * @param selected
     * @return the results of the action for a popup window
     */
    public String onSelect(GameState gameState, Integer selected) {
        return onSelect.apply(gameState, selected);
    }

    @Override
    public String toString() {
        return name;
    }

    public Action register() {
        return ActionManager.register(this);
    }

}
