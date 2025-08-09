package heroes.journey.systems.constantsystems;

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
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.modlib.actions.*;
import heroes.journey.systems.GameWorld;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.ui.hudstates.States;

import java.util.Objects;
import java.util.UUID;

@All({PositionComponent.class, IdComponent.class, ActionComponent.class})
@Exclude({MovementComponent.class})
public class ActionSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent positionComponent = PositionComponent.get(world, id);

        if (positionComponent.isNotSynced()) {
            return;
        }

        ActionComponent actionComponent = ActionComponent.get(world, id);
        ActionContext input = new ActionContext(world.getGameState(), id, false, actionComponent.input());
        Action action = actionComponent.getAction();
        ActionResult result = heroes.journey.entities.actions.ActionUtils.onSelect(action, input);
        if (result != null) {
            switch (result) {
                case StringResult str -> {
                    if (!str.isEndTurn()) {
                        world.getGameState().getEntitiesInActionOrder().addFirst(id);
                    }
                    world.getGameState().getHistory().add(action.getId(), input, id);
                    world.getGameState().nextMove();
                    HUD.get().revertToInitialState();
                    if (PlayerInfo.isPlayer(id)) {
                        HUD.get().getPopupUI().getText().setText(str.toString());
                        HUD.get().setState(States.POP_UP);
                    }
                }
                case NullResult n -> {
                }
                case ActionListResult actions -> {
                    HUD.get().setState(new ActionSelectState(actions.getList()));
                }
                case EndTurnResult nothing -> {
                    GameState.global().getHistory().add(action.getId(), input, id);
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
        GameWorld world = (GameWorld) getWorld();

        // Try to get the ID component - if it doesn't exist, all components are gone
        UUID id = null;
        try {
            id = IdComponent.get(world, entityId);
        } catch (Exception e) {
            return;
        }

        // If we have the ID, we can safely access other components
        EventQueueComponent events = EventQueueComponent.get(world, id);
        if (events != null) {
            Objects.requireNonNull(events.events().poll()).run();
            if (events.events().isEmpty()) {
                world.edit(entityId).remove(EventQueueComponent.class);
            }
        }
    }
}
