package heroes.journey.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.components.overworld.character.MovementComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.hudstates.States;
import heroes.journey.utils.RangeManager.RangeColor;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.art.ResourceManager;

public class Cursor {
    // coords
    public int x, y;
    // render points

    private HUD hud;

    private Entity selected, hover;
    // Starting positions of selected to revert to on ESCAPE
    private int sx = -1, sy = -1;
    private Cell path;

    private Animation<TextureRegion> ani;

    private float elapsed = 0;

    public Cursor(HUD hud) {
        this.hud = hud;
        TextureRegion[] frames = {ResourceManager.get(LoadTextures.UI)[0][0],
            ResourceManager.get(LoadTextures.UI)[0][0], ResourceManager.get(LoadTextures.UI)[1][0]};
        ani = new Animation<TextureRegion>(.5f, frames);
        setPosition(10, 15);
    }

    public void update() {
        hover = GameState.global().getEntities().get(x, y);
        if (selected != null && GameState.global().getRangeManager().getRange()[x][y] == RangeColor.BLUE &&
            (path == null || (path.x != x || path.y != y))) {
            path = new EntityCursorPathing().getPath(GameState.global().getMap(), sx, sy, x, y, selected);
        }
    }

    public void clearSelected() {
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

    public void setPosition(Entity e) {
        PositionComponent position = PositionComponent.get(e);
        this.setPosition(position.getX(), position.getY());
    }

    public void revertSelectedPosition() {
        if (selected == null) {
            GameState.global().getRangeManager().clearRange();
            return;
        }
        GameState.global().getEntities().moveEntity(selected, sx, sy);
        StatsComponent statsComponent = StatsComponent.get(selected);
        setPosition(sx, sy);
        clearSelected();
        if (statsComponent.getMoveDistance() != 0) {
            update();
            setSelectedtoHover();
            GameState.global().getRangeManager().setMoveAndAttackRange(selected);
            hud.select();
        }
    }

    public Entity getHover() {
        return hover;
    }

    public Entity getSelected() {
        return selected;
    }

    public void setSelectedtoHover() {
        selected = hover;
        sx = x;
        sy = y;
    }

    public void revertAction() {
        this.setPosition(sx, sy);
        //selected.openActionMenu();
    }

    public void moveSelected() {
        if (GameState.global().getEntities().get(path.x, path.y) == null ||
            GameState.global().getEntities().get(path.x, path.y) == selected) {
            selected.add(new MovementComponent(path.reverse()));
            selected.add(new ActionComponent(BaseActions.openActionMenu));
            GameState.global().getRangeManager().clearRange();
            path = null;
        }
    }

    public Cell getPath() {
        return path;
    }

    public void setPath(Cell temp) {
        this.path = temp;
    }

}
