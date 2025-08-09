package heroes.journey.entities.actions;

import heroes.journey.modlib.actions.Action;
import heroes.journey.modlib.actions.ActionEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamActions extends ArrayList<ActionEntry> {

    private static final TeamActions actionManager = new TeamActions();

    private TeamActions() {
    }

    public static List<ActionEntry> getTeamActions() {
        return actionManager;
    }

    public static void addTeamAction(Action action) {
        actionManager.addFirst(new ActionEntry(action.getId(), new HashMap<>(0)));
    }

}
