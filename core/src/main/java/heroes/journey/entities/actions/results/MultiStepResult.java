package heroes.journey.entities.actions.results;

import lombok.Getter;

import java.util.Queue;

@Getter
public class MultiStepResult implements ActionResult {

    private final Queue<Runnable> events;

    public MultiStepResult(Queue<Runnable> events) {
        this.events = events;
    }

}
