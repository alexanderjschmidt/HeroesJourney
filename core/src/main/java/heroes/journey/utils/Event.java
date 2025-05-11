package heroes.journey.utils;

import heroes.journey.GameState;
import heroes.journey.entities.actions.results.ActionResult;
import lombok.Builder;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

@Builder
public class Event {

    protected BiFunction<GameState, UUID, ActionResult> trigger;

    @Builder.Default
    protected BiPredicate<GameState, UUID> requirementsMet = (gs, e) -> true;

}
