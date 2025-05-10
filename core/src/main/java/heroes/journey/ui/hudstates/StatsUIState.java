package heroes.journey.ui.hudstates;

import com.badlogic.gdx.Gdx;
import heroes.journey.PlayerInfo;
import heroes.journey.ui.HUD;
import heroes.journey.ui.windows.Display;
import heroes.journey.utils.input.KeyManager;

public class StatsUIState extends HUDState {

    private boolean justOpened = false;

    public StatsUIState() {
        super();
    }

    @Override
    public void enter(HUD hud) {
        justOpened = true;
        hud.updateCenterPanel();
        hud.getStatsUI().setVisible(true);
        hud.getStatsUI().setEntity(PlayerInfo.get().getPlayersHero());
    }

    @Override
    public void update(HUD hud) {
        HUD.get().getStatsUI().handleInputs();
        if (Gdx.input.isKeyJustPressed(KeyManager.ESCAPE) || Gdx.input.isKeyJustPressed(KeyManager.BACK)) {
            HUD.get().revertToPreviousState();
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_INVENTORY) && !justOpened) {
            if (HUD.get().getStatsUI().display() == Display.INVENTORY) {
                HUD.get().revertToPreviousState();
            } else {
                HUD.get().getStatsUI().updatePanel(Display.INVENTORY);
            }
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_STATS) && !justOpened) {
            if (HUD.get().getStatsUI().display() == Display.STATS) {
                HUD.get().revertToPreviousState();
            } else {
                HUD.get().getStatsUI().updatePanel(Display.STATS);
            }
        } else if (Gdx.input.isKeyJustPressed(KeyManager.SHOW_QUESTS) && !justOpened) {
            if (HUD.get().getStatsUI().display() == Display.QUESTS) {
                HUD.get().revertToPreviousState();
            } else {
                HUD.get().getStatsUI().updatePanel(Display.QUESTS);
            }
        }
        justOpened = false;
    }

    @Override
    public void exit(HUD hud) {
        hud.getStatsUI().setVisible(false);
    }

}
