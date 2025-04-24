package heroes.journey.systems.constantsystems;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.components.overworld.character.MovementComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.entities.Position;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.States;

@All({PositionComponent.class, ActionComponent.class})
@Exclude({MovementComponent.class})
public class ActionSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        ActionComponent action = ActionComponent.get(world, entityId);

        String result = action.getAction().onSelect(GameState.global(), entityId);
        if (result != null) {
            //TODO make it only show up for players its supposed to
            HUD.get().getPopupUI().setText(result);
            HUD.get().setState(States.POP_UP);
        }
        if (action.getAction().isTerminal()) {
            GameState.global()
                .getHistory()
                .add(action.getAction(), new Position(action.targetX(), action.targetY()), entityId);
            GameState.global().nextTurn();
        }
        world.edit(entityId).remove(ActionComponent.class);
    }
}
