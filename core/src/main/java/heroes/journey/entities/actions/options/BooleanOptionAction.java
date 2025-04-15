package heroes.journey.entities.actions.options;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
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

    public String onSelect(GameState gameState, Entity selected) {
        toggle = !toggle;
        this.setDisplay(toggle + "");
        return null;
    }

    public boolean isTrue() {
        return toggle;
    }

}
