package heroes.journey.systems.constantsystems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.*;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.initializers.base.actions.LoadOptions;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import heroes.journey.tilemap.FogUtils;
import heroes.journey.ui.HUD;

import java.util.UUID;

@All({PositionComponent.class, RenderComponent.class, IdComponent.class})
public class RenderSystem extends BaseEntitySystem {
    public RenderSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    public RenderSystem() {
    }

    protected final void processSystem() {
        GameWorld world = (GameWorld) getWorld();

        SpriteBatch batch = Application.get().getBatch();
        batch.begin();
        GameState.global().render(batch, world.getDelta());

        IntBag actives = this.subscription.getEntities();
        int[] ids = actives.getData();
        int i = 0;

        for (int s = actives.size(); s > i; ++i) {
            this.process(world, IdComponent.get(world, ids[i]));
        }

        Fog[][] fog = getFog();

        if (!LoadOptions.debugOption.isTrue())
            renderFog(batch, fog);

        HUD.get().getCursor().render(batch, world.getDelta());
        batch.end();
    }

    private Fog[][] getFog() {
        Fog[][] fog = new Fog[GameState.global().getWidth()][GameState.global().getHeight()];
        // Get Playing entities Map
        for (UUID playable : PlayerInfo.get().getPlayableEntities()) {
            MapComponent mapComponent = MapComponent.get(GameState.global().getWorld(), playable);
            if (mapComponent != null)
                FogUtils.mergeFog(fog, mapComponent.getFog());
        }
        // Get Playing entities vision
        for (UUID playable : PlayerInfo.get().getPlayableEntities()) {
            PositionComponent positionComponent = PositionComponent.get(GameState.global().getWorld(),
                playable);
            StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), playable);
            if (statsComponent != null && positionComponent != null) {
                FogUtils.applyVision(fog, positionComponent.getX(), positionComponent.getY(),
                    statsComponent.getVision());
            }
        }
        return fog;
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
