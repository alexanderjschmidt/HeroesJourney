package heroes.journey.entities.ai;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.utils.ai.MCTS;
import heroes.journey.utils.ai.Scorer;
import heroes.journey.utils.ai.pathfinding.Cell;

public class MCTSAI implements AI, Scorer {

    private final MCTS mcts;

    public MCTSAI() {
        mcts = new MCTS();
    }

    @Override
    public QueuedAction getMove(GameState gameState, Integer playingEntity) {
        return mcts.runMCTS(gameState.clone(), playingEntity, this);
    }

    @Override
    public List<QueuedAction> getPossibleQueuedActions(GameState gameState) {
        List<QueuedAction> possibleActions = new ArrayList<>();
        Integer playingEntity = gameState.getCurrentEntity();
        PositionComponent position = PositionComponent.get(gameState.getWorld(), playingEntity);
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
    public int getScore(GameState gameState, Integer playingEntity) {
        PositionComponent position = PositionComponent.get(gameState.getWorld(), playingEntity);
        return GameState.global().getHeight() - position.getY();
    }
}
