package heroes.journey.utils.ai;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.entities.actions.QueuedAction;

import java.util.List;

public interface Scorer {

    public List<QueuedAction> getPossibleQueuedActions(GameState gameState);

    public int getScore(GameState gameState, Entity playingEntity);

}
