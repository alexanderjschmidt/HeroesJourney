package heroes.journey.entities.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.ui.windows.ActionMenu;
import heroes.journey.utils.ai.MCTS;
import heroes.journey.utils.ai.Scorer;

public class MCTSAI implements AI, Scorer {

    private final MCTS mcts;

    public MCTSAI() {
        mcts = new MCTS();
    }

    @Override
    public QueuedAction getMove(GameState gameState, UUID playingEntity) {
        return mcts.runMCTS(gameState.clone(), playingEntity, this);
    }

    @Override
    public List<QueuedAction> getPossibleQueuedActions(GameState gameState) {
        List<QueuedAction> possibleActions = new ArrayList<>();
        UUID playingEntity = gameState.getCurrentEntity();
        PositionComponent position = PositionComponent.get(gameState.getWorld(), playingEntity);

        ActionInput input = new ActionInput(gameState, playingEntity);
        addUsableActions(possibleActions, ActionMenu.getActionsFor(gameState, playingEntity), input,
            position);
        return possibleActions;
    }

    @Override
    public int getScore(GameState gameState, UUID playingEntity) {
        PlayerComponent player = PlayerComponent.get(gameState.getWorld(), playingEntity);
        return player.fame();
    }

    private void addUsableActions(
        List<QueuedAction> possibleActions,
        List<Action> actions,
        ActionInput input,
        PositionComponent position) {
        for (Action action : actions) {
            if (action.isReturnsActionList()) {
                ActionListResult result = (ActionListResult)action.onSelect(input, false);
                addUsableActions(possibleActions, result.list(), input, position);
            } else {
                if (action.requirementsMet(input) == ShowAction.YES) {
                    possibleActions.add(new QueuedAction(action, input.getEntityId()));
                }
            }
        }
    }
}
