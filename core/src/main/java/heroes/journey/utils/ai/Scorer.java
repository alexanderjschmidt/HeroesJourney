package heroes.journey.utils.ai;

import java.util.List;

import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;

public interface Scorer {

    public List<QueuedAction> getPossibleQueuedActions(GameState gameState);

    public int getScore(GameState gameState, Integer playingEntity);

}
