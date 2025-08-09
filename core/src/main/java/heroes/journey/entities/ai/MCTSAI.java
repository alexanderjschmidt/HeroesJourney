package heroes.journey.entities.ai;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.entities.actions.ActionUtils;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.actions.Action;
import heroes.journey.modlib.actions.ActionEntry;
import heroes.journey.modlib.actions.ActionListResult;
import heroes.journey.modlib.actions.ShowAction;
import heroes.journey.modlib.attributes.Stat;
import heroes.journey.mods.Registries;
import heroes.journey.utils.ai.MCTS;
import heroes.journey.utils.ai.Scorer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static heroes.journey.mods.Registries.ActionManager;

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
        Stat forbiddenTag = Registries.StatManager.get(Ids.GROUP_APPROACHES);

        List<Action> actions;
        if (forbiddenTag != null) {
            actions = input.findActionsByTags(
                input.getEntityId(), new ArrayList<>(), new ArrayList<>(),
                Collections.singletonList(forbiddenTag)
            );
        } else {
            actions = Collections.emptyList();
        }

        List<ActionEntry> options = actions.stream()
            .map(a -> new ActionEntry(a.getId(), input.getHashMapCopy()))
            .collect(Collectors.toList());
        addUsableActions(possibleActions, options, input,
            position);
        return possibleActions;
    }

    @Override
    public int getScore(GameState gameState, UUID playingEntity) {
        return StatsComponent.get(gameState.getWorld(), playingEntity).get(Ids.STAT_FAME);
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
                ActionListResult result = (ActionListResult) ActionUtils.onSelect(act, input);
                assert result != null;
                addUsableActions(possibleActions, result.getList(), input, position);
            } else {
                if (ActionUtils.requirementsMet(act, input) == ShowAction.YES) {
                    possibleActions.add(new QueuedAction(action, input.getEntityId()));
                }
            }
        }
    }
}
