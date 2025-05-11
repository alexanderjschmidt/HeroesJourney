package heroes.journey.entities.actions.inputs;

import heroes.journey.GameState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TargetInput<I> extends ActionInput {
    I input;

    public TargetInput(GameState gameState, UUID entityId, I input) {
        super(gameState, entityId);
        this.input = input;
    }
}
