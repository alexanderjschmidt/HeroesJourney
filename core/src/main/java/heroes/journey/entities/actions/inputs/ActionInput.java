package heroes.journey.entities.actions.inputs;

import heroes.journey.GameState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ActionInput {

    private final GameState gameState;
    private final UUID entityId;

    public ActionInput(GameState gameState, UUID entityId) {
        this.gameState = gameState;
        this.entityId = entityId;
    }

}
