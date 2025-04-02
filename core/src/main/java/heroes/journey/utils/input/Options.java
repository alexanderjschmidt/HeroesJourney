package heroes.journey.utils.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;

public class Options {

    public static List<Action> optionsList = new ArrayList<>(2);

    static {
        new Action("Options", true) {
            @Override
            public boolean isTerminal() {
                return false;
            }

            @Override
            public void onSelect(GameState gameState, Entity selected) {
                HUD.get().setState(new ActionSelectState(optionsList));
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return true;
            }
        };
    }
}
