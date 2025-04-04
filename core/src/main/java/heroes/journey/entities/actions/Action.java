package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;

public abstract class Action {

    protected String name;

    public Action(String name, boolean teamSkill) {
        this.name = name;
        ActionManager.get().put(name, this);
        if (teamSkill)
            ActionManager.addTeamAction(this);
    }

    public Action(String name) {
        this(name, false);
    }

    public abstract boolean requirementsMet(GameState gameState, Entity selected);

    public void onHover(GameState gameState, Entity selected) {
        gameState.getRangeManager().clearRange();
    }

    /**
     * @param gameState
     * @param selected
     * @return the results of the action for a popup window
     */
    public abstract String onSelect(GameState gameState, Entity selected);

    public String toString() {
        return name;
    }

    public boolean isTerminal() {
        return true;
    }

}
