package heroes.journey.systems.constantsystems;

import static heroes.journey.ui.hudstates.States.LOCKED;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.ActorComponent;
import heroes.journey.components.GameStateComponent;
import heroes.journey.components.MovementComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.ui.HUD;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(PositionComponent.class, ActorComponent.class)
            .one(MovementComponent.class, ActionComponent.class)
            .get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        PositionComponent position = PositionComponent.get(entity);
        MovementComponent movement = MovementComponent.get(entity);
        ActorComponent actor = ActorComponent.get(entity);
        ActionComponent action = ActionComponent.get(entity);

        if (movement != null) {
            if (movement.hasPath() && !actor.hasActions()) {
                System.out.println("Moving");
                if (!movement.hasBegunMoving()) {
                    HUD.get().setState(LOCKED);
                    GameState.global()
                        .getHistory()
                        .add(movement.getPath(), GameStateComponent.get(entity).getId());
                }
                //TODO Make duration based on move speed
                actor.addAction(Actions.sequence(Actions.moveTo(movement.getPath().x - position.getX(),
                        movement.getPath().y - position.getY(), .2f),
                    Actions.run(() -> updatePosition(entity, actor, movement))));
            }
        } else if (action != null) {
            updateActions(action, entity);
        }
    }

    private void updatePosition(Entity entity, ActorComponent actor, MovementComponent movement) {
        actor.setPosition(0, 0);
        GameState.global().getEntities().moveEntity(entity, movement.getPath().x, movement.getPath().y);
        movement.progressPath();
        if (movement.hasPath()) {
            return;
        }
        System.out.println("Finished Moving");
        entity.remove(MovementComponent.class);
        HUD.get().revertToPreviousState();
    }

    private void updateActions(ActionComponent action, Entity entity) {
        if (action.getAction() instanceof TargetAction targetAction) {
            targetAction.targetEffect(GameState.global(), entity, action.getTargetX(), action.getTargetY());
        } else {
            action.getAction().onSelect(GameState.global(), entity);
        }
        entity.remove(ActionComponent.class);
        if (action.getAction().isTerminal()) {
            GameState.global()
                .getHistory()
                .add(action.getAction(), new Position(action.getTargetX(), action.getTargetY()),
                    GameStateComponent.get(entity).getId());
            GameState.global().nextTurn();
        }
    }
}
