package heroes.journey.ui.hudstates;

import java.util.Objects;

import com.badlogic.gdx.Gdx;

import heroes.journey.GameState;
import heroes.journey.PlayerInfo;
import heroes.journey.components.character.AITurnComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.modlib.Ids;
import heroes.journey.mods.Registries;
import heroes.journey.ui.HUD;
import heroes.journey.utils.input.KeyManager;

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
                HUD.get().setState(new ActionSelectState(TeamActions.getTeamActions()));
            }
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SELECT)) {
            // System.out.println(hud.getCursor().getSelected());
            if (hud.getCursor().getHover() != null && PlayerInfo.isCurrentlyPlaying() &&
                Objects.equals(hud.getCursor().getHover(), GameState.global().getCurrentEntity())) {
                hud.getCursor().setSelectedtoHover();
                GameState.global()
                    .getWorld()
                    .edit(hud.getCursor().getSelected())
                    .create(ActionComponent.class)
                    .action((heroes.journey.entities.actions.Action)Registries.ActionManager.get(
                        Ids.OPEN_ACTION_MENU));
            } else if (hud.getCursor().getHover() == null) {
                HUD.get().setState(new ActionSelectState(TeamActions.getTeamActions()));
            }
        } else if (Gdx.input.isKeyJustPressed(KeyManager.AI_PLAY) && PlayerInfo.isCurrentlyPlaying()) {
            AITurnComponent ai = AITurnComponent.get(GameState.global().getWorld(),
                GameState.global().getCurrentEntity());
            ai.startProcessingNextMove(GameState.global(), GameState.global().getCurrentEntity());
        }
        updateFreeMove(hud.getDelta());
    }
}
