package heroes.journey.ui.screens;

import static heroes.journey.mods.ModlibDSLSetupKt.setupModlibDSLs;

import java.io.File;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import heroes.journey.Application;
import heroes.journey.GameCamera;
import heroes.journey.mods.GameMod;
import heroes.journey.mods.ScriptModLoader;
import heroes.journey.registries.TileManager;
import heroes.journey.tilemap.TileBatch;
import heroes.journey.utils.art.ResourceManager;

public class LoadingScreen implements Screen {

    // Debug flag - set to true to enable debug output across the entire loading process
    private static final boolean DEBUG = false;

    private final Application app;

    private ShapeRenderer shapeRenderer;
    private boolean modFindingStarted = false;
    private boolean modsFound = false;
    private boolean modLoadingStarted = false;
    private boolean modsLoaded = false;
    private boolean assetsLoadingStarted = false;
    private boolean assetsLoaded = false;
    private float assetProgress = 0f;
    private float scriptProgress = 0f;
    private float modLoadingProgress = 0f;
    private float combinedProgress = 0f;
    private String currentScript = "";
    private String currentMod = "";
    private BitmapFont font;
    private SpriteBatch batch;
    private List<GameMod> foundMods;
    private boolean langLoadingStarted = false;
    private boolean langLoaded = false;
    private float langProgress = 0f;
    private String currentLangMod = "";

    public LoadingScreen(final Application app) {
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        shapeRenderer.setProjectionMatrix(GameCamera.get().combined);
        this.modFindingStarted = false;
        this.modsFound = false;
        this.modLoadingStarted = false;
        this.modsLoaded = false;
        this.assetsLoadingStarted = false;
        this.assetsLoaded = false;
        this.assetProgress = 0f;
        this.scriptProgress = 0f;
        this.modLoadingProgress = 0f;
        this.combinedProgress = 0f;
        this.currentScript = "";
        this.currentMod = "";
    }

    private void update(float delta) {
        updateModFinding();
        updateModLoading();
        updateLangLoading();
        updateAssetLoading();
        updateProgress();
    }

    private void updateModFinding() {
        if (!modFindingStarted) {
            // Set up modlib DSLs before finding mods
            setupModlibDSLs();
            // Start finding mods in background
            File modsFolder = new File("mods");
            ScriptModLoader.INSTANCE.startFindingModsFrom(modsFolder, DEBUG);
            ScriptModLoader.INSTANCE.startBackgroundFinding(DEBUG);
            modFindingStarted = true;
        }

        if (!modsFound) {
            // Check background progress
            scriptProgress = (float)ScriptModLoader.INSTANCE.getScriptsProcessed() /
                Math.max(1, ScriptModLoader.INSTANCE.getModFindingProgress() * 100);
            currentScript = ScriptModLoader.INSTANCE.getCurrentProcessingScript();

            if (ScriptModLoader.INSTANCE.isBackgroundFindingComplete()) {
                modsFound = true;
                foundMods = ScriptModLoader.INSTANCE.getFoundMods();
            }
        }
    }

    private void updateLangLoading() {
        if (!modsLoaded)
            return; // Wait for mod loading to complete
        if (!langLoadingStarted) {
            // Start loading lang files asynchronously
            langLoaded = false;
            langProgress = 0f;
            currentLangMod = "";
            heroes.journey.utils.Lang.INSTANCE.startAsyncLoad("en", () -> {
                langLoaded = true;
                return null;
            });
            langLoadingStarted = true;
        }
        if (!langLoaded) {
            langProgress = heroes.journey.utils.Lang.INSTANCE.getProgress();
            currentLangMod = heroes.journey.utils.Lang.INSTANCE.getCurrentMod();
        }
    }

    private void updateModLoading() {
        if (!modsFound)
            return; // Wait for mod finding to complete

        if (!modLoadingStarted) {
            // Start loading the found mods in background
            ScriptModLoader.INSTANCE.startBackgroundLoading(DEBUG);
            modLoadingStarted = true;
        }

        if (!modsLoaded) {
            // Check background loading progress
            currentMod = ScriptModLoader.INSTANCE.getCurrentProcessingMod();
            int totalMods = foundMods.size();
            int processedMods = ScriptModLoader.INSTANCE.getModsProcessed();

            // Calculate overall mod loading progress
            float overallProgress = 0f;
            if (totalMods > 0) {
                // Add progress from completed mods
                overallProgress += (float)processedMods / totalMods;

                // Add progress from current mod if any
                if (processedMods < totalMods && currentMod != null && !currentMod.isEmpty()) {
                    // Find the current mod and get its progress
                    for (GameMod mod : foundMods) {
                        if (mod.getName().equals(currentMod)) {
                            float currentModProgress = ScriptModLoader.INSTANCE.getModLoadingProgress(mod);
                            overallProgress += currentModProgress / totalMods;
                            break;
                        }
                    }
                }
            }
            modLoadingProgress = overallProgress;

            if (ScriptModLoader.INSTANCE.isBackgroundLoadingComplete()) {
                modsLoaded = true;
            }
        }
    }

    private void updateAssetLoading() {
        if (!modsLoaded)
            return; // Wait for mod loading to complete

        if (!assetsLoadingStarted) {
            ResourceManager.get().startLoadingTextures();
            assetsLoadingStarted = true;
        }

        if (!assetsLoaded) {
            assetProgress = MathUtils.lerp(assetProgress, ResourceManager.get().getProgress(), .1f);
            if (ResourceManager.get().update() &&
                assetProgress >= ResourceManager.get().getProgress() - .001f) {
                ResourceManager.get().splits();
                TileBatch.finalizeAllBatches();
                TileManager.finalizeAllBaseTiles();
                assetsLoaded = true;
            }
        }
    }

    private void updateProgress() {
        if (!modsFound) {
            // Still finding mods: 0-10%
            combinedProgress = scriptProgress * 0.1f;
        } else if (!modsLoaded) {
            // Loading mods: 10-40%
            combinedProgress = 0.1f + modLoadingProgress * 0.3f;
        } else if (!langLoaded) {
            // Loading lang files: 40-45%
            combinedProgress = 0.4f + langProgress * 0.05f;
        } else if (!assetsLoaded) {
            // Loading assets: 45-100%
            combinedProgress = 0.45f + assetProgress * 0.55f;
        } else {
            // All done: 100%
            combinedProgress = 1.0f;
            // Transition to next screen
            app.setScreen(new SplashScreen(app));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, GameCamera.get().viewportHeight / 2 - 8, GameCamera.get().viewportWidth - 64,
            64);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(32, GameCamera.get().viewportHeight / 2 - 8,
            Math.min(combinedProgress, 1.0f) * (GameCamera.get().viewportWidth - 64), 64);

        shapeRenderer.end();

        // Draw loading label
        batch.begin();
        String label;
        if (!modsFound && currentScript != null && !currentScript.isEmpty()) {
            int scriptsProcessed = ScriptModLoader.INSTANCE.getScriptsProcessed();
            int totalMods = (int)(ScriptModLoader.INSTANCE.getModFindingProgress() * 100);
            String modFolder = ScriptModLoader.INSTANCE.getCurrentModFolder();
            label = String.format("Finding mods: %s (%d/%d)", modFolder, scriptsProcessed, totalMods);
        } else if (!modsLoaded && currentMod != null && !currentMod.isEmpty()) {
            int modsProcessed = ScriptModLoader.INSTANCE.getModsProcessed();
            int totalMods = foundMods.size();

            // Find current mod and get its script progress
            String scriptProgress = "";
            for (GameMod mod : foundMods) {
                if (mod.getName().equals(currentMod)) {
                    int scriptsLoaded = mod.getScriptsLoaded();
                    int totalScripts = mod.getTotalScripts();
                    String currentScript = ScriptModLoader.INSTANCE.getCurrentModLoadingScript(mod);
                    int currentGroup = ScriptModLoader.INSTANCE.getCurrentModGroup(mod);
                    int totalGroups = ScriptModLoader.INSTANCE.getTotalModGroups(mod);
                    String groupName = ScriptModLoader.INSTANCE.getCurrentModGroupName(mod);

                    if (currentScript != null && !currentScript.isEmpty()) {
                        // Extract just the filename to hide file path structure
                        String scriptName = currentScript.substring(currentScript.lastIndexOf('/') + 1);
                        scriptProgress = String.format(" - %s: %s (%d/%d)", groupName, scriptName,
                            scriptsLoaded, totalScripts);
                    } else {
                        scriptProgress = String.format(" - %s (%d/%d scripts)", groupName, scriptsLoaded,
                            totalScripts);
                    }
                    break;
                }
            }

            label = String.format("Loading mod: %s (%d/%d)%s", currentMod, modsProcessed, totalMods,
                scriptProgress);
        } else if (!langLoaded && currentLangMod != null && !currentLangMod.isEmpty()) {
            int modsProcessed = (int)(langProgress * foundMods.size());
            int totalMods = foundMods.size();
            label = String.format("Loading language files: %s (%d/%d)", currentLangMod, modsProcessed,
                totalMods);
        } else if (!assetsLoaded) {
            label = ResourceManager.get().getLoadingStatus();
        } else {
            label = "";
        }
        if (!label.isEmpty()) {
            font.setColor(Color.WHITE);
            font.draw(batch, label, 40, GameCamera.get().viewportHeight / 2 - 24);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        // dispose();
    }

    @Override
    public void dispose() {
        ScriptModLoader.INSTANCE.shutdown();
        shapeRenderer.dispose();
        font.dispose();
        batch.dispose();
    }
}
