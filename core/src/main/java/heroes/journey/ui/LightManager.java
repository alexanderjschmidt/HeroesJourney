package heroes.journey.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import heroes.journey.GameState;

public class LightManager {

    int maxNightTurn = 10;
    int demonTurnStart = 10;
    int demonTurnEnd = 20;
    float maxNightAlpha = 0.4f;
    float maxDemonAlpha = 0.2f; // red intensity at full demon time

    // Night color component (blue)
    Color nightTint = new Color(0f, 0f, 0.1f, 1f); // full strength, we'll scale alpha
    // Demon red component
    Color demonTint = new Color(0.3f, 0f, 0f, 1f);

    boolean isLightningActive = false;
    int lightningStart = 15;
    float lightningTimer = 0f;
    float lightningDuration = 0.1f;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    Color currentAmbientColor = new Color(0, 0, 0, 0); // initialize somewhere, maybe in your game class

    public void update() {
        // Draw ambient light overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int currentTurn = GameState.global().getTurn();

        Color targetColor = getLight(currentTurn);
        float lerpSpeed = 3f; // higher = faster transition
        currentAmbientColor.lerp(targetColor, Gdx.graphics.getDeltaTime() * lerpSpeed);

        shapeRenderer.setColor(currentAmbientColor);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private Color getLight(int turn) {
        // Normalized progress
        float nightProgress = MathUtils.clamp((float) (turn - 1) / (maxNightTurn - 1), 0f, 1f);
        float demonProgress = MathUtils.clamp((float) (turn - demonTurnStart - 1) / (demonTurnEnd - demonTurnStart - 1), 0f, 1f);

        // Combine tints by blending RGB and calculating final alpha
        Color combined = new Color();
        combined.r = nightTint.r * nightProgress + demonTint.r * demonProgress;
        combined.g = nightTint.g * nightProgress + demonTint.g * demonProgress;
        combined.b = nightTint.b * nightProgress + demonTint.b * demonProgress;

        // Final alpha is based on night + demon separately (or you can tweak it)
        combined.a = nightProgress * maxNightAlpha + demonProgress * maxDemonAlpha;

        if (MathUtils.random() < 0.002f && turn >= lightningStart) {
            isLightningActive = true;
            lightningTimer = lightningDuration;
        }

        if (isLightningActive) {
            lightningTimer -= Gdx.graphics.getDeltaTime();
            if (lightningTimer <= 0f) {
                isLightningActive = false;
            } else {
                // Boost brightness or override tint
                combined = combined.cpy().lerp(Color.WHITE, 0.8f); // flash!
                combined.a = MathUtils.clamp(combined.a + 0.3f, 0f, 1f);
            }
        }

        return combined;
    }

}
