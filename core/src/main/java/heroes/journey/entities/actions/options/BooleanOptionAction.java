package heroes.journey.entities.actions.options;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;

public class BooleanOptionAction extends OptionAction {

    private boolean toggle;

    public BooleanOptionAction(String name, boolean defaultToggle) {
        super(name);
        this.toggle = defaultToggle;
        setName(toggle + "");
    }

    public String onSelect() {
        return onSelect(null, null);
    }

    public String onSelect(GameState gameState, Entity selected) {
        toggle = !toggle;
        this.setName(toggle + "");
        return null;
    }

    public boolean isTrue() {
        return toggle;
    }

}
