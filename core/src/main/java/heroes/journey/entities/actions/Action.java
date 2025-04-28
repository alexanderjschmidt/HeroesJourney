package heroes.journey.entities.actions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import heroes.journey.GameState;
import heroes.journey.ui.HUD;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
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

    @Builder.Default protected Cost cost = Cost.builder().build();

    public ShowAction requirementsMet(GameState gameState, Integer userId) {
        return requirementsMet.apply(gameState, userId).and(cost.requirementsMet(gameState, userId));
    }

    public void onHover(GameState gameState, Integer userId) {
        HUD.get().getCursor().setMapPointerLoc(null);
        gameState.getRangeManager().clearRange();
        onHover.accept(gameState, userId);
    }

    /**
     * @param gameState
     * @param userId
     * @return the results of the action for a popup window
     */
    public String onSelect(GameState gameState, Integer userId) {
        cost.onUse(gameState, userId);
        return onSelect.apply(gameState, userId);
    }

    @Override
    public String toString() {
        return name;
    }

    public Action register() {
        return ActionManager.register(this);
    }

}
