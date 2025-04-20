package heroes.journey.entities.actions.options;

import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BooleanOptionAction extends OptionAction {

    @NonNull private Boolean toggle;
    @Getter @Builder.Default protected final boolean terminal = false;

    public String onSelect() {
        return onSelect(null, null);
    }

    public String onSelect(GameState gameState, Integer selected) {
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
