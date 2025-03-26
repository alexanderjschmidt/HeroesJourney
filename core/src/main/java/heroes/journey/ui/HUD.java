package heroes.journey.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD extends Stage {

    public enum HUDState {
        CURSOR_MOVE, MOVING, ACTION_SELECT, TARGET, LOCKED;
    }

    public static final int FONT_SIZE = 24;

    private final Cursor cursor;
    private final Table layout;

    private HUDState state = HUDState.LOCKED;
    private final ActionMenu actionMenu;
    private final TerrainUI terrainUI;
    private EntityUI entityUI, selectedEntityUI;
    private final TurnUI turnUI;
    private final StatsUI statsUI;

    private static HUD hud;

    public static HUD get() {
        if (hud == null)
            hud = new HUD();
        return hud;
    }

    public HUD() {
        super(new ScreenViewport());
        cursor = new Cursor(this);
        actionMenu = new ActionMenu();
        terrainUI = new TerrainUI();
        entityUI = new EntityUI();
        turnUI = new TurnUI();
        statsUI = new StatsUI();
        this.addActor(actionMenu);
        this.addActor(terrainUI);
        this.addActor(entityUI);
        this.addActor(turnUI);
        this.addActor(statsUI);
        layout = new Table();
        this.addActor(layout);
    }

    public void update(float delta) {
        cursor.update(delta);
        act();
        actionMenu.setVisible(getState() == HUDState.ACTION_SELECT);
        terrainUI.update();
        entityUI.update();
        if (selectedEntityUI != null) {
            selectedEntityUI.watchSelected();
            selectedEntityUI.update();
        }
        turnUI.update();
    }

    public void select() {
        selectedEntityUI = entityUI;
        selectedEntityUI.addAction(Actions.moveBy(0, 24 + HUD.FONT_SIZE * 5, .2f, Interpolation.linear));
        entityUI = new EntityUI();
        this.addActor(entityUI);
    }

    public void clearSelect() {
        if (selectedEntityUI != null)
            selectedEntityUI.addAction(
                Actions.sequence(Actions.fadeOut(.5f, Interpolation.pow5Out), Actions.delay(1f),
                    Actions.removeActor()));
        selectedEntityUI = null;
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    public ActionMenu getActionMenu() {
        return actionMenu;
    }

    public StatsUI getEntityDetailedUI() {
        return statsUI;
    }

    public HUDState getState() {
        return state;
    }

    public void setState(HUDState state) {
        System.out.println(state);
        this.state = state;
    }

    public Cursor getCursor() {
        return cursor;
    }

}
