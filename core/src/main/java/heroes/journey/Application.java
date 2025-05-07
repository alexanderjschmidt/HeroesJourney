package heroes.journey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import heroes.journey.ui.screens.BattleScreen;
import heroes.journey.ui.screens.LoadingScreen;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;

/*
 * TODO List
 * Menus
 * 	Inventory
 *      Icons
 * 		Show Gold and Weight (do I even want weight? could be a good soft stat usage) make you move less the more encumbered you are
 *          Trash Item to reduce weight if movement is 0
 * 		Scrolling
 *  Show Controls on UIs?
 *  Fix spacing on selector in ScrollPane
 *  Add slide animation to selector
 * Add Action Queue to order movement actions and cursor centers better
 *  Make Cursor jump to player at end of movement, not end of opponents turn (and statement?) incase the opponents turn ends faster than player movement
 * Effects
 *  make effects only show if can be seen in fog
 *  move effects to player info panel
 * Multiplayer
 *  setup player, host, and lobby for server
 *  make only one entity per team, will make my life easier on deciding what to display in ui
 * Make HUD Locked when players character is moving, and have the camera follow player movement
 * Make the different travels fail if someone is already there?
 *  Grayed if you can see someone their, and fail (loose the turn) if you cant see
 * AI
 *  Improve score function
 *  Add understanding of fog of war (Probably need more knowledge information)
 * Saving
 * Add Events
 *  Demon kings arrival
 *  new quests appearances
 * Map Generation
 *  Improve timeout/WFC going forever/Holes
 *  Add variation plain tiles
 * 	Guarantee path to all dungeons
 *      Make paths allowed to go through hills to make the \_/?
 *      Blacklist certain tiles in certain directions ie when a hill joins plains make it \_/ instead of \/ with no gap
 * 	Improve road generation roads are too straight
 * Sounds
 * 	Background music
 * 	Action Sounds
 * Efficiency
 *  Make Fog compute first and then dont render anything that would be covered by Dense Fog
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

        viewport = new FitViewport(w, h, new OrthographicCamera());
        GameCamera.get().setToOrtho(false, w, h);
        viewport.apply();
        batch = new SpriteBatch();

        setScreen(new LoadingScreen(this));
    }

    public void setScreen(Screen screen) {
        super.setScreen(screen);
        if (screen instanceof BattleScreen battleScreen) {
            battleScreen.startGame();
        } else {
            GameCamera.get().position.x = Gdx.graphics.getWidth() / 2f;
            GameCamera.get().position.y = Gdx.graphics.getHeight() / 2f;
        }
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
