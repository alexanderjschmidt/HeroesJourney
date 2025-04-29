package heroes.journey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import heroes.journey.ui.screens.LoadingScreen;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;

/*
 * TODO List
 * Menus
 *  Action Detail UI flesh out
 *      Make cooldown skills show recover timer
 *      Show Cost of actions
 *      Make Travel and Carriage actions cost dynamic based on distance
 * 	Inventory
 *      Icons
 * 		Show Gold and Weight (do I even want weight? could be a good soft stat usage) make you move less the more encumbered you are
 *          Trash Item to reduce weight if movement is 0
 * 		Scrolling
 *  Show Controls on UIs?
 * Make HUD Locked when players character is moving, and have the camera follow player movement
 * Dont make new basic Monsters for dungeons, they're just groupings of stats
 * make in bounds of 2d array a util function `>=0`
 * add effects to screen. Like plus's that fade upwards on regen (only if you can see it in fog)
 * Make Camera not go off map when zooming out
 * Multiplayer
 * Saving
 * Map Generation
 *  Improve timeout/WFC going forever
 *  Add variation plain tiles
 * 	Guarantee path to all dungeons
 *      Make paths allowed to go through hills to make the \_/?
 *      Blacklist certain tiles in certain directions ie when a hill joins plains make it \_/ instead of \/ with no gap
 * 	Improve road generation roads are too straight
 * Sounds
 * 	Background music
 * 	Action Sounds
 */
/*
 * Knowledge System
 *  Towns: Inn: Rumors
 *  Fog of War
 *  Knowledge Menu
 *      Locations
 *      Terrains
 *      Players
 *      Enemy Types
 *      Skills?
 *          Sword arts? Helps you train skills faster
 *          Heavy Armor
 *  Knowledge Vs Experience
 *      You can gain knowledge easier and it will speed up your experience gain
 *      for that experience/skill
 */
@Getter
public class Application extends Game {

    private Viewport viewport;
    private SpriteBatch batch;

    private static Application app;

    public static Application get() {
        if (app == null)
            app = new Application();
        return app;
    }

    private Application() {
        super();
    }

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        viewport = new StretchViewport(w, h, new OrthographicCamera());
        GameCamera.get().setToOrtho(false, w, h);
        viewport.apply();
        batch = new SpriteBatch();

        setScreen(new LoadingScreen(this));
    }

    public void setScreen(Screen screen) {
        super.setScreen(screen);
        GameCamera.get().position.x = Gdx.graphics.getWidth() / 2f;
        GameCamera.get().position.y = Gdx.graphics.getHeight() / 2f;
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.get().dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
