package heroes.journey.tilemap.wavefunction;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.GameCamera;

public class BaseTile extends Tile {

    private final TextureRegion texture;

    public BaseTile(Terrain terrain, int weight, boolean addToDefaultTiles, TextureRegion texture) {
        super(terrain, weight, addToDefaultTiles);
        this.texture = texture;
    }

    public BaseTile(Terrain terrain, int weight, TextureRegion texture) {
        super(terrain, weight, true);
        this.texture = texture;
    }

    public void render(SpriteBatch batch, float elapsed, int x, int y) {
        batch.draw(texture, x * GameCamera.get().getSize(), y * GameCamera.get().getSize(),
            GameCamera.get().getSize(), GameCamera.get().getSize());
    }
}
