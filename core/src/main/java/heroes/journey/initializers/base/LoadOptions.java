package heroes.journey.initializers.base;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.OptionAction;
import heroes.journey.initializers.InitializerInterface;

public class LoadOptions implements InitializerInterface {

    public static boolean DEBUG = false, AUTO_END_TURN = true;

    static {
        Action debugAction = new OptionAction("Debug: " + DEBUG) {
            @Override
            public String onSelect(GameState gameState, Entity selected) {
                DEBUG = !DEBUG;
                this.setName("Debug: " + DEBUG);
                return null;
            }
        };
        Action autoEndTurnAction = new OptionAction("Auto End Turn: " + AUTO_END_TURN) {
            @Override
            public String onSelect(GameState gameState, Entity selected) {
                AUTO_END_TURN = !AUTO_END_TURN;
                this.setName("Auto End Turn: " + AUTO_END_TURN);
                return null;
            }
        };
    }
}
