package heroes.journey.entities.actions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Action {

    protected final String name;
    @Getter @Builder.Default protected final boolean terminal = true;
    @Builder.Default protected BiConsumer<GameState,Entity> onHover = (gs, e) -> {
    };
    @Builder.Default protected BiFunction<GameState,Entity,String> onSelect = (gs, e) -> {
        return null;
    };
    @Builder.Default
    protected BiFunction<GameState,Entity,ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;

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

    @Override
    public String toString() {
        return name;
    }

}
