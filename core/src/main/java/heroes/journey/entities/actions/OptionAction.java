package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.utils.input.Options;

public abstract class OptionAction extends Action {

    public OptionAction(String name) {
        super(name, false);
        Options.optionsList.add(this);
    }

    @Override
    public boolean requirementsMet(GameState gameState, Entity selected) {
        return true;
    }

    public void onHover(GameState gameState, Entity selected) {

    }

    public abstract void onSelect(GameState gameState, Entity selected);

    public boolean isTerminal() {
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

}
