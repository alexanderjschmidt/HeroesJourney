package heroes.journey.systems.constantsystems;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.components.overworld.character.ActorComponent;
import heroes.journey.components.overworld.character.MovementComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.entities.Position;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.States;

@All({PositionComponent.class, ActorComponent.class})
@One({MovementComponent.class, ActionComponent.class})
public class MovementSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        PositionComponent position = PositionComponent.get(world, entityId);
        MovementComponent movement = MovementComponent.get(world, entityId);
        ActorComponent actor = ActorComponent.get(world, entityId);
        ActionComponent action = ActionComponent.get(world, entityId);

        if (movement != null) {
            if (movement.hasPath() && actor != null && !actor.hasActions()) {
                System.out.println("Moving " + movement.path());
                if (!movement.hasBegunMoving()) {
                    GameState.global().getHistory().add(movement.path(), entityId);
                }
                //TODO Make duration based on move speed
                actor.addAction(Actions.sequence(
                    Actions.moveTo(movement.path().x - position.getX(), movement.path().y - position.getY(),
                        .2f), Actions.run(() -> updatePosition(world, entityId, actor, movement))));
            }
        } else if (action != null) {
            updateActions(world, action, entityId);
        }

        if (actor != null) {
            actor.act(world.getDelta());
        }
    }

    private void updatePosition(World world, int entityId, ActorComponent actor, MovementComponent movement) {
        actor.reset();
        PositionComponent positionComponent = PositionComponent.get(world, entityId);
        positionComponent.setPos(movement.path().x, movement.path().y);
        movement.progressPath();
        if (movement.hasPath()) {
            return;
        }
        System.out.println("Finished Moving");
        world.edit(entityId).remove(MovementComponent.class);
    }

    private void updateActions(World world, ActionComponent action, int entityId) {
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
