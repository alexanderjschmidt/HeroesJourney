package heroes.journey.systems.constantsystems;

import static heroes.journey.registries.Registries.ActionManager;

import java.util.Objects;
import java.util.UUID;

import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.EventQueueComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.actions.results.MultiStepResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.systems.GameWorld;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.ui.hudstates.States;

@All({PositionComponent.class, IdComponent.class, ActionComponent.class})
@Exclude({MovementComponent.class})
public class ActionSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent positionComponent = PositionComponent.get(world, id);

        if (positionComponent.isNotSynced()) {
            return;
        }

        ActionComponent actionComponent = ActionComponent.get(world, id);
        ActionInput input = new ActionInput(GameState.global(), id);
        Action action = actionComponent.getAction();
        if (action == null) {
            String[] targetActionParts = actionComponent.action().split(",");
            TargetAction targetAction = (TargetAction)ActionManager.get(targetActionParts[0]);
            if (targetAction == null)
                throw new RuntimeException(actionComponent.action() + " action could not be parsed");
            action = targetAction.getActionFromSelected(input, targetActionParts[1]);
        }
        ActionResult result = action.onSelect(input);
        if (result != null) {
            switch (result) {
                case StringResult str -> {
                    GameState.global().getHistory().add(action, id);
                    GameState.global().nextMove();
                    HUD.get().revertToInitialState();
                    if (PlayerInfo.isPlayer(id)) {
                        HUD.get().getPopupUI().getText().setText(str.toString());
                        HUD.get().setState(States.POP_UP);
                    }
                }
                case ActionListResult actions -> {
                    HUD.get().setState(new ActionSelectState(actions.list()));
                }
                case MultiStepResult multi -> {
                    world.edit(entityId).create(EventQueueComponent.class).events(multi.getEvents());
                }
                case EndTurnResult nothing -> {
                    GameState.global().getHistory().add(action, id);
                    GameState.global().nextMove();
                    HUD.get().revertToInitialState();
                }
                default -> throw new IllegalStateException("Unexpected value: " + result.getClass());
            }
        }
        world.edit(entityId).remove(ActionComponent.class);
    }

    @Override
    public void removed(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        EventQueueComponent events = EventQueueComponent.get(world, id);
        if (events != null) {
            Objects.requireNonNull(events.events().poll()).run();
            if (events.events().isEmpty()) {
                world.edit(entityId).remove(EventQueueComponent.class);
            }
        }
    }
}
