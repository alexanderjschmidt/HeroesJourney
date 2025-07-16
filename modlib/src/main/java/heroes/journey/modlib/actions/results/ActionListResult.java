package heroes.journey.modlib.actions.results;

import java.util.List;

import heroes.journey.modlib.actions.ActionEntry;

public record ActionListResult(List<ActionEntry> list) implements ActionResult {

    public String toString() {
        return list.toString();
    }

}
