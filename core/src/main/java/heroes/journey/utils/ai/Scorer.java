package heroes.journey.utils.ai;

import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;

import java.util.List;
import java.util.UUID;

public interface Scorer {

    public List<QueuedAction> getPossibleQueuedActions(GameState gameState);

    public int getScore(GameState gameState, UUID playingEntity);

}
