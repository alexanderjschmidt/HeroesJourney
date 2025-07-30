package heroes.journey.systems.constantsystems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.LocationComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.modlib.Ids;
import heroes.journey.mods.Registries;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Fog;
import heroes.journey.ui.HUD;
import heroes.journey.utils.RenderBounds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static heroes.journey.mods.Registries.RenderableManager;

@All({PositionComponent.class, RenderComponent.class, IdComponent.class})
public class RenderSystem extends BaseEntitySystem {

    private float deltaTime = 0f;
    private final TextureRegion front, background;

    public RenderSystem(Aspect.Builder aspect) {
        super(aspect);
        this.front = RenderableManager.get(Ids.GREEN).getRender();
        this.background = RenderableManager.get(Ids.RED).getRender();
    }

    public RenderSystem() {
        this.front = RenderableManager.get(Ids.GREEN).getRender();
        this.background = RenderableManager.get(Ids.RED).getRender();
    }

    protected final void processSystem() {
        GameWorld world = (GameWorld) getWorld();
        deltaTime += world.getDelta();
        if (deltaTime >= 60)
            deltaTime -= 60;

        SpriteBatch batch = Application.get().getBatch();
        batch.begin();
        GameState.global().render(batch, world.getDelta());

        IntBag actives = this.subscription.getEntities();
        int[] ids = actives.getData();

        List<UUID> sortedIds = new ArrayList<>(actives.size());

        for (int i = 0; i < actives.size(); ++i) {
            UUID id = IdComponent.get(world, ids[i]);
            sortedIds.add(id);
        }

        sortedIds.sort(Comparator.comparingInt(eid -> computeRenderPriority(world, eid)));

        for (UUID entityUuid : sortedIds) {
            this.process(world, entityUuid);
        }

        PlayerInfo.updateFog();

        if (!((heroes.journey.entities.actions.options.BooleanOptionAction) Registries.ActionManager.get(
            Ids.DEBUG)).isTrue())
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
                .draw(render.sprite().getRender(deltaTime), x, y, GameCamera.get().getSize(),
                    heroes.journey.GameCamera.get().getSize());

            // Draw health bar if entity has health
            Attributes stats = StatsComponent.get(world, entityId);
            if (stats != null) {
                Integer health = stats.get(Ids.STAT_CHALLENGE_HEALTH);
                Integer health_max = stats.get(Ids.STAT_CHALLENGE_HEALTH_MAX);
                if (health != null && health_max != null && health < health_max) {
                    float tileSize = GameCamera.get().getSize();
                    float barWidth = tileSize * 0.8f;  // 80% of tile width
                    float barHeight = tileSize * 0.1f; // 10% of tile height
                    float barX = x + (tileSize - barWidth) / 2;  // Center horizontally
                    float barY = y - barHeight - 2;  // Just below the sprite

                    // Draw background (red)
                    Application.get().getBatch().draw(background, barX, barY, barWidth, barHeight);
                    // Draw foreground (green) based on health percentage
                    float healthPercentage = health / 100f;  // Assuming max health is 100
                    Application.get().getBatch().draw(front, barX, barY, barWidth * healthPercentage, barHeight);
                }
            }
        }
    }

    private int computeRenderPriority(GameWorld world, UUID entityId) {
        // Lower number means "render first"
        boolean hasLocation = LocationComponent.get(world, entityId) != null;

        if (hasLocation)
            return 0;
        return 2; // fallback for everything else
    }
}
