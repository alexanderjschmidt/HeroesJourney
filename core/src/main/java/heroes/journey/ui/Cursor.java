package heroes.journey.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.MovementComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.ui.HUD.HUDState;
import heroes.journey.utils.RangeManager.RangeColor;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.art.ResourceManager;
import heroes.journey.utils.art.TextureMaps;

public class Cursor {
    // coords
    public int x, y;
    // render points

    private HUD hud;

    private Entity selected, hover;
    private int sx = -1, sy = -1;
    private Cell path;
    private TargetAction activeSkill;

    private Animation<TextureRegion> ani;

    private int initialX, initialY;

    private float elapsed = 0;

    public Cursor(HUD hud) {
        this.hud = hud;
        TextureRegion[] frames = {ResourceManager.get(TextureMaps.UI)[0][0],
            ResourceManager.get(TextureMaps.UI)[0][0], ResourceManager.get(TextureMaps.UI)[1][0]};
        ani = new Animation<TextureRegion>(.5f, frames);
        setPosition(10, 15);
    }

    public void update(float delta) {
        hover = GameState.global().getEntities().get(x, y);
        if (selected != null && GameState.global().getRangeManager().getRange()[x][y] == RangeColor.BLUE &&
            (path == null || (path.i != x || path.j != y))) {
            StatsComponent statsComponent = StatsComponent.get(selected);
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
        drawPath(batch);
        if (hud.getState() != HUDState.LOCKED) {
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

    private void drawPath(Batch batch) {
        if (path == null)
            return;
        Cell c = path;
        Cell p = null;
        Cell n = c.parent;
        // no path dot
        if (n == null) {
            batch.draw(ResourceManager.get(TextureMaps.UI)[0][3], c.i * GameCamera.get().getSize(),
                c.j * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
            return;
        }
        // start
        if (n.i > c.i)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][1], (c.i) * GameCamera.get().getSize(),
                (c.j + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 270f);
        else if (n.i < c.i)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][1], (c.i + 1) * GameCamera.get().getSize(),
                c.j * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 90f);
        else if (n.j < c.j)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][1], (c.i + 1) * GameCamera.get().getSize(),
                (c.j + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 180f);
        else if (n.j > c.j)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][1], c.i * GameCamera.get().getSize(),
                c.j * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 0);
        p = c;
        c = n;
        n = n.parent;

        while (n != null) {
            if (p.i != n.i && p.j != n.j) {
                if (p.j != c.j) {
                    if (p.i < n.i && p.j < n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            (c.i) * GameCamera.get().getSize(), (c.j + 1) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 270);
                    else if (p.i > n.i && p.j < n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            (c.i + 1) * GameCamera.get().getSize(), (c.j + 1) * GameCamera.get().getSize(), 0,
                            0, GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 180f);
                    else if (p.i < n.i && p.j > n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            c.i * GameCamera.get().getSize(), c.j * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 0);
                    else if (p.i > n.i && p.j > n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            (c.i + 1) * GameCamera.get().getSize(), (c.j) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 90);
                } else {
                    if (p.i < n.i && p.j < n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            (c.i + 1) * GameCamera.get().getSize(), (c.j) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 90);
                    else if (p.i > n.i && p.j < n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            c.i * GameCamera.get().getSize(), c.j * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 0);
                    else if (p.i < n.i && p.j > n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            (c.i + 1) * GameCamera.get().getSize(), (c.j + 1) * GameCamera.get().getSize(), 0,
                            0, GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 180f);
                    else if (p.i > n.i && p.j > n.j)
                        batch.draw(ResourceManager.get(TextureMaps.UI)[0][2],
                            (c.i) * GameCamera.get().getSize(), (c.j + 1) * GameCamera.get().getSize(), 0, 0,
                            GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 270);
                }
            } else {
                // straights
                if (p.j != c.j)
                    batch.draw(ResourceManager.get(TextureMaps.UI)[0][1], c.i * GameCamera.get().getSize(),
                        c.j * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                        GameCamera.get().getSize(), 1f, 1f, 0);
                else
                    batch.draw(ResourceManager.get(TextureMaps.UI)[0][1],
                        (c.i + 1) * GameCamera.get().getSize(), c.j * GameCamera.get().getSize(), 0, 0,
                        GameCamera.get().getSize(), GameCamera.get().getSize(), 1f, 1f, 90f);
            }
            p = c;
            c = n;
            n = n.parent;
        }
        // arrow
        if (p.j > c.j)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][2], (c.i) * GameCamera.get().getSize(),
                (c.j + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 270f);
        else if (p.j < c.j)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][2], (c.i + 1) * GameCamera.get().getSize(),
                c.j * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 90f);
        else if (p.i > c.i)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][2], (c.i + 1) * GameCamera.get().getSize(),
                (c.j + 1) * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 180f);
        else if (p.i < c.i)
            batch.draw(ResourceManager.get(TextureMaps.UI)[1][2], c.i * GameCamera.get().getSize(),
                c.j * GameCamera.get().getSize(), 0, 0, GameCamera.get().getSize(),
                GameCamera.get().getSize(), 1f, 1f, 0);
    }

    public void setPosition(int i, int j) {
        x = i;
        y = j;
        update(0);
    }

    public void setPosition(Entity e) {
        PositionComponent position = PositionComponent.get(e);
        this.setPosition(position.getX(), position.getY());
    }

    public Entity getSelected() {
        return selected;
    }

    public void revertSelectedPosition() {
        if (selected == null) {
            GameState.global().getRangeManager().clearRange();
            return;
        }
        Entity e = GameState.global().getEntities().removeEntity(sx, sy);
        PositionComponent position = PositionComponent.get(e);
        position.setX(initialX).setY(initialY);
        GameState.global().getEntities().addEntity(e);
        clearSelected();
        if (5 != 0) {
            update(0);
            setSelectedtoHover();
            GameState.global().getRangeManager().setMoveAndAttackRange(selected);
            hud.select();
        }

    }

    public void setActiveSkill(TargetAction skill) {
        this.activeSkill = skill;
    }

    public Entity getHover() {
        return hover;
    }

    public Cell getPath() {
        return path;
    }

    public void setSelectedtoHover() {
        selected = hover;
        sx = x;
        sy = y;
        initialX = x;
        initialY = y;
    }

    public TargetAction getActiveSkill() {
        return activeSkill;
    }

    public void revertAction() {
        this.setPosition(sx, sy);
        //selected.openActionMenu();
    }

    public void turn(float vx, float vy) {
        this.x = (int) (sx + vx);
        this.y = (int) (sy + vy);
    }

    public void moveSelected() {
        if (GameState.global().getEntities().get(path.i, path.j) == null ||
            GameState.global().getEntities().get(path.i, path.j) == selected) {
            hud.setState(HUDState.MOVING);
            MovementComponent.get(selected).move(path.reverse());
            GameState.global().getRangeManager().clearRange();
            sx = path.i;
            sy = path.j;
            path = null;
        }
    }

    public void setPath(Cell temp) {
        this.path = temp;
    }

    public void setSelected(Entity selected) {
        this.selected = selected;
    }

}
