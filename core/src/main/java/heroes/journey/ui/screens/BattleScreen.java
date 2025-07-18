package heroes.journey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.client.GameClient;
import heroes.journey.entities.actions.options.BooleanOptionAction;
import heroes.journey.models.MapData;
import heroes.journey.modlib.Ids;
import heroes.journey.mods.Registries;
import heroes.journey.ui.DebugRenderer;
import heroes.journey.ui.HUD;
import heroes.journey.ui.HUDEffectManager;
import heroes.journey.ui.LightManager;
import heroes.journey.ui.WorldEffectManager;
import heroes.journey.utils.MusicManager;
import heroes.journey.utils.Random;
import heroes.journey.utils.input.KeyManager;
import heroes.journey.utils.worldgen.MapGenPlan;
import heroes.journey.utils.worldgen.MapGenerator;

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
        this.mapData = new MapData((int)(Math.random() * 10000000), MapGenPlan.MAP_SIZE, 3, false);
        this.client = new GameClient();
        this.lightManager = new LightManager();
        this.debugRenderer = new DebugRenderer();
        new MapGenPlan().init();
    }

    public void startGame() {
        batch = app.getBatch();

        MapGenerator.initMapGeneration(GameState.global(), mapData, ready);

        if (((BooleanOptionAction)Registries.ActionManager.get(Ids.MUSIC)).isTrue())
            MusicManager.play("Sounds/Music/Dragon_Of_The_Mist.mp3");
        ready = true;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(KeyManager.RE_GEN_MAP)) {
            Random.get().setSeed((int)(Math.random() * 10000000));
            MapGenerator.initMapGeneration(GameState.global(), mapData, false);
        }

        GameCamera.get().updateGameCamera();
        batch.setProjectionMatrix(GameCamera.get().combined);

        GameState.global().update(delta);
        lightManager.update();
        if (((heroes.journey.entities.actions.options.BooleanOptionAction)Registries.ActionManager.get(
            Ids.DEBUG)).isTrue())
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
