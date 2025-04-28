package heroes.journey.entities.actions;

import java.util.ArrayList;
import java.util.List;

public class TeamActions extends ArrayList<Action> {

    private static final TeamActions actionManager = new TeamActions();

    private TeamActions() {
    }

    public static List<Action> getTeamActions() {
        return actionManager;
    }

    public static void addTeamAction(Action action) {
        actionManager.addFirst(action);
    }

}
