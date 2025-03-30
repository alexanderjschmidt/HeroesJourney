package heroes.journey.systems.constantsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import heroes.journey.GameState;
import heroes.journey.components.*;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.ui.HUD;

import static heroes.journey.ui.hudstates.States.LOCKED;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(PositionComponent.class, GlobalGameStateComponent.class, ActorComponent.class)
            .one(MovementComponent.class, ActionComponent.class).get());
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
                if (!movement.hasBegunMoving())
                    HUD.get().setState(LOCKED);
                //TODO Make duration based on move speed
                actor.addAction(Actions.sequence(
                    Actions.moveTo(movement.getPath().i - position.getX(), movement.getPath().j - position.getY(),
                        .2f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            actor.setPosition(0, 0);
                            GameState.global()
                                .getEntities()
                                .moveEntity(entity, movement.getPath().i, movement.getPath().j);
                            movement.progressPath();
                            if (movement.hasPath()) {
                                return;
                            }
                            System.out.println("Finished Moving");
                            entity.remove(MovementComponent.class);
                            HUD.get().revertToPreviousState();
                        }
                    })));
            }
        } else if (action != null) {
            updateActions(action, entity);
        }
    }

    private void updateActions(ActionComponent action, Entity entity) {
        if (action.getAction() instanceof TargetAction targetAction) {
            targetAction.targetEffect(GameState.global(), entity, action.getTargetX(),
                action.getTargetY());
        } else {
            action.getAction().onSelect(GameState.global(), entity);
        }
        entity.remove(ActionComponent.class);
        if (action.getAction().isTerminal())
            GameState.global().nextTurn();
    }
}
