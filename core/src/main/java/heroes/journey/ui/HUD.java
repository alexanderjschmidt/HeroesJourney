package heroes.journey.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import heroes.journey.ui.hudstates.HUDState;
import heroes.journey.ui.hudstates.States;
import heroes.journey.ui.windows.ActionDetailUI;
import heroes.journey.ui.windows.ActionMenu;
import heroes.journey.ui.windows.EntityUI;
import heroes.journey.ui.windows.StatsUI;
import heroes.journey.ui.windows.TerrainUI;
import heroes.journey.ui.windows.TurnUI;

public class HUD extends Stage {

    public static final int FONT_SIZE = 24;

    private final Cursor cursor;
    private final Table layout, leftCol;

    private final ActionMenu actionMenu;
    private final ActionDetailUI actionDetailUI;
    private final TerrainUI terrainUI;
    private EntityUI entityUI, selectedEntityUI;
    private final TurnUI turnUI;
    private final StatsUI statsUI;

    private static HUD hud;
    private final StateMachine<HUD,HUDState> stateMachine;
    private float delta;

    public static HUD get() {
        if (hud == null)
            hud = new HUD();
        return hud;
    }

    public HUD() {
        super(new ScreenViewport());
        stateMachine = new StackStateMachine<HUD,HUDState>(this, States.LOCKED);
        stateMachine.setGlobalState(States.GLOBAL);

        cursor = new Cursor(this);

        actionDetailUI = new ActionDetailUI();
        actionMenu = new ActionMenu(actionDetailUI);
        terrainUI = new TerrainUI();
        entityUI = new EntityUI();
        turnUI = new TurnUI();
        statsUI = new StatsUI();

        layout = new Table();
        layout.setFillParent(true);
        layout.defaults().pad(5).expand().fill();
        layout.top().left();

        // TODO lock its width, so that the middle column can expland?
        leftCol = new Table();
        buildLeftCol();

        Table rightCol = new Table();
        rightCol.defaults().expand().fill();
        rightCol.top().left();
        //TODO fix action menu make it only take up as much space as it needs with a filler row
        rightCol.add(actionMenu).padBottom(2.5f);
        rightCol.row();
        rightCol.add(actionDetailUI).padTop(2.5f);

        layout.add(leftCol);
        layout.add(statsUI).colspan(3);
        layout.add(rightCol);

        leftCol.debug();
        rightCol.debug();
        layout.debug();
        this.addActor(layout);

        resize();
    }

    private void buildLeftCol() {
        leftCol.clearChildren();
        leftCol.defaults().expandX().fillX();
        leftCol.add(turnUI).minHeight(FONT_SIZE * 2).maxHeight(FONT_SIZE * 2).padBottom(2.5f).top();
        leftCol.row();
        leftCol.add().expandY();
        leftCol.row();
        if (selectedEntityUI != null) {
            selectedEntityUI.setPosition(0, 0);
            leftCol.add(selectedEntityUI)
                .minHeight(FONT_SIZE * 5)
                .maxHeight(FONT_SIZE * 5)
                .padBottom(2.5f)
                .padTop(2.5f)
                .bottom();
            leftCol.row();
        }
        leftCol.add(entityUI)
            .minHeight(FONT_SIZE * 5)
            .maxHeight(FONT_SIZE * 5)
            .padBottom(2.5f)
            .padTop(2.5f)
            .bottom();
        leftCol.row();
        leftCol.add(terrainUI).minHeight(FONT_SIZE * 2).maxHeight(FONT_SIZE * 2).padTop(2.5f).bottom();
    }

    public void update(float delta) {
        this.delta = delta;
        stateMachine.update();
        act();
        if (selectedEntityUI != null) {
            selectedEntityUI.watchSelected();
        }
        draw();
    }

    public void select() {
        selectedEntityUI = entityUI;
        entityUI = new EntityUI();
        float slideAmount = selectedEntityUI.getHeight() + 5;
        selectedEntityUI.addAction(Actions.sequence(Actions.moveBy(0, slideAmount, .2f, Interpolation.linear),
            Actions.run(this::buildLeftCol)));
    }

    public void clearSelect() {
        if (selectedEntityUI != null)
            selectedEntityUI.addAction(
                Actions.sequence(Actions.fadeOut(.5f, Interpolation.pow5Out), Actions.delay(1f),
                    Actions.removeActor()));
        selectedEntityUI = null;
        buildLeftCol();
    }

    public void resize() {
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public ActionMenu getActionMenu() {
        return actionMenu;
    }

    public ActionDetailUI getActionDetailedUI() {
        return actionDetailUI;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public StatsUI getEntityDetailedUI() {
        return statsUI;
    }

    public HUDState getState() {
        return stateMachine.getCurrentState();
    }

    public void setState(HUDState newState) {
        stateMachine.changeState(newState);
        System.out.println("set to " + stateMachine.getCurrentState());
    }

    public void revertToInitialState() {
        while (stateMachine.revertToPreviousState()) {
        }
        System.out.println("reset to " + stateMachine.getCurrentState());
    }

    public void revertToPreviousState() {
        stateMachine.revertToPreviousState();
        System.out.println("revert to " + stateMachine.getCurrentState());
    }

    public float getDelta() {
        return delta;
    }

    private float getHeightPerc(float percentage) {
        return (Gdx.graphics.getHeight() * percentage) / 100;
    }
}
