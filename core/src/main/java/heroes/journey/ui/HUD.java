package heroes.journey.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import heroes.journey.ui.hudstates.HUDState;
import heroes.journey.ui.hudstates.States;
import heroes.journey.ui.windows.ActionDetailUI;
import heroes.journey.ui.windows.ActionMenu;
import heroes.journey.ui.windows.DelveUI;
import heroes.journey.ui.windows.EntityUI;
import heroes.journey.ui.windows.PopupUI;
import heroes.journey.ui.windows.StatsUI;
import heroes.journey.ui.windows.TerrainUI;
import heroes.journey.ui.windows.TurnUI;
import lombok.Getter;

public class HUD extends Stage {

    public static final int FONT_SIZE = 24;

    @Getter private final Cursor cursor;
    private final Table layout, leftCol;

    @Getter private final ActionMenu actionMenu;
    private final ActionDetailUI actionDetailUI;
    private final TerrainUI terrainUI;
    private EntityUI entityUI, selectedEntityUI;
    private final TurnUI turnUI;

    private final Cell<?> centerWindow;
    @Getter private final StatsUI statsUI;
    @Getter private final PopupUI popupUI;
    private DelveUI delveUI;

    private static HUD hud;
    private final StateMachine<HUD,HUDState> stateMachine;
    private HUDState baseState;
    @Getter private float delta;

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
        popupUI = new PopupUI();
        //delveUI = new DelveUI();

        layout = new Table();
        layout.setFillParent(true);
        layout.defaults().pad(5).expand().fill();
        layout.top().left();

        // TODO lock its width, so that the middle column can expand?
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
        centerWindow = layout.add(statsUI).colspan(3);
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

    public ActionDetailUI getActionDetailedUI() {
        return actionDetailUI;
    }

    public void updateCenterPanel() {
        centerWindow.clearActor();
        if (stateMachine.getCurrentState() == States.STATS) {
            centerWindow.setActor(statsUI).colspan(3).expand().fill();
        } else if (stateMachine.getCurrentState() == States.POP_UP) {
            Table popupTable = new Table();
            String text = popupUI.getText();
            long newlineCount = text.chars().filter(c -> c == '\n').count() + 2;
            popupTable.add(popupUI)
                .minWidth(FONT_SIZE * 25)
                .maxWidth(FONT_SIZE * 25)
                .minHeight(FONT_SIZE * newlineCount)
                .maxHeight(FONT_SIZE * newlineCount)
                .pad(10);
            centerWindow.setActor(popupTable).colspan(3).fill().expand();
        } else if (stateMachine.getCurrentState() == States.DELVE) {
            centerWindow.setActor(delveUI).colspan(3).expand().fill();
        }
        layout.invalidate();
    }

    public HUDState getState() {
        return stateMachine.getCurrentState();
    }

    public void setState(HUDState newState) {
        stateMachine.changeState(newState);
        System.out.println("set to " + stateMachine.getCurrentState() + " previous state " +
            stateMachine.getPreviousState() + " base state " + baseState);
    }

    public void setInitialState(HUDState state) {
        baseState = state;
        if (stateMachine.getPreviousState() == null) {
            stateMachine.setInitialState(baseState);
        }
        System.out.println("current state " + stateMachine.getCurrentState() + " previous state " +
            stateMachine.getPreviousState() + " base state " + baseState);
    }

    public void revertToInitialState() {
        while (stateMachine.revertToPreviousState()) {
        }
        if (stateMachine.getPreviousState() == null) {
            stateMachine.setInitialState(baseState);
        }
        System.out.println("reset to " + stateMachine.getCurrentState() + " base state " + baseState);
    }

    public void revertToPreviousState() {
        stateMachine.revertToPreviousState();
        if (stateMachine.getPreviousState() == null) {
            stateMachine.setInitialState(baseState);
        }
        System.out.println("revert to " + stateMachine.getCurrentState() + " previous state " +
            stateMachine.getPreviousState() + " base state " + baseState);
    }

}
