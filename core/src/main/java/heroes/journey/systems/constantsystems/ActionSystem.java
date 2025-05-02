package heroes.journey.systems.constantsystems;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.ui.hudstates.States;

@All({PositionComponent.class, ActionComponent.class})
@Exclude({MovementComponent.class})
public class ActionSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        ActionComponent action = ActionComponent.get(world, entityId);
        PositionComponent positionComponent = PositionComponent.get(world, entityId);

        if (positionComponent.isNotSynced()) {
            return;
        }

        // TODO this is null on carriage?
        ActionResult result = action.getAction().onSelect(GameState.global(), entityId);
        if (result != null) {
            //TODO make it only show up for players its supposed to
            System.out.println(result.getClass());
            switch (result) {
                case StringResult str -> {
                    GameState.global().nextMove();
                    HUD.get().revertToInitialState();
                    HUD.get().getPopupUI().setText(str.toString());
                    HUD.get().setState(States.POP_UP);
                }
                case ActionListResult actions -> {
                    HUD.get().setState(new ActionSelectState(actions.list()));
                }
                case EndTurnResult nothing -> {
                    GameState.global()
                        .getHistory()
                        .add(action.getAction(), new Position(action.targetX(), action.targetY()), entityId);
                    GameState.global().nextMove();
                    HUD.get().revertToInitialState();
                }
                default -> throw new IllegalStateException("Unexpected value: " + result.getClass());
            }
        }
        world.edit(entityId).remove(ActionComponent.class);
    }
}
