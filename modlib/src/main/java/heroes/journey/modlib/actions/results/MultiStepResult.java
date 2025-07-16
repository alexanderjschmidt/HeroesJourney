package heroes.journey.modlib.actions.results;

import java.util.Queue;

public class MultiStepResult implements ActionResult {

    private final Queue<Runnable> events;

    public MultiStepResult(Queue<Runnable> events) {
        this.events = events;
    }

    public Queue<Runnable> getEvents() {
        return events;
    }

}
