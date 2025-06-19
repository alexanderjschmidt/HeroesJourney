package heroes.journey.entities.actions.results;

import java.util.List;

import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionEntry;

public class ActionListNoInputResult implements ActionResult {

    private List<Action> list;

    public ActionListNoInputResult(List<Action> list) {
        this.list = list;
    }

    public List<ActionEntry> list() {
        return list.stream().map(e -> new ActionEntry(e.getId(), "")).toList();
    }

    public String toString() {
        return list.toString();
    }

}
