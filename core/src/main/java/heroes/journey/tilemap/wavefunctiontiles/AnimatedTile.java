package heroes.journey.tilemap.wavefunctiontiles;

import static heroes.journey.mods.Registries.TerrainManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.GameCamera;

public class AnimatedTile extends Tile {

    private final Animation<TextureRegion> animation;

    public AnimatedTile(
        Terrain terrain,
        int weight,
        boolean addToDefault,
        TextureRegion[] frames,
        float frameRate) {
        super(terrain, weight, addToDefault);
        this.animation = new Animation<>(frameRate, frames);
    }

    public AnimatedTile(
        String terrain,
        int weight,
        boolean addToDefault,
        TextureRegion[] frames,
        float frameRate) {
        this(TerrainManager.get(terrain), weight, addToDefault, frames, frameRate);
    }

    public void render(SpriteBatch batch, float elapsed, int x, int y) {
        batch.draw(animation.getKeyFrame(elapsed, true), x * GameCamera.get().getSize(),
            y * GameCamera.get().getSize(), GameCamera.get().getSize(), GameCamera.get().getSize());
    }
}
