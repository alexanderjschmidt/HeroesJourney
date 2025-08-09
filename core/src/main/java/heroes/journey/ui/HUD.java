package heroes.journey.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.FitViewport;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.actions.BooleanOptionAction;
import heroes.journey.mods.Registries;
import heroes.journey.ui.hudstates.HUDState;
import heroes.journey.ui.hudstates.PopupUIState;
import heroes.journey.ui.hudstates.States;
import heroes.journey.ui.windows.*;

public class HUD extends Stage {

    public static boolean isReverting = false;

    public static final int FONT_SIZE = 24;

    private final Cursor cursor;
    private final Table layout, leftCol;
    private final int layoutPadding = 5;

    // TODO move this into its own right column class
    private final ActionMenu actionMenu;
    private final InfoUI infoUI;

    // TODO move this into its own left column class
    private final TerrainUI terrainUI;
    private final EntityUI entityUI;
    private final TurnUI turnUI;

    // TODO move this into its own center screen class
    private final Cell<?> centerWindow;
    private final StatsUI statsUI;
    private final PopupUI popupUI;

    private static HUD hud;
    private final StateMachine<HUD, HUDState> stateMachine;
    private float delta;

    public static HUD get() {
        if (hud == null)
            hud = new HUD();
        return hud;
    }

    private HUD() {
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        stateMachine = new StackStateMachine<HUD, HUDState>(this, States.CURSOR_MOVE);
        stateMachine.setGlobalState(States.GLOBAL);

        cursor = new Cursor(this);

        infoUI = new InfoUI();
        actionMenu = new ActionMenu(infoUI);
        terrainUI = new TerrainUI();
        entityUI = new EntityUI();
        turnUI = new TurnUI();
        statsUI = new StatsUI();
        popupUI = new PopupUI();

        layout = new Table();
        layout.setFillParent(true);
        layout.defaults().pad(layoutPadding).fill().top().left();

        // TODO lock its width, so that the middle column can expand?
        leftCol = new Table();
        buildLeftCol();

        Table rightCol = new Table();
        rightCol.defaults().top().right().expand().fill().height(Value.percentHeight(0.49f, rightCol));
        //TODO fix action menu make it only take up as much space as it needs with a filler row
        rightCol.add(actionMenu).padBottom(2.5f);
        rightCol.row();
        rightCol.add(infoUI).padTop(2.5f);

        float screenWidth = getViewport().getWorldWidth();
        float sideWidth = screenWidth / 5;

        layout.add(leftCol).left().fill().width(sideWidth);
        centerWindow = layout.add(statsUI).expand().fill();
        layout.add(rightCol).right().fill().width(sideWidth);

        this.addActor(layout);

        resize();
    }

    private void buildLeftCol() {
        leftCol.clearChildren();
        leftCol.defaults().expandX().fill();
        leftCol.add(turnUI).height(Value.percentHeight(.075f, leftCol)).padBottom(2.5f).top();
        leftCol.row();
        leftCol.add().expandY();
        leftCol.row();
        leftCol.add(entityUI)
            .height(Value.percentHeight(.175f, leftCol))
            .padBottom(2.5f)
            .padTop(2.5f)
            .bottom();
        leftCol.row();
        leftCol.add(terrainUI).height(Value.percentHeight(.075f, leftCol)).padTop(2.5f).bottom();
    }

    public void update(float delta) {
        this.delta = delta;
        stateMachine.update();
        act();
        this.setDebugAll(((BooleanOptionAction) Registries.ActionManager.get(Ids.DEBUG)).isTrue());

        draw();
    }

    public void resize() {
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public InfoUI getActionDetailedUI() {
        return infoUI;
    }

    public void updateCenterPanel() {
        centerWindow.clearActor();
        if (stateMachine.getCurrentState() == States.STATS) {
            centerWindow.setActor(statsUI).expand().fill();
        } else if (stateMachine.getCurrentState() == States.POP_UP) {
            Table popupTable = new Table();
            popupTable.add(popupUI).width(Value.percentWidth(.75f, popupTable)).pad(10);
            centerWindow.setActor(popupTable)
                .expand()
                .fill()
                .height(Value.percentHeight(0.75f, layout))
                .center();
        }
        layout.invalidate();
        layout.pack();
    }

    public HUDState getState() {
        return stateMachine.getCurrentState();
    }

    public void setState(HUDState newState) {
        stateMachine.changeState(newState);
        if (((BooleanOptionAction) Registries.ActionManager.get(Ids.DEBUG)).isTrue()) {
            System.out.println("set to " + stateMachine.getCurrentState() + " previous state " +
                stateMachine.getPreviousState());
        }
    }

    public void revertToInitialState() {
        revertToInitialState(false);
    }

    public void revertToInitialState(boolean force) {
        if (stateMachine.getCurrentState() instanceof PopupUIState popupState && !force) {
            System.out.println("plan to reset on close");
            popupState.resetOnClose();
            return;
        }
        isReverting = true;
        while (stateMachine.revertToPreviousState()) {
        }
        isReverting = false;
        if (((BooleanOptionAction) Registries.ActionManager.get(Ids.DEBUG)).isTrue()) {
            System.out.println("reset to " + stateMachine.getCurrentState());
        }
    }

    public void revertToPreviousState() {
        stateMachine.revertToPreviousState();
        if (((BooleanOptionAction) Registries.ActionManager.get(Ids.DEBUG)).isTrue()) {
            System.out.println("revert to " + stateMachine.getCurrentState() + " previous state " +
                stateMachine.getPreviousState());
        }
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public ActionMenu getActionMenu() {
        return this.actionMenu;
    }

    public StatsUI getStatsUI() {
        return this.statsUI;
    }

    public PopupUI getPopupUI() {
        return this.popupUI;
    }

    public float getDelta() {
        return this.delta;
    }
}
