package heroes.journey.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.registries.RegionManager;
import heroes.journey.tilemap.Region;

public class DebugRenderer {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public DebugRenderer() {
    }

    public void render() {
        shapeRenderer.setProjectionMatrix(GameCamera.get().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        if (GameState.global().getMap().getRegionMap() != null) {
            renderRegions(GameState.global().getMap().getRegionMap());
        }

        renderRegionCentersAndConnections();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void renderRegions(int[][] regionMap) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Map<Integer,Color> regionColors = new HashMap<>();
        float tileSize = GameCamera.get().getSize();

        for (int x = 0; x < regionMap.length; x++) {
            for (int y = 0; y < regionMap[0].length; y++) {
                int regionId = regionMap[x][y];
                if (regionId < 0)
                    continue;

                Color color = regionColors.computeIfAbsent(regionId, id -> generateColorFromId(id));
                shapeRenderer.setColor(color);

                shapeRenderer.rect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        shapeRenderer.end();
    }

    private void renderRegionCentersAndConnections() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
        float tileSize = GameCamera.get().getSize();

        for (Region region : RegionManager.get().values()) {
            for (int neighborId : region.neighborRegionIds) {
                Region neighbor = RegionManager.get().get(neighborId);
                if (region.id < neighbor.id) { // avoid double-drawing
                    shapeRenderer.line(region.center.getX() * tileSize + tileSize / 2f,
                        region.center.getY() * tileSize + tileSize / 2f,
                        neighbor.center.getX() * tileSize + tileSize / 2f,
                        neighbor.center.getY() * tileSize + tileSize / 2f);
                }
            }
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);

        for (Region region : RegionManager.get().values()) {
            shapeRenderer.circle(region.center.getX() * tileSize + tileSize / 2f,
                region.center.getY() * tileSize + tileSize / 2f, tileSize / 3f);
        }

        shapeRenderer.end();
    }

    private Color generateColorFromId(int id) {
        // Spread hues using golden angle for even distribution
        float hue = (id * 137.508f) % 360f; // golden angle degrees
        float saturation = 0.6f;
        float lightness = 0.6f;

        return hslToRgba(hue, saturation, lightness, 0.5f); // last param is alpha
    }

    private Color hslToRgba(float h, float s, float l, float a) {
        h /= 360f;

        float r, g, b;
        if (s == 0f) {
            r = g = b = l; // achromatic
        } else {
            float q = l < 0.5f ? l * (1 + s) : (l + s - l * s);
            float p = 2 * l - q;
            r = hueToRgb(p, q, h + 1f / 3f);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1f / 3f);
        }

        return new Color(r, g, b, a);
    }

    private float hueToRgb(float p, float q, float t) {
        if (t < 0)
            t += 1;
        if (t > 1)
            t -= 1;
        if (t < 1f / 6f)
            return p + (q - p) * 6f * t;
        if (t < 1f / 2f)
            return q;
        if (t < 2f / 3f)
            return p + (q - p) * (2f / 3f - t) * 6f;
        return p;
    }

}
