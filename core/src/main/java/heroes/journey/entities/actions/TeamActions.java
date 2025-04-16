package heroes.journey.entities.actions;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.ui.ScrollPaneEntry;

public class TeamActions extends ArrayList<Action> {

    private static final TeamActions actionManager = new TeamActions();

    private TeamActions() {
    }

    public static List<ScrollPaneEntry<Action>> getTeamActions(GameState gameState) {
        ArrayList<ScrollPaneEntry<Action>> options = new ArrayList<>(actionManager.size());
        for (Action action : actionManager) {
            if (action.requirementsMet(gameState, null) == ShowAction.YES)
                options.add(new ScrollPaneEntry<>(action, true));
        }
        return options;
    }

    public static void addTeamAction(Action action) {
        actionManager.addFirst(action);
    }

}
