package heroes.journey.utils.art;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import heroes.journey.registries.Registry;

import java.util.HashMap;

public class ResourceManager extends AssetManager {

    public static Registry<Renderable> RenderableManager = new Registry<>();
    public static Registry<TextureMap> TextureManager = new Registry<>();

    public static TextureMap UI;

    private FreeTypeFontGenerator generator;
    public NinePatch menu;
    public BitmapFont font18;
    public BitmapFont font24;
    public BitmapFont font36;
    public BitmapFont font72;
    public Skin skin;

    public HashMap<TextureMap, TextureRegion[][]> textureRegions;

    private static ResourceManager manager;
    private int totalAssetsQueued = 1;
    private java.util.List<String> assetQueue = new java.util.ArrayList<>();

    public static synchronized ResourceManager get() {
        if (manager == null) {
            manager = new ResourceManager();
            UI = new TextureMap("ui", "UI/cursor.png", 32, 32).register();
        }
        return manager;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private ResourceManager() {
        textureRegions = new HashMap<>();
    }

    @Override
    public synchronized <T> void load(String fileName, Class<T> type) {
        // Only count if this asset isn't already loaded or queued
        if (!isLoaded(fileName) && !assetQueue.contains(fileName)) {
            totalAssetsQueued++;
            assetQueue.add(fileName);
        }
        super.load(fileName, type);
    }

    @Override
    public synchronized <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
        // Only count if this asset isn't already loaded or queued
        if (!isLoaded(fileName) && !assetQueue.contains(fileName)) {
            totalAssetsQueued++;
            assetQueue.add(fileName);
        }
        super.load(fileName, type, parameter);
    }

    public void startLoadingTextures() {
        // Initialize our asset tracking with any already loaded assets
        initializeAssetTracking();

        initFonts();
        loadSkin("uiskin");
        loadTexture("Textures/UI/cursor.png");
        loadTexture("Textures/sprites.png");
        loadTexture("Textures/UI/TalkingBackground.png");
        loadTexture("Textures/splash/badlogic.jpg");
        loadTexture("Textures/splash/pvglogo.png");
        loadTexture("Textures/splash/aschmidtlogo.png");
        loadTexture("Textures/UI/Background.png");
        menu = new NinePatch(new Texture(Gdx.files.internal("Textures/UI/menu9.png")), 12, 12, 12, 12);

        loadTexture("Textures/wangCorner.png");
        loadTexture("Textures/cliffTransitionTapper.png");
        loadTexture("Textures/cliffTransition.png");
        loadTexture("Textures/wangEdge.png");

        for (TextureMap map : textureRegions.keySet())
            loadTexture(map.getLocation());
    }

    private void initializeAssetTracking() {
        // Reset our tracking
        totalAssetsQueued = 1;
        assetQueue.clear();

        // Count any assets that are already loaded
        for (String assetName : getAssetNames()) {
            if (isLoaded(assetName)) {
                totalAssetsQueued++;
                assetQueue.add(assetName);
            }
        }
    }

    public void splits() {
        for (TextureMap map : textureRegions.keySet())
            loadTextureMap(map);
    }

    public void loadTextureMap(TextureMap textureMap) {
        //System.out.println("load map " + textureMap.getLocation());
        TextureRegion[][] textures = TextureRegion.split(getTexture(textureMap.getLocation()),
            textureMap.getWidth(), textureMap.getHeight());
        // Because fuck [y][x]
        TextureRegion[][] transposed = new TextureRegion[textures[0].length][textures.length];
        for (int y = 0; y < textures.length; y++) {
            for (int x = 0; x < textures[y].length; x++) {
                transposed[x][y] = textures[y][x]; // Swap indices
            }
        }
        textureRegions.put(textureMap, transposed);
    }

    public String loadTexture(String path) {
        //System.out.println("load texture " + path);
        if (isLoaded(path))
            return path;
        if (Gdx.files.internal(path).exists()) {
            load(path, Texture.class);
        }
        // manager.finishLoading();
        return path;
    }

    // could cause issues from creating file handles over and over
    public Texture getTexture(String path) {
        if (!Gdx.files.internal(path).exists()) {
            return null;
        }
        if (!isLoaded(path)) {
            this.load(path, Texture.class);
            this.finishLoadingAsset(path);
        }
        return get(path, Texture.class);
    }

    @Override
    public void dispose() {
        super.dispose();
        generator.dispose();
        font18.dispose();
        font24.dispose();
        font36.dispose();
        font72.dispose();
    }

    public static TextureRegion[][] get(TextureMap textureMap) {
        return manager.textureRegions.get(textureMap);
    }

    public static TextureRegion get(TextureMap textureMap, int x, int y) {
        return manager.textureRegions.get(textureMap)[x][y];
    }

    /**
     * Get the name of the asset currently being loaded
     *
     * @return The filename of the current asset being loaded, or empty string if none
     */
    public String getCurrentLoadingAsset() {
        if (getQueuedAssets() > 0) {
            // Find the first asset in our queue that's not loaded yet
            for (String asset : assetQueue) {
                if (!isLoaded(asset)) {
                    // Extract just the filename from the path
                    return asset.substring(asset.lastIndexOf('/') + 1);
                }
            }
        }
        return "";
    }

    /**
     * Get the number of assets currently queued for loading
     *
     * @return Number of assets in the loading queue
     */
    public int getQueuedAssets() {
        return totalAssetsQueued - getLoadedAssets();
    }

    /**
     * Get the total number of assets (loaded + queued)
     *
     * @return Total number of assets
     */
    public int getTotalAssets() {
        return totalAssetsQueued;
    }

    /**
     * Get detailed loading progress information
     *
     * @return A string describing current loading state
     */
    public String getLoadingStatus() {
        int loaded = getLoadedAssets();
        int total = getTotalAssets();
        String currentAsset = getCurrentLoadingAsset();

        if (currentAsset.isEmpty()) {
            return String.format("Loading assets... (%d/%d)", loaded, total);
        } else {
            return String.format("Loading: %s (%d/%d)", currentAsset, loaded, total);
        }
    }

    private void loadSkin(String skinName) {
        load("skin/" + skinName + "/" + skinName + ".atlas", TextureAtlas.class);
        finishLoadingAsset("skin/" + skinName + "/" + skinName + ".atlas");
        skin = new Skin();
        skin.addRegions(get("skin/" + skinName + "/" + skinName + ".atlas", TextureAtlas.class));
        skin.add("default-font", font18);
        skin.add("title", font72);
        skin.load(Gdx.files.internal("skin/" + skinName + "/" + skinName + ".json"));
    }

    private void initFonts() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/bulletinV1.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.color = Color.WHITE;

        parameter.size = 18;
        font18 = generator.generateFont(parameter);
        parameter.size = 24;
        font24 = generator.generateFont(parameter);
        parameter.size = 36;
        font36 = generator.generateFont(parameter);
        parameter.size = 72;
        font72 = generator.generateFont(parameter);
    }

}
