package heroes.journey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import heroes.journey.screens.LoadingScreen;
import heroes.journey.utils.art.ResourceManager;

/*
 * TODO List
 * Fold Faction AI into character AI loop for prediction purposes
 * Make TextureMaps not Enums?
 * make cursor arrow pathfinding favor already followed path.
 * GameEngine
 *  Make Systems and Listeners more dynamic? Auto load them like the initializers?
 * Camera
 *  fix far out zoom
 *  force zoom if camera would go off screen
 *  make camera not go beyond map
 * Menus
 *  Action Detail UI flesh out
 *  Make Options be dynamic so you can add an option from initializers
 *  use a table layout
 *  use a ScrollPane UI element for action list and inventory
 *  reposition menus on window resize
 * 	Stats Screen
 * 		increment on level up
 * 	Inventory
 * 		Use Item (health potion)
 * 		Show Weight
 * 		Show Gold
 * 		Scrolling
 * 		Tabs
 *      Crafting??
 * 		Equipment
 * 			Equip
 * Multiplayer
 * Action on Town
 *  Add grayed out actions, ie missing a requirement but letting you know its an option
 *  Quests
 * Fog of War
 *  fog of unseen tiles
 *  black on undiscovered tiles
 * Add Day/Night Cycle (could be used to show the world itself is getting darker not day/night)
 *  darker then redder
 * Add time
 * Saving
 * Map Generation
 *  make Map Generation Phases not enums
 *  Blacklist certain tiles in certain directions ie when a hill joins plains make it \_/ instead of \/ with no gap
 *  Add variation plain tiles
 * 	Guarantee path to all dungeons
 * 	Improve road generation some towns roads loop too much
 * 	Improve map generation
 * Enter Region
 * 	World Map/Region Map/Local Map ?
 * 	64/16/16
 * Sounds
 * 	Background music
 * 	Action Sounds
 * Efficiency
 *  Use a GameEngine Pool for Ashely so AI doesnt keep making new Entities to be deleted
 *  Chunk EntityManager for more efficiency? Only clone nearby chunks for AI? limited on how far they could travel in x turns
 */
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

    public SpriteBatch getBatch() {
        return batch;
    }

    public Viewport getViewport() {
        return viewport;
    }

}
