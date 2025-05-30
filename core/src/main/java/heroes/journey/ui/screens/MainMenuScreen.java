package heroes.journey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.utils.art.ResourceManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen implements Screen {

    private final Application app;

    private Stage stage;

    public MainMenuScreen(final Application app) {
        this.app = app;
        this.stage = new Stage(
            new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), GameCamera.get()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        Image background = new Image(ResourceManager.get().getTexture("Textures/UI/Background.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        Label label = new Label("Heroes Journey", ResourceManager.get().skin, "title");
        label.setPosition(Gdx.graphics.getWidth() / 2 - (label.getWidth() / 2),
            Gdx.graphics.getHeight() / 2 - (label.getHeight() / 2) + 120);
        stage.addActor(label);
        initButtons();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initButtons() {

        TextButton buttonPlay = new TextButton("New Game", ResourceManager.get().skin, "default");
        buttonPlay.setSize(280, 60);
        buttonPlay.setPosition(Gdx.graphics.getWidth() / 2 - (buttonPlay.getWidth() / 2),
            Gdx.graphics.getHeight() / 2 - (buttonPlay.getHeight() / 2));
        buttonPlay.addAction(
            sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MapSettingScreen(app));
            }
        });

        TextButton buttonLoad = new TextButton("Load Game", ResourceManager.get().skin, "default");
        buttonLoad.setSize(280, 60);
        buttonLoad.setPosition(Gdx.graphics.getWidth() / 2 - (buttonLoad.getWidth() / 2),
            Gdx.graphics.getHeight() / 2 - (buttonLoad.getHeight() / 2) - 70);
        buttonLoad.addAction(
            sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.loadFromFile("save");
                BattleScreen battleScreen = new BattleScreen(app, true);
                battleScreen.readyUp();
                app.setScreen(battleScreen);
            }
        });

        TextButton buttonExit = new TextButton("Exit", ResourceManager.get().skin, "default");
        buttonExit.setSize(280, 60);
        buttonExit.setPosition(Gdx.graphics.getWidth() / 2 - (buttonExit.getWidth() / 2),
            Gdx.graphics.getHeight() / 2 - (buttonExit.getHeight() / 2) - 140);
        buttonExit.addAction(
            sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        TextButton buttonTemplate = new TextButton("Join Game", ResourceManager.get().skin, "default");
        buttonTemplate.setSize(280, 60);
        buttonTemplate.setPosition(Gdx.graphics.getWidth() / 2 - (buttonTemplate.getWidth() / 2),
            Gdx.graphics.getHeight() / 2 - (buttonTemplate.getHeight() / 2) - 210);
        buttonTemplate.addAction(
            sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonTemplate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //app.setScreen(new BattleScreen(app));
            }
        });
        TextButton buttonSingle = new TextButton("Single Player Game", ResourceManager.get().skin, "default");
        buttonSingle.setSize(280, 60);
        buttonSingle.setPosition(Gdx.graphics.getWidth() / 2 - (buttonTemplate.getWidth() / 2),
            Gdx.graphics.getHeight() / 2 - (buttonTemplate.getHeight() / 2) - 280);
        buttonSingle.addAction(
            sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonSingle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new BattleScreen(app, true));
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonLoad);
        stage.addActor(buttonExit);
        stage.addActor(buttonTemplate);
        stage.addActor(buttonSingle);
    }
}
