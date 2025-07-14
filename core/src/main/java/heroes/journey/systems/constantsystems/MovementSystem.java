package heroes.journey.systems.constantsystems;

import java.util.Objects;
import java.util.UUID;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.EventQueueComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.modlib.Ids;
import heroes.journey.registries.Registries;
import heroes.journey.systems.GameWorld;

@All({PositionComponent.class, ActorComponent.class, MovementComponent.class, IdComponent.class})
public class MovementSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
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
        GameWorld world = (GameWorld)getWorld();

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

    private void updateMovement(
        UUID id,
        int entityId,
        PositionComponent position,
        ActorComponent actor,
        MovementComponent movement) {
        if (movement.hasPath() && actor != null && !actor.hasActions()) {
            if (((heroes.journey.entities.actions.options.BooleanOptionAction)Registries.ActionManager.get(
                Ids.DEBUG)).isTrue()) {
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
        if (((heroes.journey.entities.actions.options.BooleanOptionAction)Registries.ActionManager.get(
            Ids.DEBUG)).isTrue()) {
            System.out.println("Finished Moving");
        }
        world.edit(entityId).remove(MovementComponent.class);
    }

}
