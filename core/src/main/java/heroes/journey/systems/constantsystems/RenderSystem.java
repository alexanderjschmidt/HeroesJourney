package heroes.journey.systems.constantsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.ActorComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RenderComponent;
import heroes.journey.ui.HUD;

public class RenderSystem extends IteratingSystem {

    public RenderSystem() {
        super(Family.all(PositionComponent.class, RenderComponent.class).get());
    }

    @Override
    public void update(float delta) {
        SpriteBatch batch = Application.get().getBatch();

        batch.begin();

        GameState.global().render(batch, delta);
        super.update(delta);
        HUD.get().getCursor().render(batch, delta);

        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        PositionComponent position = PositionComponent.get(entity);
        ActorComponent actor = ActorComponent.get(entity);

        if (GameCamera.get().onCamera(position.getX(), position.getY())) {
            RenderComponent render = RenderComponent.get(entity);

            float x = (position.getX() + (actor == null ? 0 : actor.getX())) * GameCamera.get().getSize();
            float y = (position.getY() + (actor == null ? 0 : actor.getY())) * GameCamera.get().getSize();
            Application.get().getBatch().setColor(Color.WHITE);
            Application.get()
                .getBatch()
                .draw(render.getSprite(), x, y, GameCamera.get().getSize(),
                    heroes.journey.GameCamera.get().getSize());
        }
    }
}
