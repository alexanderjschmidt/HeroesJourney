package heroes.journey.ui.hudstates;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.ActionQueue;
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

    void sendAction() {
        Cell temp = pathHolder;
        if (temp == null) {
            temp = new Cell(cursor().x, cursor().y, 1);
        }
        List<Integer> xCoords = new ArrayList<Integer>();
        List<Integer> yCoords = new ArrayList<Integer>();
        while (temp != null) {
            xCoords.add(temp.i);
            yCoords.add(temp.j);
            temp = temp.parent;
        }
        // System.out.println(HUD.get().getActionMenu().getSelected());
        // System.out.println(HUD.get().getCursor().x + ", " +
        // HUD.get().getCursor().y);
        pathHolder = null;
        JSONObject gameAction = new JSONObject();
        try {
            gameAction.put("skill", HUD.get().getActionMenu().getSelected());
            gameAction.put("targetX", cursor().x);
            gameAction.put("targetY", cursor().y);
            gameAction.put("xCoords", xCoords);
            gameAction.put("yCoords", yCoords);
        } catch (JSONException e) {
            System.out.println(e);
        }
        ActionQueue.get().sendAction(gameAction);
    }

    void savePath() {
        Cell holder = Cell.clone(cursor().getPath());
        if (holder == null) {
            if (cursor().getSelected() != null) {
                PositionComponent position = PositionComponent.get(cursor().getSelected());
                holder = new Cell(position.getX(), position.getY(), 1);
            } else
                holder = new Cell(cursor().x, cursor().y, 1);
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
