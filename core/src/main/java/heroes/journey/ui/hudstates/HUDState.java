package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.ui.Cursor;
import heroes.journey.ui.HUD;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.input.KeyManager;

public abstract class HUDState implements State<HUD> {

    private float holdElapsedX, holdElapsedY;

    public Cell pathHolder;

    void updateFreeMove(float delta) {
        if (Gdx.input.isKeyJustPressed(KeyManager.DOWN) && cursor().y > 0) {
            holdElapsedY = 0;
            cursor().y--;
        } else if (Gdx.input.isKeyJustPressed(KeyManager.UP) && cursor().y < gameState().getHeight() - 1) {
            holdElapsedY = 0;
            cursor().y++;
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.LEFT) && cursor().x > 0) {
            holdElapsedX = 0;
            cursor().x--;
        } else if (Gdx.input.isKeyJustPressed(KeyManager.RIGHT) && cursor().x < gameState().getWidth() - 1) {
            holdElapsedX = 0;
            cursor().x++;
        }
        if (Gdx.input.isKeyPressed(KeyManager.DOWN) && cursor().y > 0) {
            holdElapsedY += delta;
            if (holdElapsedY >= .1) {
                holdElapsedY = 0;
                cursor().y--;
            }
        } else if (Gdx.input.isKeyPressed(KeyManager.UP) && cursor().y < gameState().getHeight() - 1) {
            holdElapsedY += delta;
            if (holdElapsedY >= .1) {
                holdElapsedY = 0;
                cursor().y++;
            }
        }
        if (Gdx.input.isKeyPressed(KeyManager.RIGHT) && cursor().x < gameState().getWidth() - 1) {
            holdElapsedX += delta;
            if (holdElapsedX >= .1) {
                holdElapsedX = 0;
                cursor().x++;
            }
        } else if (Gdx.input.isKeyPressed(KeyManager.LEFT) && cursor().x > 0) {
            holdElapsedX += delta;
            if (holdElapsedX >= .1) {
                holdElapsedX = 0;
                cursor().x--;
            }
        }
    }

    private static Cursor cursor() {
        return HUD.get().getCursor();
    }

    private static GameState gameState() {
        return GameState.global();
    }

    void savePath() {
        Cell holder = Cell.clone(cursor().getPath());
        if (holder == null) {
            if (cursor().getSelected() != null) {
                PositionComponent position = PositionComponent.get(GameState.global().getWorld(),
                    cursor().getSelected());
                holder = new Cell(position.getX(), position.getY());
            } else
                holder = new Cell(cursor().x, cursor().y);
        }
        pathHolder = holder;
    }

    @Override
    public void enter(HUD hud) {
    }

    @Override
    public void exit(HUD hud) {
    }

    @Override
    public boolean onMessage(HUD hud, Telegram telegram) {
        return false;
    }
}
