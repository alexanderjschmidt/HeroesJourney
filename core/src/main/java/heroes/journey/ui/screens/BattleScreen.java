package heroes.journey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.client.GameClient;
import heroes.journey.initializers.base.Map;
import heroes.journey.tilemap.MapData;
import heroes.journey.ui.HUD;
import heroes.journey.ui.LightManager;
import heroes.journey.utils.Random;
import heroes.journey.utils.input.KeyManager;
import heroes.journey.utils.worldgen.NewMapManager;

public class BattleScreen implements Screen {

    private Application app;
    private GameClient client;
    private SpriteBatch batch;
    private LightManager lightManager;

    private MapData mapData;
    private boolean ready = false;

    // quickStart constructor
    public BattleScreen(Application app, boolean quickStart) {
        this.app = app;
        this.mapData = new MapData((int) (Math.random() * 10000000), Map.MAP_SIZE, 2, false);
        this.client = new GameClient();
        this.lightManager = new LightManager();

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
        if (Gdx.input.isKeyJustPressed(KeyManager.RE_GEN_MAP)) {
            Random.get().setSeed((int) (Math.random() * 10000000));
            NewMapManager.get().initMapGeneration(GameState.global());
        }

        GameCamera.get().updateGameCamera();
        app.getViewport().setCamera(GameCamera.get());
        batch.setProjectionMatrix(GameCamera.get().combined);

        GameState.global().update(delta);
        lightManager.update();
        HUD.get().update(delta);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        if (!ready)
            return;
        HUD.get().resize();
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
