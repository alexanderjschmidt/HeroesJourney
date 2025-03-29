package heroes.journey.utils.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;

public class Options {

    public static boolean DEBUG = false, AUTO_END_TURN = true;

    static {
        Action debugAction = new Action("Debug: " + DEBUG) {
            @Override
            public boolean isTerminal() {
                return false;
            }

            @Override
            public void onSelect(GameState gameState, Entity selected) {
                DEBUG = !DEBUG;
                this.setName("Debug: " + DEBUG);
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return false;
            }
        };
        Action autoEndTurnAction = new Action("Auto End Turn: " + AUTO_END_TURN) {
            @Override
            public boolean isTerminal() {
                return false;
            }

            @Override
            public void onSelect(GameState gameState, Entity selected) {
                AUTO_END_TURN = !AUTO_END_TURN;
                this.setName("Auto End Turn: " + AUTO_END_TURN);
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return false;
            }
        };
        List<Action> optionsList = new ArrayList<>(2);
        optionsList.add(debugAction);
        optionsList.add(autoEndTurnAction);
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
