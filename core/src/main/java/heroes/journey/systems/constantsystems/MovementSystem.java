package heroes.journey.systems.constantsystems;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.EventQueueComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.initializers.base.actions.LoadOptions;
import heroes.journey.systems.GameWorld;

import java.util.Objects;
import java.util.UUID;

@All({PositionComponent.class, ActorComponent.class, MovementComponent.class, IdComponent.class})
public class MovementSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        PositionComponent position = PositionComponent.get(world, id);
        MovementComponent movement = MovementComponent.get(world, id);
        ActorComponent actor = ActorComponent.get(world, id);

        updateMovement(id, entityId, position, actor, movement);

        if (actor != null) {
            actor.act(world.getDelta());
        }
    }

    @Override
    public void removed(int entityId) {
        GameWorld world = (GameWorld) getWorld();
        UUID id = IdComponent.get(world, entityId);
        EventQueueComponent events = EventQueueComponent.get(world, id);
        if (events != null) {
            Objects.requireNonNull(events.events().poll()).run();
            if (events.events().isEmpty()) {
                world.edit(entityId).remove(EventQueueComponent.class);
            }
        }
    }

    private void updateMovement(
        UUID id,
        int entityId,
        PositionComponent position,
        ActorComponent actor,
        MovementComponent movement) {
        if (movement.hasPath() && actor != null && !actor.hasActions()) {
            if (LoadOptions.debugOption.isTrue()) {
                System.out.println("Moving " + movement.path());
            }
            //TODO Make duration based on move speed
            actor.addAction(Actions.sequence(
                Actions.moveTo(movement.path().x - position.getX(), movement.path().y - position.getY(), .2f),
                Actions.run(() -> updatePosition(entityId, position, actor, movement))));
        }
    }

    private void updatePosition(
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
        if (LoadOptions.debugOption.isTrue()) {
            System.out.println("Finished Moving");
        }
        world.edit(entityId).remove(MovementComponent.class);
    }

}
