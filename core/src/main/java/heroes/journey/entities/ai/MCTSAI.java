package heroes.journey.entities.ai;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.ui.windows.ActionMenu;
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

        addActions(possibleActions, ActionMenu.getActionsFor(gameState, playingEntity), gameState,
            playingEntity, position);
        return possibleActions;
    }

    private void addActions(
        List<QueuedAction> possibleActions,
        List<Action> actions,
        GameState gameState,
        Integer entityId,
        PositionComponent position) {
        for (Action action : actions) {
            if (action.isReturnsActionList()) {
                ActionListResult result = (ActionListResult)action.onSelect(gameState, entityId);
                addActions(possibleActions, result.list(), gameState, entityId, position);
            } else {
                if (action.requirementsMet(gameState, entityId) == ShowAction.YES) {
                    Cell path = new Cell(position.getX(), position.getY());
                    possibleActions.add(new QueuedAction(path, action, -1, -1));
                }
            }
        }
    }

    @Override
    public int getScore(GameState gameState, Integer playingEntity) {
        PositionComponent position = PositionComponent.get(gameState.getWorld(), playingEntity);
        return GameState.global().getHeight() - position.getY();
    }
}
