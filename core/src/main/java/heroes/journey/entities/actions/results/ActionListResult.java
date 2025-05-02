package heroes.journey.entities.actions.results;

import java.util.List;

import heroes.journey.entities.actions.Action;

public record ActionListResult(List<Action> list) implements ActionResult {

    public String toString() {
        return list.toString();
    }

}
