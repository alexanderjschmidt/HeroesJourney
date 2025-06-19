package heroes.journey.entities.actions.results;

import java.util.List;

import heroes.journey.entities.actions.ActionEntry;

public record ActionListResult(List<ActionEntry> list) implements ActionResult {

    public String toString() {
        return list.toString();
    }

}
