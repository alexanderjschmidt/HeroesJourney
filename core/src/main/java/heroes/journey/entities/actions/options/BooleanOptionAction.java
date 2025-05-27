package heroes.journey.entities.actions.options;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BooleanOptionAction extends OptionAction {

    @NonNull private Boolean toggle;
    @Getter @Builder.Default protected final boolean terminal = false;

    public ActionResult onSelect() {
        return onSelect(null, null);
    }

    public ActionResult onSelect(GameState gameState, UUID selected) {
        toggle = !toggle;
        this.setDisplay(toggle + "");
        return null;
    }

    public boolean isTrue() {
        return toggle;
    }

    public BooleanOptionAction register() {
        return (BooleanOptionAction)ActionManager.register(this);
    }

}
