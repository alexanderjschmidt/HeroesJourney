package heroes.journey.entities.actions.options;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.utils.input.Options;

public abstract class OptionAction extends Action {

    private final String title;

    public OptionAction(String name) {
        super(name, false);
        this.title = name;
        Options.optionsList.add(this);
    }

    @Override
    public boolean requirementsMet(GameState gameState, Entity selected) {
        return true;
    }

    public void onHover(GameState gameState, Entity selected) {

    }

    public abstract String onSelect(GameState gameState, Entity selected);

    public boolean isTerminal() {
        return false;
    }

    public void setName(String value) {
        this.name = title + ": " + value;
    }

}
