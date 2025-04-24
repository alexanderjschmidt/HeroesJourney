package heroes.journey.systems.constantsystems;

import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.ActorComponent;
import heroes.journey.components.overworld.character.MovementComponent;
import heroes.journey.components.overworld.character.PositionComponent;

@All({PositionComponent.class, ActorComponent.class, MovementComponent.class})
public class MovementSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        World world = getWorld();
        PositionComponent position = PositionComponent.get(world, entityId);
        MovementComponent movement = MovementComponent.get(world, entityId);
        ActorComponent actor = ActorComponent.get(world, entityId);

        updateMovement(entityId, position, actor, movement);

        if (actor != null) {
            actor.act(world.getDelta());
        }
    }

    private void updateMovement(
        int entityId,
        PositionComponent position,
        ActorComponent actor,
        MovementComponent movement) {
        if (movement.hasPath() && actor != null && !actor.hasActions()) {
            System.out.println("Moving " + movement.path());
            if (!movement.hasBegunMoving()) {
                GameState.global().getHistory().add(movement.path(), entityId);
            }
            //TODO Make duration based on move speed
            actor.addAction(Actions.sequence(
                Actions.moveTo(movement.path().x - position.getX(), movement.path().y - position.getY(), .2f),
                Actions.run(() -> updatePosition(world, entityId, position, actor, movement))));
        }
    }

    private void updatePosition(
        World world,
        int entityId,
        PositionComponent position,
        ActorComponent actor,
        MovementComponent movement) {
        actor.reset();
        position.setPos(movement.path().x, movement.path().y);
        movement.progressPath();
        if (movement.hasPath()) {
            return;
        }
        System.out.println("Finished Moving");
        world.edit(entityId).remove(MovementComponent.class);
    }

}
