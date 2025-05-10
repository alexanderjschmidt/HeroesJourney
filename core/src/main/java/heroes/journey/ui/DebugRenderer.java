package heroes.journey.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import heroes.journey.GameCamera;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;

import java.util.UUID;

public class DebugRenderer {

    private Color connectionColor = Color.WHITE;
    private Color featureDotColor = Color.RED;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public DebugRenderer() {
    }

    public void render() {
        shapeRenderer.setProjectionMatrix(GameCamera.get().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(connectionColor);

        for (Feature feature : FeatureManager.get().values()) {
            for (UUID connectedId : feature.connections) {
                // Avoid double-drawing
                Feature connected = FeatureManager.get().get(connectedId);
                if (feature.location.hashCode() < connected.location.hashCode()) {
                    shapeRenderer.line((feature.location.getX() * GameCamera.get().getSize()) +
                            (GameCamera.get().getSize() / 2f),
                        (feature.location.getY() * GameCamera.get().getSize()) +
                            (GameCamera.get().getSize() / 2f),
                        (connected.location.getX() * GameCamera.get().getSize()) +
                            (GameCamera.get().getSize() / 2f),
                        (connected.location.getY() * GameCamera.get().getSize()) +
                            (GameCamera.get().getSize() / 2f));
                }
            }
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(featureDotColor);

        for (Feature feature : FeatureManager.get().values()) {
            shapeRenderer.circle(
                (feature.location.getX() * GameCamera.get().getSize()) + (GameCamera.get().getSize() / 2f),
                (feature.location.getY() * GameCamera.get().getSize()) + (GameCamera.get().getSize() / 2f),
                GameCamera.get().getSize() / 4f);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
