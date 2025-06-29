package heroes.journey.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import heroes.journey.GameCamera;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.components.character.IdComponent;

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

    private void renderRegions(UUID[][] regionMap) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Map<UUID,Color> regionColors = new HashMap<>();
        float tileSize = GameCamera.get().getSize();

        for (int x = 0; x < regionMap.length; x++) {
            for (int y = 0; y < regionMap[0].length; y++) {
                UUID regionId = regionMap[x][y];

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
        IntBag regions = GameState.global().getWorld().getRegionSubscription().getEntities();
        int[] regionIds = regions.getData();

        for (int i = 0; i < regions.size(); i++) {
            int entityId = regionIds[i];
            UUID id = IdComponent.get(GameState.global().getWorld(), entityId);
            RegionComponent region = RegionComponent.get(GameState.global().getWorld(), id);
            PositionComponent regionPos = PositionComponent.get(GameState.global().getWorld(), id);
            for (UUID neighborId : region.neighborRegionIds) {
                PositionComponent neighborPos = PositionComponent.get(GameState.global().getWorld(),
                    neighborId);
                shapeRenderer.line(regionPos.getX() * tileSize + tileSize / 2f,
                    regionPos.getY() * tileSize + tileSize / 2f,
                    neighborPos.getX() * tileSize + tileSize / 2f,
                    neighborPos.getY() * tileSize + tileSize / 2f);
            }
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);

        for (int i = 0; i < regions.size(); i++) {
            int entityId = regionIds[i];
            UUID id = IdComponent.get(GameState.global().getWorld(), entityId);
            PositionComponent regionPos = PositionComponent.get(GameState.global().getWorld(), id);
            shapeRenderer.circle(regionPos.getX() * tileSize + tileSize / 2f,
                regionPos.getY() * tileSize + tileSize / 2f, tileSize / 3f);
        }

        shapeRenderer.end();
    }

    private Color generateColorFromId(UUID uuid) {
        if (uuid == null) {
            return new Color(0, 0, 0, 0); // Clear color (fully transparent)
        }
        // Convert UUID to a consistent hash code (use hashCode or better: combine both halves)
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        long combined = most ^ least; // combine both halves for more entropy
        int id = (int)(combined & 0xFFFFFFFFL); // get a consistent 32-bit int from the long

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
