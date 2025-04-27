package heroes.journey.systems.constantsystems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.initializers.base.LoadOptions;
import heroes.journey.tilemap.Fog;
import heroes.journey.ui.HUD;

@All({PositionComponent.class, RenderComponent.class})
public class RenderSystem extends BaseEntitySystem {
    public RenderSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    public RenderSystem() {
    }

    protected final void processSystem() {
        World world = getWorld();

        SpriteBatch batch = Application.get().getBatch();
        batch.begin();
        GameState.global().render(batch, world.getDelta());

        IntBag actives = this.subscription.getEntities();
        int[] ids = actives.getData();
        int i = 0;

        for (int s = actives.size(); s > i; ++i) {
            this.process(world, ids[i]);
        }

        Fog[][] fog = new Fog[GameState.global().getWidth()][GameState.global().getHeight()];
        for (Integer playable : GameState.global().getPlayableEntities()) {
            MapComponent mapComponent = MapComponent.get(GameState.global().getWorld(), playable);
            if (mapComponent != null)
                mergeFog(fog, mapComponent.getFog());
        }
        for (Integer playable : GameState.global().getPlayableEntities()) {
            MapComponent mapComponent = MapComponent.get(GameState.global().getWorld(), playable);
            PositionComponent positionComponent = PositionComponent.get(GameState.global().getWorld(), playable);
            StatsComponent statsComponent = StatsComponent.get(GameState.global().getWorld(), playable);
            if (mapComponent != null && statsComponent != null && positionComponent != null) {
                int vision = statsComponent.getVision();
                for (int x = -vision; x <= vision; x++) {
                    for (int y = -vision; y <= vision; y++) {
                        if (Math.abs(x) + Math.abs(y) <= vision) {
                            int newX = positionComponent.getX() + x;
                            int newY = positionComponent.getY() + y;

                            // Ensure we are within bounds of the fog array
                            if (newX >= 0 && newX < fog.length && newY >= 0 && newY < fog.length) {
                                // Set fog to None (null) if within the vision range
                                fog[newX][newY] = null; // Assuming `null` indicates visible (no fog).
                                mapComponent.getFog()[newX][newY] = Fog.LIGHT;
                            }
                        }
                    }
                }
            }
        }

        if (!LoadOptions.debugOption.isTrue())
            renderFog(batch, fog);

        HUD.get().getCursor().render(batch, world.getDelta());
        batch.end();
    }

    private void mergeFog(Fog[][] fog, Fog[][] fog1) {
        // Assuming fog and fog1 have the same dimensions
        int width = fog.length;
        int height = fog[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Merge each corresponding element from fog and fog1
                fog[i][j] = mergeFog(fog[i][j], fog1[i][j]);
            }
        }
    }

    public Fog mergeFog(Fog existingFog, Fog newFog) {
        if (newFog == Fog.NONE || existingFog == Fog.NONE) {
            return Fog.NONE;
        } else if (newFog == Fog.LIGHT || existingFog == Fog.LIGHT) {
            return Fog.LIGHT;
        }
        return Fog.DENSE;
    }

    public void renderFog(Batch batch, Fog[][] fog) {
        int xo = (int) (GameCamera.get().position.x / GameCamera.get().getSize());
        int yo = (int) (GameCamera.get().position.y / GameCamera.get().getSize());

        int x0 = (int) Math.max(Math.floor(xo - GameCamera.get().getXLow()), 0);
        int y0 = (int) Math.max(Math.floor(yo - GameCamera.get().getYLow()), 0);
        int x1 = (int) Math.min(Math.floor(xo + GameCamera.get().getXHigh()), fog.length);
        int y1 = (int) Math.min(Math.floor(yo + GameCamera.get().getYHigh()), fog.length);

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                if (fog[x][y] != Fog.NONE && fog[x][y] != null)
                    fog[x][y].render(batch, x, y);
            }
        }
    }

    private void process(World world, int entityId) {
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
