package heroes.journey.entities.ai;

import com.badlogic.ashley.core.Entity;
import heroes.journey.GameState;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.utils.ai.MCTS;
import heroes.journey.utils.ai.Scorer;
import heroes.journey.utils.ai.pathfinding.Cell;

import java.util.ArrayList;
import java.util.List;

public class MCTSAI implements AI, Scorer {

    private final MCTS mcts;

    public MCTSAI() {
        mcts = new MCTS();
    }

    @Override
    public QueuedAction getMove(GameState gameState, Entity playingEntity) {
        return mcts.runMCTS(gameState.clone(), playingEntity, this);
    }

    @Override
    public List<QueuedAction> getPossibleQueuedActions(GameState gameState) {
        List<QueuedAction> possibleActions = new ArrayList<>();
        Entity playingEntity = gameState.getCurrentEntity();
        PositionComponent position = PositionComponent.get(playingEntity);
        Cell target = new Cell(position.getX(), position.getY() - 1);
        Cell path = new Cell(position.getX(), position.getY());
        path.parent = target;
        if (position.getY() > 0)
            possibleActions.add(new QueuedAction(path, BaseActions.wait, 0, 0));
        Cell path2 = new Cell(position.getX(), position.getY());
        possibleActions.add(new QueuedAction(path2, BaseActions.wait, 0, 0));
        return possibleActions;
    }

    @Override
    public int getScore(GameState gameState, Entity playingEntity) {
        PositionComponent position = PositionComponent.get(playingEntity);
        return GameState.global().getHeight() - position.getY();
    }
}
