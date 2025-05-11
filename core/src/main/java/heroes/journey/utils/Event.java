package heroes.journey.utils;

import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Builder;

import java.util.function.Function;
import java.util.function.Predicate;

@Builder
public class Event {

    protected Function<ActionInput, ActionResult> trigger;

    @Builder.Default
    protected Predicate<ActionInput> requirementsMet = (input) -> true;

}
