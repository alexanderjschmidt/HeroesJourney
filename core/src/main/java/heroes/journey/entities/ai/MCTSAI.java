package heroes.journey.entities.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.actions.ActionEntry;
import heroes.journey.entities.actions.ActionInput;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.ShowAction;
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
        List<ActionEntry> actions,
        ActionInput inputBase,
        PositionComponent position) {
        for (ActionEntry action : actions) {
            ActionInput input = new ActionInput(inputBase.getGameState(), inputBase.getEntityId(),
                action.getInput());
            if (action.getAction().isReturnsActionList()) {
                ActionListResult result = (ActionListResult)action.getAction().onSelect(input, false);
                assert result != null;
                addUsableActions(possibleActions, result.list(), input, position);
            } else {
                if (action.getAction().requirementsMet(input) == ShowAction.YES) {
                    possibleActions.add(new QueuedAction(action, input.getEntityId()));
                }
            }
        }
    }
}
