package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import heroes.journey.GameState;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

import java.util.Objects;

class CursorMoveState extends HUDState {
    @Override
    public void update(HUD hud) {
        if (Gdx.input.isKeyJustPressed(KeyManager.NEXT_CHARACTER)) {
            hud.getCursor().setPosition(GameState.global().getCurrentEntity());
        }
        if (Gdx.input.isKeyJustPressed(KeyManager.ESCAPE) || Gdx.input.isKeyJustPressed(KeyManager.BACK)) {
            if (hud.getCursor().getSelected() != null) {
                hud.getCursor().clearSelected();
            } else {
                HUD.get().setState(new ActionSelectState(TeamActions.getTeamActions(GameState.global())));
            }
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            // TODO only show/allow movement if it has a movement component
            if (hud.getCursor().getSelected() != null) {
                savePath();
                hud.getCursor().moveSelected();
            } else if (hud.getCursor().getHover() != null &&
                GameState.global().getPlayableEntities().contains(GameState.global().getCurrentEntity()) &&
                Objects.equals(hud.getCursor().getHover(), GameState.global().getCurrentEntity())) {
                hud.getCursor().setSelectedtoHover();
                HUD.get().select();
                StatsComponent stats = StatsComponent.get(GameState.global().getWorld(),
                    hud.getCursor().getSelected());
                if (stats.getMoveDistance() == 0) {
                    savePath();
                    GameState.global()
                        .getWorld()
                        .edit(hud.getCursor().getSelected())
                        .create(ActionComponent.class)
                        .action(BaseActions.openActionMenu);
                } else {
                    GameState.global().getRangeManager().setMoveAndAttackRange(hud.getCursor().getSelected());
                }
            } else if (hud.getCursor().getHover() == null) {
                HUD.get().setState(new ActionSelectState(TeamActions.getTeamActions(GameState.global())));
            }
        }
        updateFreeMove(hud.getDelta());
    }
}
