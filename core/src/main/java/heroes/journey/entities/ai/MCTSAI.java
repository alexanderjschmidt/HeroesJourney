package heroes.journey.entities.ai;

import static heroes.journey.mods.Registries.ActionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.modlib.actions.ActionEntry;
import heroes.journey.modlib.actions.ActionListResult;
import heroes.journey.modlib.actions.ShowAction;
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

        ActionContext input = new ActionContext(gameState, playingEntity, true);
        addUsableActions(possibleActions, ActionMenu.getActionsFor(gameState, playingEntity), input,
            position);
        return possibleActions;
    }

    @Override
    public int getScore(GameState gameState, UUID playingEntity) {
        return StatsComponent.getFame(gameState.getWorld(), playingEntity);
    }

    private void addUsableActions(
        List<QueuedAction> possibleActions,
        List<ActionEntry> actions,
        ActionContext inputBase,
        PositionComponent position) {
        for (ActionEntry action : actions) {
            ActionContext input = new ActionContext(inputBase.getGameState(), inputBase.getEntityId(), true,
                action.getInput());
            Action act = ActionManager.get(action.getActionId());
            if (act.isReturnsActionList()) {
                ActionListResult result = (ActionListResult)act.onSelect(input);
                assert result != null;
                addUsableActions(possibleActions, result.getList(), input, position);
            } else {
                if (act.requirementsMet(input) == ShowAction.YES) {
                    possibleActions.add(new QueuedAction(action, input.getEntityId()));
                }
            }
        }
    }
}
