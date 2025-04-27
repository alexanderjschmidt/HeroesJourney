package heroes.journey.ui;

import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.entities.Position;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.ui.hudstates.States;
import heroes.journey.utils.RangeManager.RangeColor;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;
import lombok.Setter;

public class Cursor {
    // coords
    public int x, y;
    // render points

    private final HUD hud;

    @Getter
    private Integer selected, hover;
    // Starting positions of selected to revert to on ESCAPE
    private int sx = -1, sy = -1;
    @Setter
    @Getter
    private Cell path;

    private final Animation<TextureRegion> ani, mapPointer;
    @Setter
    private Position mapPointerLoc;

    private float elapsed = 0;

    public Cursor(HUD hud) {
        this.hud = hud;
        TextureRegion[] frames = {ResourceManager.get(LoadTextures.UI)[0][0],
            ResourceManager.get(LoadTextures.UI)[0][0], ResourceManager.get(LoadTextures.UI)[1][0]};
        ani = new Animation<TextureRegion>(.5f, frames);
        TextureRegion[] framesPointer = {ResourceManager.get(LoadTextures.UI)[3][3],
            ResourceManager.get(LoadTextures.UI)[3][3], ResourceManager.get(LoadTextures.UI)[4][3]};
        mapPointer = new Animation<TextureRegion>(.5f, framesPointer);
    }

    public void update() {
        hover = GameState.global().getEntities().get(x, y);
        if (selected != null && GameState.global().getRangeManager().getRange()[x][y] == RangeColor.BLUE &&
            (path == null || (path.x != x || path.y != y))) {
            path = new EntityCursorPathing().getPath(GameState.global().getMap(), sx, sy, x, y, selected);
        }
    }

    public void clearSelected() {
        System.out.println("clear selected");
        selected = null;
        sx = -1;
        sy = -1;
        path = null;
        GameState.global().getRangeManager().clearRange();
        hud.clearSelect();
    }

    public void render(Batch batch, float delta) {
        elapsed += delta;
        drawPath(batch, path);
        if (hud.getState() == States.CURSOR_MOVE) {
            if (selected != null) {
                batch.setColor(Color.BLUE);
            } else if (hover != null) {
                batch.setColor(Color.RED);
            }
        }
        batch.draw(ani.getKeyFrame(elapsed, true), x * GameCamera.get().getSize(),
            y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
        if (mapPointerLoc != null) {
            batch.draw(mapPointer.getKeyFrame(elapsed, true), mapPointerLoc.getX() * GameCamera.get().getSize(),
                mapPointerLoc.getY() * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
        }
        batch.setColor(Color.WHITE);
    }

    private static void drawPath(Batch batch, Cell path) {
        if (path == null)
            return;
        Cell c = path;
        Cell p = null;
        Cell n = c.parent;
        // no path dot
        if (n == null) {
            batch.draw(ResourceManager.get(LoadTextures.UI)[0][3], c.x * GameCamera.get().getSize(),
                c.y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
            return;
        }
        // start
        if (n.x > c.x)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][1], (c.x) * GameCamera.get().getSize(),
                (c.y + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 270f);
        else if (n.x < c.x)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][1], (c.x + 1) * GameCamera.get().getSize(),
                c.y * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 90f);
        else if (n.y < c.y)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][1], (c.x + 1) * GameCamera.get().getSize(),
                (c.y + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 180f);
        else if (n.y > c.y)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][1], c.x * GameCamera.get().getSize(),
                c.y * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 0);
        p = c;
        c = n;
        n = n.parent;

        while (n != null) {
            if (p.x != n.x && p.y != n.y) {
                if (p.y != c.y) {
                    if (p.x < n.x && p.y < n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            (c.x) * GameCamera.get().getSize(), (c.y + 1) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 270);
                    else if (p.x > n.x && p.y < n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            (c.x + 1) * GameCamera.get().getSize(), (c.y + 1) * GameCamera.get().getSize(), 0,
                            0, GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 180f);
                    else if (p.x < n.x && p.y > n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            c.x * GameCamera.get().getSize(), c.y * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 0);
                    else if (p.x > n.x && p.y > n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            (c.x + 1) * GameCamera.get().getSize(), (c.y) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 90);
                } else {
                    if (p.x < n.x && p.y < n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            (c.x + 1) * GameCamera.get().getSize(), (c.y) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 90);
                    else if (p.x > n.x && p.y < n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            c.x * GameCamera.get().getSize(), c.y * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 0);
                    else if (p.x < n.x && p.y > n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            (c.x + 1) * GameCamera.get().getSize(), (c.y + 1) * GameCamera.get().getSize(), 0,
                            0, GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 180f);
                    else if (p.x > n.x && p.y > n.y)
                        batch.draw(ResourceManager.get(LoadTextures.UI)[0][2],
                            (c.x) * GameCamera.get().getSize(), (c.y + 1) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 270);
                }
            } else {
                // straights
                if (p.y != c.y)
                    batch.draw(ResourceManager.get(LoadTextures.UI)[0][1], c.x * GameCamera.get().getSize(),
                        c.y * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                        GameCamera.get().getSize(), 1f, 1f, 0);
                else
                    batch.draw(ResourceManager.get(LoadTextures.UI)[0][1],
                        (c.x + 1) * GameCamera.get().getSize(), c.y * GameCamera.get().getSize(), 0, 0,
                        GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 90f);
            }
            p = c;
            c = n;
            n = n.parent;
        }
        // arrow
        if (p.y > c.y)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][2], (c.x) * GameCamera.get().getSize(),
                (c.y + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 270f);
        else if (p.y < c.y)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][2], (c.x + 1) * GameCamera.get().getSize(),
                c.y * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 90f);
        else if (p.x > c.x)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][2], (c.x + 1) * GameCamera.get().getSize(),
                (c.y + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 180f);
        else if (p.x < c.x)
            batch.draw(ResourceManager.get(LoadTextures.UI)[1][2], c.x * GameCamera.get().getSize(),
                c.y * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 0);
    }

    public void setPosition(int i, int j) {
        x = i;
        y = j;
        update();
    }

    public void setPosition(Integer entityId) {
        PositionComponent position = PositionComponent.get(GameState.global().getWorld(), entityId);
        this.setPosition(position.getX(), position.getY());
    }

    public void revertSelectedPosition() {
        if (selected == null) {
            GameState.global().getRangeManager().clearRange();
            return;
        }
        World world = GameState.global().getWorld();
        PositionComponent positionComponent = PositionComponent.get(world, selected);
        positionComponent.setPos(sx, sy);
        StatsComponent statsComponent = StatsComponent.get(world, selected);
        setPosition(sx, sy);
        clearSelected();
        if (statsComponent.getMoveDistance() != 0) {
            update();
            setSelectedtoHover();
            GameState.global().getRangeManager().setMoveAndAttackRange(selected, sx, sy);
            hud.select();
        }
    }

    public void setSelectedtoHover() {
        selected = hover;
        sx = x;
        sy = y;
    }

    public void moveSelected() {
        if (GameState.global().getEntities().get(path.x, path.y) == null ||
            GameState.global().getEntities().get(path.x, path.y).equals(selected)) {
            EntityEdit entity = GameState.global().getWorld().edit(selected);
            entity.create(MovementComponent.class).path(path.reverse());
            entity.create(ActionComponent.class).action(BaseActions.openActionMenu);
            GameState.global().getRangeManager().clearRange();
            path = null;
        }
    }

}
