package heroes.journey.entities.actions;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.GameState;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.ui.HUD;
import heroes.journey.ui.windows.InfoProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@SuperBuilder(toBuilder = true)
public class Action implements InfoProvider {

    @NonNull
    @Getter
    protected final String name;
    @Builder.Default
    protected final String displayName = null;
    @Builder.Default
    @Getter
    protected final String description = "";
    @Builder.Default
    @Getter
    protected final boolean returnsActionList = false;
    @Builder.Default
    protected BiConsumer<GameState, UUID> onHover = (gs, e) -> {
    };
    protected BiFunction<GameState, UUID, ActionResult> onSelect;
    // This is used for complex actions that need to be simplified for the AI
    protected BiFunction<GameState, UUID, ActionResult> onSelectAI;
    @Builder.Default
    protected BiFunction<GameState, UUID, ShowAction> requirementsMet = (gs, e) -> ShowAction.YES;

    @Builder.Default
    protected Cost cost = Cost.builder().build();

    public ShowAction requirementsMet(GameState gameState, UUID userId) {
        return requirementsMet.apply(gameState, userId).and(cost.requirementsMet(gameState, userId));
    }

    public void onHover(GameState gameState, UUID userId) {
        HUD.get().getCursor().setMapPointerLoc(null);
        onHover.accept(gameState, userId);
    }

    /**
     * @return the results of the action for a popup window
     */
    public ActionResult onSelect(GameState gameState, UUID userId, boolean ai) {
        cost.onUse(gameState, userId);
        if (ai && onSelectAI != null)
            return onSelectAI.apply(gameState, userId);
        return onSelect.apply(gameState, userId);
    }

    public ActionResult onSelect(GameState gameState, UUID userId) {
        return onSelect(gameState, userId, false);
    }

    @Override
    public String toString() {
        return displayName == null ? name : displayName;
    }

    public Action register() {
        return ActionManager.register(this);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public void fillCustomContent(Table table, Skin skin) {
        table.add(cost.getDisplay()).center().fill().expand();
    }
}
