package heroes.journey.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.initializers.base.Map;
import heroes.journey.systems.GameEngine;
import heroes.journey.tilemap.MapData;
import heroes.journey.ui.HUD;
import heroes.journey.utils.worldgen.NewMapManager;

public class BattleScreen implements Screen {

    private Application app;
    private SpriteBatch batch;

    private MapData mapData;
    private boolean ready = false;

    // quickStart constructor
    public BattleScreen(Application app, boolean quickStart) {
        this.app = app;
        this.mapData = new MapData((int) (Math.random() * 10000000), Map.MAP_SIZE, 2, false);
        startGame();
    }

    public void startGame() {
        batch = app.getBatch();

        GameState.global().init(mapData);
        NewMapManager.get().initMapGeneration(GameState.global());
        GameState.global().nextTurn();

        ready = true;
        //ActionQueue.get().checkLocked();
    }

    @Override
    public void render(float delta) {
        GameCamera.get().updateGameCamera();
        app.getViewport().setCamera(GameCamera.get());
        batch.setProjectionMatrix(GameCamera.get().combined);

        GameEngine.get().update(delta);
        HUD.get().update(delta);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        if (!ready)
            return;
        HUD.get().resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    public Application getApp() {
        return app;
    }

}
