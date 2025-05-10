package heroes.journey.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.Position;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.ui.hudstates.States;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Cursor {
    // coords
    public int x, y;
    // render points

    private final HUD hud;

    @Getter
    private UUID selected, hover;

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
        hover = GameState.global().getEntities().get(x, y).isEmpty() ? null : GameState.global().getEntities().get(x, y).getFirst();
    }

    public void clearSelected() {
        //System.out.println("clear selected");
        selected = null;
    }

    public void render(Batch batch, float delta) {
        elapsed += delta;
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
            batch.draw(mapPointer.getKeyFrame(elapsed, true),
                mapPointerLoc.getX() * GameCamera.get().getSize(),
                mapPointerLoc.getY() * GameCamera.get().getSize(), GameCamera.get().getSize(),
                GameCamera.get().getSize());
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

    public void setPosition(UUID entityId) {
        PositionComponent position = PositionComponent.get(GameState.global().getWorld(), entityId);
        this.setPosition(position.getX(), position.getY());
        GameCamera.get().center(position.getX(), position.getY());
    }

    public void revertSelectedPosition() {
        if (selected == null) {
            return;
        }
        clearSelected();
    }

    public void setSelectedtoHover() {
        selected = hover;
    }

}
