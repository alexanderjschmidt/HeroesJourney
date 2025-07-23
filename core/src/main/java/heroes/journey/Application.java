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
import heroes.journey.utils.MusicManager;
import heroes.journey.utils.art.ResourceManager;
import lombok.Getter;

/*
 * TODO Features
 * Remove Feats, you will just earn actions (active), or buffs (passive
 * Allow buffs to be permanent
 * Display buffs
 * update basemod with costs
 * Have actions with limited uses (this can be refilled) (potions, scrolls)
 * Add Perks (actions or buffs earn from having X of a stat)
 * Update AI so that any non order specific actions dont matter for better efficiency?
 * Update region controls so the farther from the capital the less quests/challenges
 * Make challenges/quests have a value attribute that will control which regions they will appear in.
 * make some challenges biome specific
 * Add biome buffs
 * Remove inventory and items (anything like that will be controlled by an action or buff)
 * Add Events
 *  Demon kings arrival
 *  Global Quests
 *  new quests appearances
 */
/* TODO General Improvements
 *
 * MapGenPlan Generation
 *  Add variation plain tiles
 *      Make paths allowed to go through hills to make the \_/?
 *      Blacklist certain tiles in certain directions ie when a hill joins plains make it \_/ instead of \/ with no gap
 * 	Improve road generation roads are too straight
 *  make biome edges more natural
 * AI
 *  Add understanding of fog of war (Probably need more knowledge information)
 * better score function
 * Make HUD Locked when players character is moving, and have the camera follow player movement
 */
/*
 * TODO long term
 * Sounds
 * 	Action Sounds
 * Multiplayer
 *  setup player, host, and lobby for server
 */
@Getter
public class Application extends Game {

    private static Application app;
    private Viewport viewport;
    private SpriteBatch batch;

    private Application() {
        super();
    }

    public static Application get() {
        if (app == null)
            app = new Application();
        return app;
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
        MusicManager.get().dispose();
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
