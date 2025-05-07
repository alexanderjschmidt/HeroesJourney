package heroes.journey.systems.constantsystems;

import java.util.UUID;

import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.systems.GameWorld;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.ui.hudstates.States;

@All({PositionComponent.class, ActionComponent.class, IdComponent.class})
@Exclude({MovementComponent.class})
public class ActionSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);
        ActionComponent action = ActionComponent.get(world, id);
        PositionComponent positionComponent = PositionComponent.get(world, id);

        if (positionComponent.isNotSynced()) {
            return;
        }

        // TODO this is null on carriage?
        ActionResult result = action.getAction().onSelect(GameState.global(), id);
        if (result != null) {
            //TODO make it only show up for players its supposed to
            //System.out.println(result.getClass());
            switch (result) {
                case StringResult str -> {
                    GameState.global().nextMove();
                    HUD.get().revertToInitialState();
                    if (PlayerInfo.get().getPlayableEntities().contains(id)) {
                        HUD.get().getPopupUI().getText().setText(str.toString());
                        HUD.get().setState(States.POP_UP);
                    }
                }
                case ActionListResult actions -> {
                    HUD.get().setState(new ActionSelectState(actions.list()));
                }
                case EndTurnResult nothing -> {
                    GameState.global()
                        .getHistory()
                        .add(action.getAction(), new Position(action.targetX(), action.targetY()), id);
                    GameState.global().nextMove();
                    HUD.get().revertToInitialState();
                }
                default -> throw new IllegalStateException("Unexpected value: " + result.getClass());
            }
        }
        world.edit(entityId).remove(ActionComponent.class);
    }
}
