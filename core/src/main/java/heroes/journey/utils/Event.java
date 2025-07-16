package heroes.journey.utils;

import java.util.function.Function;
import java.util.function.Predicate;

import heroes.journey.entities.actions.ActionInput;
import heroes.journey.modlib.actions.results.ActionResult;

public class Event {
    protected final Function<ActionInput,ActionResult> trigger;
    protected final Predicate<ActionInput> requirementsMet;

    public Event(Function<ActionInput,ActionResult> trigger, Predicate<ActionInput> requirementsMet) {
        this.trigger = trigger;
        this.requirementsMet = requirementsMet != null ? requirementsMet : (input) -> true;
    }

    public Event(Function<ActionInput,ActionResult> trigger) {
        this(trigger, (input) -> true);
    }
}
