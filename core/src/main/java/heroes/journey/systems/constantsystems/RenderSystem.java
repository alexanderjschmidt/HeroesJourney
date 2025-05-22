package heroes.journey.systems.constantsystems;

import java.util.UUID;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.initializers.base.actions.LoadOptions;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import heroes.journey.ui.HUD;
import heroes.journey.utils.RenderBounds;

@All({PositionComponent.class, RenderComponent.class, IdComponent.class})
public class RenderSystem extends BaseEntitySystem {
    public RenderSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    public RenderSystem() {
    }

    protected final void processSystem() {
        GameWorld world = (GameWorld)getWorld();

        SpriteBatch batch = Application.get().getBatch();
        batch.begin();
        GameState.global().render(batch, world.getDelta());

        IntBag actives = this.subscription.getEntities();
        int[] ids = actives.getData();
        int i = 0;

        for (int s = actives.size(); s > i; ++i) {
            this.process(world, IdComponent.get(world, ids[i]));
        }

        PlayerInfo.updateFog();

        if (!LoadOptions.debugOption.isTrue())
            renderFog(batch, PlayerInfo.get().getFog());

        HUD.get().getCursor().render(batch, world.getDelta());
        batch.end();
    }

    public void renderFog(Batch batch, Fog[][] fog) {
        RenderBounds bounds = RenderBounds.get();

        for (int x = bounds.x0; x < bounds.x1; x++) {
            for (int y = bounds.y0; y < bounds.y1; y++) {
                if (fog[x][y] != Fog.NONE && fog[x][y] != null)
                    fog[x][y].render(batch, x, y);
            }
        }
    }

    private void process(GameWorld world, UUID entityId) {
        PositionComponent position = PositionComponent.get(world, entityId);

        if (GameCamera.get().onCamera(position.getX(), position.getY())) {
            ActorComponent actor = ActorComponent.get(world, entityId);
            RenderComponent render = RenderComponent.get(world, entityId);

            float x = (position.getX() + (actor == null ? 0 : actor.getX())) * GameCamera.get().getSize();
            float y = (position.getY() + (actor == null ? 0 : actor.getY())) * GameCamera.get().getSize();
            Application.get().getBatch().setColor(Color.WHITE);
            Application.get()
                .getBatch()
                .draw(render.sprite(), x, y, GameCamera.get().getSize(),
                    heroes.journey.GameCamera.get().getSize());
        }
    }
}
