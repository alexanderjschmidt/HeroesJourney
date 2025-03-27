package heroes.journey.systems.constantsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;

import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.ActorComponent;
import heroes.journey.components.GlobalGameStateComponent;
import heroes.journey.components.MovementComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.ActionQueue;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.ui.HUD;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(PositionComponent.class, MovementComponent.class, ActorComponent.class,
            GlobalGameStateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        PositionComponent position = PositionComponent.get(entity);
        MovementComponent movement = MovementComponent.get(entity);
        ActorComponent actor = ActorComponent.get(entity);
        ActionComponent action = ActionComponent.get(entity);

        if (movement.hasPath() && !actor.hasActions()) {
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
                        if (movement.hasPath() || action == null) {
                            return;
                        }
                        HUD.get().revertToPreviousState();
                        updateActions(action, entity);
                    }
                })));
        }
    }

    private void updateActions(ActionComponent action, Entity entity) {
        if (action.getAction() == null) {
            HUD.get().getActionMenu().open();
        } else {
            if (action.getAction() instanceof TargetAction targetAction) {
                targetAction.targetEffect(GameState.global(), entity, action.getTargetX(),
                    action.getTargetY());
            } else {
                action.getAction().onSelect(GameState.global(), entity);
            }
            if (ActionQueue.get().isActionInProgress()) {
                if (action.getAction().equals(BaseActions.wait)) {
                    ActionQueue.get().endAction();
                } else {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ActionQueue.get().endAction();
                        }
                    }, 1f);
                }
            }
        }
    }
}
