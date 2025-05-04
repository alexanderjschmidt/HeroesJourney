package heroes.journey.entities.ai;

import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;

import java.util.UUID;

public interface AI {

    public QueuedAction getMove(GameState gameState, UUID playingEntity);

}
