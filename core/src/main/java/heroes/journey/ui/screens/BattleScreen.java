package heroes.journey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.client.GameClient;
import heroes.journey.initializers.Initializer;
import heroes.journey.initializers.base.Map;
import heroes.journey.initializers.base.actions.LoadOptions;
import heroes.journey.models.MapData;
import heroes.journey.ui.*;
import heroes.journey.utils.MusicManager;
import heroes.journey.utils.Random;
import heroes.journey.utils.input.KeyManager;
import heroes.journey.utils.worldgen.NewMapManager;

public class BattleScreen implements Screen {

    private Application app;
    private GameClient client;
    private SpriteBatch batch;
    private LightManager lightManager;
    private DebugRenderer debugRenderer;

    private MapData mapData;
    private boolean ready = false;

    // quickStart constructor
    public BattleScreen(Application app, boolean quickStart) {
        this.app = app;
        this.mapData = new MapData((int) (Math.random() * 10000000), Map.MAP_SIZE, 2, false);
        this.client = new GameClient();
        this.lightManager = new LightManager();
        this.debugRenderer = new DebugRenderer();
    }

    public void startGame() {
        Initializer.init();
        batch = app.getBatch();

        NewMapManager.get().initMapGeneration(GameState.global(), mapData, ready);

        if (LoadOptions.backgroundMusic.isTrue())
            MusicManager.play("Sounds/Music/Dragon_Of_The_Mist.mp3");
        ready = true;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(KeyManager.RE_GEN_MAP)) {
            Random.get().setSeed((int) (Math.random() * 10000000));
            NewMapManager.get().initMapGeneration(GameState.global(), mapData, false);
        }

        GameCamera.get().updateGameCamera();
        batch.setProjectionMatrix(GameCamera.get().combined);

        GameState.global().update(delta);
        lightManager.update();
        if (LoadOptions.debugOption.isTrue())
            debugRenderer.render();
        WorldEffectManager.get().update(delta);
        HUD.get().update(delta);
        HUDEffectManager.get().update(delta);
    }

    @Override
    public void show() {
        app.getViewport().setCamera(GameCamera.get());
        GameCamera.get().setZoom();
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

    public void readyUp() {
        this.ready = true;
    }
}
