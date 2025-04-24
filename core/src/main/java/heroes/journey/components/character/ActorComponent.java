package heroes.journey.components.character;

import com.artemis.World;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.utils.Direction;
import lombok.Getter;

@Getter
@Transient
public class ActorComponent extends PooledClonableComponent<ActorComponent> {

    private final Actor actor;

    public ActorComponent() {
        actor = new Actor();
    }

    public void vibrate(float delay) {
        // 0.2 seconds
        Vector2 v = Direction.SOUTH.getDirVector();
        actor.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence(
            com.badlogic.gdx.scenes.scene2d.actions.Actions.delay(delay),
            com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(5 * v.y, 5 * v.x, .05f,
                Interpolation.pow5In),
            com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(-10 * v.y, -10 * v.x, .05f,
                Interpolation.pow5),
            com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(10 * v.y, 10 * v.x, .05f,
                Interpolation.pow5),
            com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(-5 * v.y, -5 * v.x, .05f,
                Interpolation.pow5Out)));

    }

    public void lunge(float delay) {
        // 0.4 seconds
        Vector2 v = Direction.SOUTH.getDirVector();
        actor.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence(
            com.badlogic.gdx.scenes.scene2d.actions.Actions.delay(delay),
            com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(15 * v.x, 15 * v.y, .2f,
                Interpolation.pow5In),
            com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(-15 * v.x, -15 * v.y, .2f,
                Interpolation.pow5Out)));
    }

    public static ActorComponent get(World world, int entityId) {
        return world.getMapper(ActorComponent.class).get(entityId);
    }

    public boolean hasActions() {
        return actor.hasActions();
    }

    public void act(float delta) {
        actor.act(delta);
    }

    public void addAction(Action action) {
        this.actor.addAction(action);
    }

    public void reset() {
        actor.setPosition(0, 0);
    }

    @Override
    public void copy(ActorComponent from) {
    }

    public float getX() {
        return actor.getX();
    }

    public float getY() {
        return actor.getY();
    }
}
