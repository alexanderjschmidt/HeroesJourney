package heroes.journey.entities.actions;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import heroes.journey.GameState;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.ui.HUD;
import heroes.journey.ui.windows.InfoProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

import static heroes.journey.registries.Registries.ActionManager;

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
    protected Consumer<ActionInput> onHover = (input) -> {
    };
    protected Function<ActionInput, ActionResult> onSelect;
    // This is used for complex actions that need to be simplified for the AI
    protected Function<ActionInput, ActionResult> onSelectAI;
    @Builder.Default
    protected Function<ActionInput, ShowAction> requirementsMet = (input) -> ShowAction.YES;

    @Builder.Default
    protected Cost<ActionInput> cost = Cost.builder().build();

    public ShowAction requirementsMet(ActionInput input) {
        return requirementsMet.apply(input).and(cost.requirementsMet(input));
    }

    public void onHover(ActionInput input) {
        HUD.get().getCursor().setMapPointerLoc(null);
        onHover.accept(input);
    }

    /**
     * @return the results of the action for a popup window
     */
    public ActionResult onSelect(ActionInput input, boolean ai) {
        cost.onUse(input);
        if (ai && onSelectAI != null)
            return onSelectAI.apply(input);
        return onSelect.apply(input);
    }

    public ActionResult onSelect(ActionInput input) {
        return onSelect(input, false);
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
        return toString();
    }

    @Override
    public void fillCustomContent(Table table, Skin skin) {
        table.add(cost.getDisplay(new ActionInput(GameState.global(), GameState.global().getCurrentEntity())))
            .center()
            .fill()
            .expand();
    }
}
