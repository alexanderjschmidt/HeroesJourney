package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;

import heroes.journey.GameState;
import heroes.journey.entities.actions.ActionQueue;
import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

class TargetState extends HUDState {
    @Override
    public void update(HUD hud) {
        if (hud.getCursor().getSelected() != null) {
            if (hud.getCursor().getActiveSkill().shouldTargetEntity()) {
                if (Gdx.input.isKeyJustPressed(KeyManager.UP) ||
                    Gdx.input.isKeyJustPressed(KeyManager.RIGHT)) {
                    GameState.global().getRangeManager().pointAtTarget(1);
                } else if (Gdx.input.isKeyJustPressed(KeyManager.DOWN) ||
                    Gdx.input.isKeyJustPressed(KeyManager.LEFT)) {
                    GameState.global().getRangeManager().pointAtTarget(-1);
                }
            } else {
                updateFreeMove(hud.getDelta());
            }
            if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
                ActionQueue.get().sendAction(HUD.get().getActionMenu().getSelected(), pathHolder);
                pathHolder = null;
                hud.getCursor()
                    .getActiveSkill()
                    .targetEffect(GameState.global(), hud.getCursor().getSelected(), hud.getCursor().x,
                        hud.getCursor().y);
                HUD.get().revertToPreviousState();
                GameState.global().nextTurn();
            } else if (Gdx.input.isKeyJustPressed(KeyManager.ESCAPE) ||
                Gdx.input.isKeyJustPressed(KeyManager.BACK)) {
                hud.getCursor().revertAction();
            }
        }
    }
}
