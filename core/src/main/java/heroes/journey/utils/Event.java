package heroes.journey.utils;

import java.util.function.Function;
import java.util.function.Predicate;

import heroes.journey.entities.actions.ActionContext;
import heroes.journey.modlib.actions.results.ActionResult;

public class Event {
    protected final Function<ActionContext,ActionResult> trigger;
    protected final Predicate<ActionContext> requirementsMet;

    public Event(Function<ActionContext,ActionResult> trigger, Predicate<ActionContext> requirementsMet) {
        this.trigger = trigger;
        this.requirementsMet = requirementsMet != null ? requirementsMet : (input) -> true;
    }

    public Event(Function<ActionContext,ActionResult> trigger) {
        this(trigger, (input) -> true);
    }
}
